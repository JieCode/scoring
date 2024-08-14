package com.jie.scoring.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.jie.scoring.R
import com.jie.scoring.databinding.DialogResetBinding

/**
 * @CreateDate: 2024/7/8
 * @author: jingjie
 * @desc:
 */
class ResetDialog {
    companion object {
        fun createDialog(mContext: Context, onResetListener: OnResetListener): Dialog {
            var view = LayoutInflater.from(mContext).inflate(R.layout.dialog_reset, null)
            var dialog = Dialog(mContext, R.style.dialog)
            var binding = DialogResetBinding.bind(view)
            binding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            binding.btnSubmit.setOnClickListener {
                dialog.dismiss()
                onResetListener.onReset()
            }
            dialog.setContentView(view)

            return dialog
        }
    }

    interface OnResetListener{
        fun onReset()
    }
}