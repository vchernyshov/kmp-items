package dev.garage.items.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.garage.app.common.hello
import dev.garage.items.app.databinding.ActivityLaunchBinding

class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "KMP Items Sample"

        with(binding) {
            helloView.text = hello()
            simpleButton.setOnClickListener {
                startActivity(Intent(this@LaunchActivity, SimpleExampleActivity::class.java))
            }
            payloadButton.setOnClickListener {
                startActivity(Intent(this@LaunchActivity, PayloadExampleActivity::class.java))
            }
        }
    }
}