package com.jie.scoring.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jie.scoring.R
import com.jie.scoring.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://www.sohu.com/a/197767894_99894383")
    }

}