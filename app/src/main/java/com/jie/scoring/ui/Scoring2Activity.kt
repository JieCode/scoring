package com.jie.scoring.ui

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.jie.scoring.bean.RedBlueScore
import com.jie.scoring.databinding.ActivityScoring2Binding
import com.jie.scoring.listener.OnDoubleClickListener
import com.jie.scoring.ui.dialog.ResetDialog
import com.jie.scoring.ui.dialog.WinDialog
import com.jie.scoring.ui.group.Grouping2Activity
import com.jie.scoring.ui.group.GroupingActivity
import com.jie.scoring.util.AvoidDoubleClick

class Scoring2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityScoring2Binding

    private var redScore = 0
    private var blueScore = 0
    private var scoreList = ArrayList<RedBlueScore>()
    private var resetDialog: Dialog? = null
    var isRedServe: Boolean = true
    var winCount: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoring2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setFullScreen()
        initViews()
    }

    /**
     * 设置全屏, 状态栏背景透明
     */
    private fun setFullScreen() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        window.statusBarColor = resources.getColor(android.R.color.transparent)
    }

    //    撤销会出现数字显示错误问题，怀疑是因为操作过快
//    界面弹窗样式很丑，需要修改
    @SuppressLint("ClickableViewAccessibility")
    private fun initViews() {
        resetScoreList()
        binding.tvRedScore.setOnTouchListener(OnDoubleClickListener(object : OnDoubleClickListener.DoubleClickCallback {
            override fun onDoubleClick() {
                handler.sendEmptyMessage(MSG_RED_ADD)
            }
        }))
        binding.tvBlueScore.setOnTouchListener(OnDoubleClickListener(object : OnDoubleClickListener.DoubleClickCallback {
            override fun onDoubleClick() {
                handler.sendEmptyMessage(MSG_BLUE_ADD)
            }
        }))
        binding.ivRedo.setOnClickListener {
            if (AvoidDoubleClick.responseDoubleClick(AvoidDoubleClick.KEY_CLICK_REDO)) {
                handler.sendEmptyMessage(MSG_REDO)
            }
        }
        binding.ivReset.setOnClickListener {
            showResetDialog()
        }
        binding.ivGroup.setOnClickListener{
            if (AvoidDoubleClick.responseDoubleClick(AvoidDoubleClick.KEY_CLICK_GROUP)) {
                Grouping2Activity.startActivity(this)
            }
        }
    }

    private var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: android.os.Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_RED_ADD -> {
                    redScore++
                    binding.tvRedScore.text = getScoreText(redScore)
                    if (isRedServe) {
                        winCount++
                    } else {
                        winCount = 1
                    }
                    isRedServe = true
                    scoreList.add(RedBlueScore(redScore, blueScore, true, winCount))
                    binding.tvRedScoreCount.text = "连胜${winCount}次"
                    binding.tvRedScoreCount.visibility = if (isRedServe && winCount > 0) View.VISIBLE else View.INVISIBLE
                    binding.tvBlueScoreCount.visibility = if (!isRedServe && winCount > 0) View.VISIBLE else View.INVISIBLE
                    isWin()
                }

                MSG_BLUE_ADD -> {
                    blueScore++
                    binding.tvBlueScore.text = getScoreText(blueScore)
                    if (!isRedServe) {
                        winCount++
                    } else {
                        winCount = 1
                    }
                    isRedServe = false
                    scoreList.add(RedBlueScore(redScore, blueScore, false, winCount))
                    binding.tvBlueScoreCount.text = "连胜${winCount}次"
                    binding.tvRedScoreCount.visibility = if (isRedServe && winCount > 0) View.VISIBLE else View.INVISIBLE
                    binding.tvBlueScoreCount.visibility = if (!isRedServe && winCount > 0) View.VISIBLE else View.INVISIBLE
                    isWin()
                }

                MSG_REDO -> {
                    if (scoreList.size > 0) {
                        val score = scoreList[scoreList.size - 1]
                        redScore = score.redScore
                        blueScore = score.blueScore
                        binding.tvRedScore.text = getScoreText(redScore)
                        binding.tvBlueScore.text = getScoreText(blueScore)
                        isRedServe = score.isRedServe
                        winCount = score.winCount
                        if (isRedServe) {
                            binding.tvRedScoreCount.text = "连胜${winCount}次"
                        } else {
                            binding.tvBlueScoreCount.text = "连胜${winCount}次"
                        }
                        binding.tvRedScoreCount.visibility = if (isRedServe && winCount > 0) View.VISIBLE else View.INVISIBLE
                        binding.tvBlueScoreCount.visibility = if (!isRedServe && winCount > 0) View.VISIBLE else View.INVISIBLE
                        scoreList.remove(score)
                    } else {
                        reset()
                    }
                }
            }
        }
    }

    fun getScoreText(score: Int): String {
        return if (score < 10) {
            "0$score"
        } else {
            score.toString()
        }
    }

    /**
     * 红方或蓝方得分21分且大于对方2分时, 胜利
     */
    private fun isWin() {
        if (redScore >= 21 || blueScore >= 21) {
            if (redScore >= blueScore + 2 || redScore == 30 || blueScore >= redScore + 2 || blueScore == 30) {
                WinDialog.showDialog(this, redScore, blueScore, object : WinDialog.OnResetListener {
                    override fun onReset() {
                        reset()
                    }
                })
            }
        }
    }

    private fun showResetDialog() {
        if (resetDialog == null) {
            resetDialog = ResetDialog.createDialog(this, object : ResetDialog.OnResetListener {
                override fun onReset() {
                    reset()
                }
            })
        }
        resetDialog!!.show()
    }

    /**
     * 重置
     */
    private fun reset() {
        redScore = 0
        blueScore = 0
        binding.tvRedScore.text = getScoreText(redScore)
        binding.tvBlueScore.text = getScoreText(blueScore)
        scoreList.clear()
        isRedServe = true
        winCount = 0
        binding.tvRedScoreCount.visibility = if (isRedServe && winCount > 0) View.VISIBLE else View.INVISIBLE
        binding.tvBlueScoreCount.visibility = if (!isRedServe && winCount > 0) View.VISIBLE else View.INVISIBLE
        resetScoreList()
    }

    /**
     * 重置评分缓存列表
     */
    private fun resetScoreList() {
        scoreList.add(RedBlueScore(redScore, blueScore, true, 0))
        scoreList.add(RedBlueScore(redScore, blueScore, true, 0))
        scoreList.add(RedBlueScore(redScore, blueScore, true, 0))
        scoreList.add(RedBlueScore(redScore, blueScore, true, 0))
        scoreList.add(RedBlueScore(redScore, blueScore, true, 0))
    }

    companion object {
        const val MSG_RED_ADD = 1001
        const val MSG_BLUE_ADD = 1002
        const val MSG_REDO = 1003
        const val MSG_RESET = 1004
    }

}