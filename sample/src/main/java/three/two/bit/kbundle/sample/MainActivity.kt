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
package three.two.bit.kbundle.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import three.two.bit.kbundle.bundle

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
  }
}
