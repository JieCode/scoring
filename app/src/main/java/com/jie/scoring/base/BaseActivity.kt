package com.jie.scoring.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @CreateDate: 2024/7/11
 * @author: jingjie
 * @desc:
 */
open class BaseActivity: AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mActivity = this
        setFullScreen()
    }

    /**
     * 设置全屏, 状态栏背景透明
     */
    private fun setFullScreen() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        window.statusBarColor = resources.getColor(android.R.color.transparent)
    }
}