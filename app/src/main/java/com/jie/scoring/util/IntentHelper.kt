package com.jie.scoring.util

import android.app.Activity
import android.content.Intent
import com.jie.scoring.ui.WebViewActivity

/**
 * @CreateDate: 2024/6/28
 * @author: jingjie
 * @desc:
 */
class IntentHelper {
    fun startWebViewActivity(activity: Activity, url: String, title: String) {
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("url", url)
        intent.putExtra("title", title)
        activity.startActivity(intent)
    }
}