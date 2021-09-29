/**
 * Designed and developed by Martin Janči (m.janci@32bit.sk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package three.two.bit.kbundle

import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import androidx.fragment.app.Fragment
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Delegate for fragment arguments.
 */
abstract class ArgumentDelegate<T>(private val skipCache: Boolean = false) : ReadWriteProperty<Fragment, T> {

  private var isInitialized = false
  private var cached: T? = null

  override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
    cached = value
    isInitialized = true

    thisRef.arguments?.putGeneric(property.name, value)
  }

  protected fun getAndCache(thisRef: Fragment, key: String): T? {
    if (!skipCache && isInitialized) {
      return cached
    }

    @Suppress("UNCHECKED_CAST")
    val value = thisRef.arguments?.get(key) as T?
    cached = value
    return value
  }

  /** Nullable provider without default value from bundle */
  class Nullable<T>(skipCache: Boolean = false) : ArgumentDelegate<T?>(skipCache) {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? = getAndCache(thisRef, property.name)
  }

  /** Non nullable provider with default value from bundle */
  class NotNull<T>(private val defaultValue: T, skipCache: Boolean = false) : ArgumentDelegate<T>(skipCache) {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
      getAndCache(thisRef, property.name) ?: defaultValue
  }

  /** Non nullable provider without default value from bundle */
  class NotNullNotDefault<T>(val message: String? = null, skipCache: Boolean = false) :
    ArgumentDelegate<T>(skipCache) {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
      val cache = getAndCache(thisRef, property.name)
      requireNotNull(cache) { message ?: "Missing required argument" }
      return cache
    }
  }
}

/** Instantiate nullable value from arguments */
fun <T> instanceArgument(skipCache: Boolean = false): ArgumentDelegate<T?> = ArgumentDelegate.Nullable(skipCache)

/** Instantiate non-nullable value from arguments */
fun <T> instanceArgument(defaultValue: T, skipCache: Boolean = false): ArgumentDelegate<T> =
  ArgumentDelegate.NotNull(defaultValue, skipCache)

/** Instantiate non-nullable value from arguments */
fun <T> requireArgument(message: String? = null, skipCache: Boolean = false): ArgumentDelegate<T> =
  ArgumentDelegate.NotNullNotDefault(message, skipCache)

/**
 * Method to put common generic types into a [Bundle].
 * Use with caution, not all types are supported.
 * If feasible prefer the strict put methods.
 */
fun <T> Bundle.putGeneric(key: String, value: T?) {
  if (value == null) {
    remove(key)
    return
  }

  when (value) {
//    null -> remove(key)

    // Scalars
    is Boolean -> putBoolean(key, value)
    is Byte -> putByte(key, value)
    is Char -> putChar(key, value)
    is Double -> putDouble(key, value)
    is Float -> putFloat(key, value)
    is Int -> putInt(key, value)
    is Long -> putLong(key, value)
    is Short -> putShort(key, value)

    // References
    is Bundle -> putBundle(key, value)
    is CharSequence -> putCharSequence(key, value)
    is Parcelable -> putParcelable(key, value)

    // Scalar arrays
    is BooleanArray -> putBooleanArray(key, value)
    is ByteArray -> putByteArray(key, value)
    is CharArray -> putCharArray(key, value)
    is DoubleArray -> putDoubleArray(key, value)
    is FloatArray -> putFloatArray(key, value)
    is IntArray -> putIntArray(key, value)
    is LongArray -> putLongArray(key, value)
    is ShortArray -> putShortArray(key, value)

    // Reference arrays
    is Array<*> -> {
      val arrayValue = value ?: return
      val componentType = arrayValue::class.java.componentType ?: return
      @Suppress("UNCHECKED_CAST") // Checked by reflection.
      when {
        Parcelable::class.java.isAssignableFrom(componentType) -> {
          putParcelableArray(key, value as Array<Parcelable>)
        }
        String::class.java.isAssignableFrom(componentType) -> {
          putStringArray(key, value as Array<String>)
        }
        CharSequence::class.java.isAssignableFrom(componentType) -> {
          putCharSequenceArray(key, value as Array<CharSequence>)
        }
        Serializable::class.java.isAssignableFrom(componentType) -> {
          putSerializable(key, value)
        }
        else -> {
          val valueType = componentType.canonicalName
          throw IllegalArgumentException(
            "Illegal value array type $valueType for key \"$key\""
          )
        }
      }
    }

    // Last resort. Also we must check this after Array<*> as all arrays are serializable.
    is Serializable -> putSerializable(key, value)

    else -> {
      if (value is Binder) {
        putBinder(key, value)
      } else if (Build.VERSION.SDK_INT >= 21 && value is Size) {
        putSize(key, value)
      } else if (Build.VERSION.SDK_INT >= 21 && value is SizeF) {
        putSizeF(key, value)
      } else {
        val valueType = value.javaClass.canonicalName
        throw IllegalArgumentException("Illegal value type $valueType for key \"$key\"")
      }
    }
  }
}
