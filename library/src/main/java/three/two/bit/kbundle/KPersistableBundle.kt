/**
 * Designed and developed by Martin Janči (mj.janci@gmail.com)
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

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.RequiresApi

/**
 * Builds PersistableBundle convenient way. Uses inline class to spare allocation.
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun persistablePersistableBundle(
  PersistableBundle: PersistableBundle? = null,
  configure: PersistableBundleBuilder.() -> Unit
): PersistableBundle =
  PersistableBundleBuilderImpl(PersistableBundle ?: PersistableBundle()).apply(configure).PersistableBundle

/**
 * Builder representing typed builder factory
 */
interface PersistableBundleBuilder {

  /** Inserts all mappings from the given PersistableBundle into this BasePersistableBundle. */
  fun putAll(PersistableBundle: Bundle)

  /** Inserts all mappings from the given PersistablePersistableBundle into this BasePersistableBundle. */
  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  fun putAll(PersistableBundle: PersistableBundle)

  /** Key to [Int] value */
  infix fun String.to(value: Int)

  /** Key to [Long] value */
  infix fun String.to(value: Long)

  /** Key to [String] value */
  infix fun String.to(value: String)

  /** Key to [String] array value */
  infix fun String.to(value: Array<String>?)

  /** Key to [PersistableBundle] array value */
  infix fun String.to(value: PersistableBundle?)

  /** Key to [Double] value */
  infix fun String.to(value: Double)

  /** Key to [DoubleArray] value */
  infix fun String.to(value: DoubleArray?)

  /** Key to [Boolean] value */
  @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
  infix fun String.to(value: Boolean)

  /** Key to [BooleanArray] value */
  @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
  infix fun String.to(value: BooleanArray?)

}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@JvmInline
internal value class PersistableBundleBuilderImpl constructor(
  val PersistableBundle: PersistableBundle = PersistableBundle()
) : PersistableBundleBuilder {

  override fun putAll(PersistableBundle: Bundle) {
    PersistableBundle.putAll(PersistableBundle)
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun putAll(PersistableBundle: PersistableBundle) {
    PersistableBundle.putAll(PersistableBundle)
  }

  override fun String.to(value: String) {
    PersistableBundle.putString(this, value)
  }

  override fun String.to(value: Int) {
    PersistableBundle.putInt(this, value)
  }

  override fun String.to(value: Long) {
    PersistableBundle.putLong(this, value)
  }

  override fun String.to(value: Array<String>?) {
    PersistableBundle.putStringArray(this, value)
  }

  override fun String.to(value: PersistableBundle?) {
    PersistableBundle.putPersistableBundle(this, value)
  }

  override fun String.to(value: Double) {
    PersistableBundle.putDouble(this, value)
  }

  override fun String.to(value: DoubleArray?) {
    PersistableBundle.putDoubleArray(this, value)
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
  override fun String.to(value: Boolean) {
    PersistableBundle.putBoolean(this, value)
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
  override fun String.to(value: BooleanArray?) {
    PersistableBundle.putBooleanArray(this, value)
  }

}
