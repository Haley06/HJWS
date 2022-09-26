package change.aaa.rtmpdemo.ble

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import change.aaa.rtmpdemo.R

class HwActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hw)

        findViewById<ImageView>(R.id.back_bbt).setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}