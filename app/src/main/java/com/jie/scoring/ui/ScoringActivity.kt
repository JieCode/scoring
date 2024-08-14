package com.jie.scoring.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jie.scoring.databinding.ActivityScoringBinding

class ScoringActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoringBinding
    private var mRedScore = 0
    private var mBlueScore = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoringBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_scoring)// 使用R.layout.activity_scoring导致按钮无法点击
        setFullScreen()
        initViews()
//        initListener()
    }

    /**
     * 设置全屏, 状态栏背景透明
     */
    private fun setFullScreen() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        window.statusBarColor = resources.getColor(android.R.color.transparent)
    }

    private fun initViews() {
        // 点击减少红方分数
        binding.tvRedReduce.setOnClickListener {
            if (mRedScore > 0) {
                mRedScore--
                binding.tvRedScore.text = mRedScore.toString()
            }
        }
        // 点击增加红方分数
        binding.tvRedAdd.setOnClickListener {
            mRedScore++
            binding.tvRedScore.text = mRedScore.toString()
        }
        // 点击重置分数
        binding.tvReset.setOnClickListener {
            mRedScore = 0
            mBlueScore = 0
            binding.tvRedScore.text = mRedScore.toString()
            binding.tvBlueScore.text = mBlueScore.toString()
        }
        // 点击减少蓝方分数
        binding.tvBlueReduce.setOnClickListener {
            if (mBlueScore > 0) {
                mBlueScore--
                binding.tvBlueScore.text = mBlueScore.toString()
            }
        }
        // 点击增加蓝方分数
        binding.tvBlueAdd.setOnClickListener {
            mBlueScore++
            binding.tvBlueScore.text = mBlueScore.toString()
        }
    }


    private fun initListener() {
        binding.tvRedScore.setOnClickListener {
            mRedScore++
            binding.tvRedScore.text = mRedScore.toString()
        }
        binding.tvBlueScore.setOnClickListener {
            mBlueScore++
            binding.tvBlueScore.text = mBlueScore.toString()
        }
        // 长按重置
        binding.tvRedScore.setOnLongClickListener {
            showResetScoreDialog()
            true
        }
        // 长按重置
        binding.tvBlueScore.setOnLongClickListener {
            showResetScoreDialog()
            true
        }
    }

    private fun showResetScoreDialog() {

    }

    private fun resetScore() {
        mRedScore = 0
        mBlueScore = 0
        binding.tvRedScore.text = mRedScore.toString()
        binding.tvBlueScore.text = mBlueScore.toString()
    }
}