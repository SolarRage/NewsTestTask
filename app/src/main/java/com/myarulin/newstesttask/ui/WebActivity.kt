package com.myarulin.newstesttask.ui

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.myarulin.newstesttask.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.apply {
            web.webViewClient = WebViewClient()


            web.loadUrl("https://www.geeksforgeeks.org/")

            web.settings.javaScriptEnabled = true

            web.settings.setSupportZoom(true)
        }
    }

    override fun onBackPressed() {
        binding.apply {
            if (web.canGoBack())
                web.goBack()
            else
                super.onBackPressed()
        }
    }

}
