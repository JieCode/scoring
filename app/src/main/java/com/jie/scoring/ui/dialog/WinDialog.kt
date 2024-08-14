package com.jie.scoring.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.jie.scoring.R
import com.jie.scoring.databinding.DialogResetBinding
import com.jie.scoring.databinding.DialogWinBinding

/**
 * @CreateDate: 2024/7/8
 * @author: jingjie
 * @desc:
 */
class WinDialog {
    companion object {
        fun showDialog(mContext: Context, scoreRed:Int,scoreBlue: Int, onResetListener: OnResetListener) {
            var view = LayoutInflater.from(mContext).inflate(R.layout.dialog_win, null)
            var dialog = Dialog(mContext, R.style.dialog)
            var binding = DialogWinBinding.bind(view)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            binding.btnSubmit.setOnClickListener {
                dialog.dismiss()
                onResetListener.onReset()
            }
            dialog.setContentView(view)
            dialog.show()
            if (scoreRed > scoreBlue) {
                binding.tvWin.text = "红方胜利"
                binding.tvWin.setTextColor(mContext.getColor(R.color.color_90_red))
                binding.tvScore.text = "红方:${getScoreText(scoreRed)} VS 蓝方:${getScoreText(scoreBlue)}"
                binding.tvScore.setTextColor(mContext.getColor(R.color.color_90_red))
                binding.btnSubmit.setBackgroundColor(mContext.getColor(R.color.color_90_red))
            } else if (scoreBlue > scoreRed){
                binding.tvWin.text = "蓝方胜利"
                binding.tvWin.setTextColor(mContext.getColor(R.color.color_90_blue))
                binding.tvScore.text = "红方:${getScoreText(scoreRed)} VS 蓝方:${getScoreText(scoreBlue)}"
                binding.tvScore.setTextColor(mContext.getColor(R.color.color_90_blue))
                binding.btnSubmit.setBackgroundColor(mContext.getColor(R.color.color_90_blue))
            } else {
                binding.tvWin.text = "平局"
                binding.tvWin.setTextColor(mContext.getColor(R.color.color_fff100))
                binding.tvScore.text = "红方:${getScoreText(scoreRed)} VS 蓝方:${getScoreText(scoreBlue)}"
                binding.tvScore.setTextColor(mContext.getColor(R.color.color_fff100))
                binding.btnSubmit.text = "重新开始"
                binding.btnSubmit.setBackgroundColor(mContext.getColor(R.color.color_fff100))
            }
        }

        private fun getScoreText(score: Int): String {
            return if (score < 10) {
                "0$score"
            } else {
                score.toString()
            }
        }

    }



    interface OnResetListener{
        fun onReset()
    }
}