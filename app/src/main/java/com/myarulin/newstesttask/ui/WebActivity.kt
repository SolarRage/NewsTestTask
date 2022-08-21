package com.myarulin.newstesttask.ui

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.setViewTreeOnBackPressedDispatcherOwner
import androidx.appcompat.app.AppCompatActivity
import com.myarulin.newstesttask.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle: Bundle? = intent.extras
        val url: String? = intent.getStringExtra("url")

        binding.apply {
            web.webViewClient = WebViewClient()
            web.loadUrl(url.toString())
            web.settings.javaScriptEnabled = true
            web.settings.setSupportZoom(true)
            btnBack.setOnClickListener{
                onBackPressed()
            }
            tvBack.setOnClickListener{
                onBackPressed()
            }
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
