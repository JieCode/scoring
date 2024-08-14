package com.jie.scoring.listener

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener

/**
 * @CreateDate: 2024/7/29
 * @author: jingjie
 * @desc: 按钮双击事件处理
 */
open class OnDoubleClickListener(doubleClickListener: DoubleClickCallback?) : OnTouchListener {
    private var count = 0       // 点击次数
    private var firstClick: Long = 0  //第一次点击事件
    private var secondClick: Long = 0 //第二次点击事件
    private var totalTime = 500    // 两次点击事件时间间隔，单位毫秒
    private var mCallBack: DoubleClickCallback? = doubleClickListener

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (MotionEvent.ACTION_DOWN == event?.action) {
            count++
            if (count == 1) { // 第一次点击
                firstClick = System.currentTimeMillis()
            } else if (count == 2) { // 第二次点击
                secondClick = System.currentTimeMillis()
                if (secondClick - firstClick < totalTime) { // 如果两次点击时间小于totalTime，则认为是双击事件
                    mCallBack?.onDoubleClick()
                    count = 0
                    firstClick = 0
                } else {
                    firstClick = secondClick
                    count = 1
                }
                secondClick = 0
            }
        }
        return true
    }

    interface DoubleClickCallback {
        fun onDoubleClick()
    }
}