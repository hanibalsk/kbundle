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
package three.two.bit.kbundle.sample

import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import three.two.bit.kbundle.bundle
import three.two.bit.kbundle.instanceArgument
import three.two.bit.kbundle.requireArgument

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val s: String? = null
    val bundle = bundle {
      "IntKey" to 4
      "LongKey" to 7L
      "StringKey" to s
      "StringKey2" to "S"
      putAll(Bundle())
    }

    println(bundle)

    val fragment = CustomFragment.newInstance(
      1,
      "text",
      Rect(5, 3, 5, 3),
    )

    println(fragment)
  }
}

class CustomFragment : Fragment() {

  private val intParameter by requireArgument<Int>()
  private val stringWithDefaultParameter by requireArgument<String>("default text")
  private var longOptionalParameter by instanceArgument<Long>()

  // Error throw, when missing
  private val parcelableParameter by requireArgument<Rect>("parcelableParameter parameter is required")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    println("intParameter is $intParameter")
    println("stringWithDefaultParameter is $stringWithDefaultParameter")
    println("longOptionalParameter is $longOptionalParameter")
    println("parcelableParameter is $parcelableParameter")

    // Parameter can be changed
    longOptionalParameter = 22L
  }

  companion object {

    fun newInstance(
      intParameter: Int,
      stringWithDefaultParameter: String,
      parcelableParameter: Rect,
      longOptionalParameter: Long? = null,
    ) = CustomFragment().also { fragment ->
      fragment.arguments = bundle {
        fragment::intParameter.name to intParameter
        fragment::stringWithDefaultParameter.name to stringWithDefaultParameter
        fragment::longOptionalParameter.name to longOptionalParameter
        fragment::parcelableParameter.name to parcelableParameter
      }
    }
  }
}
