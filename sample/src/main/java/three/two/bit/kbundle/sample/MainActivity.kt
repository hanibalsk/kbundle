package three.two.bit.kbundle.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import three.two.bit.kbundle.bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      val bundle = bundle {
        "IntKey" to 4
        "LongKey" to 7L
        "StringKey" to "Some text"
      }

      println(bundle)
    }
}