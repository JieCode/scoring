package com.jie.scoring.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
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
    }
}