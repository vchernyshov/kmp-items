package dev.garage.items.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import dev.garage.app.common.hello
import dev.garage.items.app.databinding.ActivityLaunchBinding

class LaunchActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            helloView.text = hello()
        }
    }
}