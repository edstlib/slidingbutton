package id.co.edtslib.slidingbutton.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import id.co.edtslib.slidingbutton.SlidingButton
import id.co.edtslib.slidingbutton.SlidingButtonDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tvActivate).setOnClickListener {
            val slidingButton = findViewById<SlidingButton>(R.id.slidingButton)
            slidingButton.isActivated = true
        }

        findViewById<TextView>(R.id.tvReset).setOnClickListener {
            val slidingButton = findViewById<SlidingButton>(R.id.slidingButton)
            slidingButton.reset()
        }

        findViewById<TextView>(R.id.tvDeactive).setOnClickListener {
            val slidingButton = findViewById<SlidingButton>(R.id.slidingButton)
            slidingButton.isActivated = false
        }

        val slidingButton = findViewById<SlidingButton>(R.id.slidingButton)
        slidingButton.delegate = object : SlidingButtonDelegate {
            override fun onCompleted() {
                Toast.makeText(this@MainActivity, "Add some action on here", Toast.LENGTH_SHORT).show()
            }
        }

    }
}