package edu.temple.coroutineconversion

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val cakeImageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val currentTextView: TextView by lazy {
        findViewById(R.id.currentTextView)
    }

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.revealButton).setOnClickListener {
            coroutineScope.launch {
                revealCake()
            }
        }
    }

    private suspend fun revealCake() {
        for (i in 0 until 100) {
            withContext(Dispatchers.Main) {
                currentTextView.text = String.format(Locale.getDefault(), "Current opacity: %d", i)
                cakeImageView.alpha = i / 100f
            }
            delay(40)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}