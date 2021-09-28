package three.two.bit.kbundle

import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import androidx.annotation.RequiresApi
import java.io.Serializable

/**
 * Builds bundle convenient way. Uses inline class to spare allocation.
 */
fun bundle(bundle: Bundle? = null, configure: BundleBuilder.() -> Unit): Bundle =
  BundleBuilderImpl(bundle ?: Bundle()).apply(configure).bundle

/**
 * Builder representing typed builder factory
 */
interface BundleBuilder {

  /** Inserts all mappings from the given Bundle into this BaseBundle. */
  fun putAll(bundle: Bundle)

  /** Inserts all mappings from the given PersistableBundle into this BaseBundle. */
  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  fun putAll(bundle: PersistableBundle)

  /** Key to [Int] value */
  infix fun String.to(value: Int)

  /** Key to [Long] value */
  infix fun String.to(value: Long)

  /** Key to [String] value */
  infix fun String.to(value: String)

  /** Key to [Float] value */
  infix fun String.to(value: Float)

  /** Key to [Short] value */
  infix fun String.to(value: Short)

  /** Key to [ShortArray] value */
  infix fun String.to(value: ShortArray?)

  /** Key to [IBinder] value */
  infix fun String.to(value: IBinder?)

  /** Key to [FloatArray] value */
  infix fun String.to(value: FloatArray?)

  /** Key to [Parcelable] value */
  infix fun String.to(value: Parcelable?)

  /** Key to [Serializable] value */
  infix fun String.to(value: Serializable?)

  /** Key to [Byte] value */
  infix fun String.to(value: Byte)

  /** Key to [ByteArray] value */
  infix fun String.to(value: ByteArray?)

  /** Key to [Char] value */
  infix fun String.to(value: Char)

  /** Key to [CharArray] value */
  infix fun String.to(value: CharArray?)

  /** Key to [CharSequence] value */
  infix fun String.to(value: CharSequence?)

  /** Key to [CharSequence] array value */
  infix fun String.to(value: Array<CharSequence>?)

  /** Key to [String] array value */
  infix fun String.to(value: Array<String>?)

  /** Key to [Bundle] array value */
  infix fun String.to(value: Bundle?)

  /** Key to [SparseArray] value */
  infix fun String.to(value: SparseArray<Parcelable>?)

  /** Key to [Parcelable] array value */
  infix fun String.to(value: Array<Parcelable>?)

  /** Key to [Double] value */
  infix fun String.to(value: Double)

  /** Key to [DoubleArray] value */
  infix fun String.to(value: DoubleArray?)

  /** Key to [Boolean] value */
  infix fun String.to(value: Boolean)

  /** Key to [BooleanArray] value */
  infix fun String.to(value: BooleanArray?)

  /** Key to [Size] value */
  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  infix fun String.to(value: Size)

  /** Key to [SizeF] value */
  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  infix fun String.to(value: SizeF)

  /** Key to [Int] list value */
  infix fun String.toIntList(value: ArrayList<Int>?)

  /** Key to [String] list value */
  infix fun String.toStringList(value: ArrayList<String>?)

  /** Key to [CharSequence] list value */
  infix fun String.toCharSequenceList(value: ArrayList<CharSequence>?)

  /** Key to [Parcelable] list value */
  infix fun String.toParcelableList(value: ArrayList<Parcelable>?)

}

@JvmInline
internal value class BundleBuilderImpl(val bundle: Bundle = Bundle()) : BundleBuilder {

  override fun putAll(bundle: Bundle) {
    bundle.putAll(bundle)
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun putAll(bundle: PersistableBundle) {
    bundle.putAll(bundle)
  }

  override fun String.to(value: String) {
    bundle.putString(this, value)
  }

  override fun String.to(value: Int) {
    bundle.putInt(this, value)
  }

  override fun String.to(value: Long) {
    bundle.putLong(this, value)
  }

  override fun String.to(value: Float) {
    bundle.putFloat(this, value)
  }

  override fun String.to(value: Short) {
    bundle.putShort(this, value)
  }

  override fun String.to(value: ShortArray?) {
    bundle.putShortArray(this, value)
  }

  override fun String.to(value: IBinder?) {
    bundle.putBinder(this, value)
  }

  override fun String.to(value: FloatArray?) {
    bundle.putFloatArray(this, value)
  }

  override fun String.to(value: Parcelable?) {
    bundle.putParcelable(this, value)
  }

  override fun String.to(value: Serializable?) {
    bundle.putSerializable(this, value)
  }

  override fun String.to(value: Byte) {
    bundle.putByte(this, value)
  }

  override fun String.to(value: ByteArray?) {
    bundle.putByteArray(this, value)
  }

  override fun String.to(value: Char) {
    bundle.putChar(this, value)
  }

  override fun String.to(value: CharArray?) {
    bundle.putCharArray(this, value)
  }

  override fun String.to(value: CharSequence?) {
    bundle.putCharSequence(this, value)
  }

  override fun String.to(value: Array<CharSequence>?) {
    bundle.putCharSequenceArray(this, value)
  }

  override fun String.to(value: Array<String>?) {
    bundle.putStringArray(this, value)
  }

  override fun String.toCharSequenceList(value: ArrayList<CharSequence>?) {
    bundle.putCharSequenceArrayList(this, value)
  }

  override fun String.to(value: Bundle?) {
    bundle.putBundle(this, value)
  }

  override fun String.to(value: SparseArray<Parcelable>?) {
    bundle.putSparseParcelableArray(this, value)
  }

  override fun String.to(value: Array<Parcelable>?) {
    bundle.putParcelableArray(this, value)
  }

  override fun String.to(value: Double) {
    bundle.putDouble(this, value)
  }

  override fun String.to(value: DoubleArray?) {
    bundle.putDoubleArray(this, value)
  }

  override fun String.to(value: Boolean) {
    bundle.putBoolean(this, value)
  }

  override fun String.to(value: BooleanArray?) {
    bundle.putBooleanArray(this, value)
  }

  override fun String.toParcelableList(value: ArrayList<Parcelable>?) {
    bundle.putParcelableArrayList(this, value)
  }

  override fun String.toIntList(value: ArrayList<Int>?) {
    bundle.putIntegerArrayList(this, value)
  }

  override fun String.toStringList(value: ArrayList<String>?) {
    bundle.putStringArrayList(this, value)
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun String.to(value: Size) {
    bundle.putSize(this, value)
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun String.to(value: SizeF) {
    bundle.putSizeF(this, value)
  }

}
