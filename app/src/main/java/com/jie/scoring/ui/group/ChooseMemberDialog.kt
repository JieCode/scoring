package com.jie.scoring.ui.group

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jie.scoring.R
import com.jie.scoring.data.Member
import com.jie.scoring.data.MemberViewModel
import com.jie.scoring.databinding.DialogChooseMemberBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @CreateDate: 2024/9/3
 * @author: jingjie
 * @desc: 选择成员对话框，可以选择、添加、修改、删除成员
 */
class ChooseMemberDialog {
    companion object {
        fun createDialog(
            mContext: Context, viewModel: MemberViewModel, lifecycleScope: LifecycleCoroutineScope,
            mSelectedList: MutableList<Member>, onChooseMemberListener: OnChooseMemberListener
        ): Dialog {
            var view = LayoutInflater.from(mContext).inflate(R.layout.dialog_choose_member, null)
            var dialog = Dialog(mContext, R.style.dialog)
            var binding = DialogChooseMemberBinding.bind(view)
            dialog.setCanceledOnTouchOutside(true)
            dialog.setCancelable(true)
            dialog.setContentView(view)
            // 设置 Dialog 的宽度
            dialog.window?.setLayout(
                Resources.getSystem().displayMetrics.widthPixels / 2, // 调整宽度为屏幕的一半
                Resources.getSystem().displayMetrics.widthPixels / 2 * 3 / 4
            )
            binding.tvCancel.setOnClickListener {
                dialog.dismiss()
            }
            lifecycleScope.launchWhenStarted {
                val memberList = withContext(Dispatchers.IO) {
                    viewModel.getAllMembers()
                }
                binding.tvEmpty.visibility = if (memberList.isEmpty()) View.VISIBLE else View.GONE
                binding.rvMember.adapter = MemberAdapter(mContext, memberList, mSelectedList, object : MemberAdapter.OnItemClickListener {
                    override fun onChoose(position: Int, member: Member) {
                        var isSelected = mSelectedList.contains(member)
                        for (selectMember in mSelectedList) {
                            if (member.id == selectMember.id) {
                                mSelectedList.remove(member)
                                isSelected = true
                                break
                            }
                        }
                        if (!isSelected) {
                            mSelectedList.add(member)
                        }
                        binding.rvMember.adapter?.notifyItemChanged(position)
                    }

                    override fun onItemLongClick(position: Int, member: Member) {
                        MemberMenuDialog.createDialog(mContext, member, object : MemberMenuDialog.OnMemberMenuClickListener {
                            override fun onModifyClick(member: Member) {
                                AddMemberDialog.showDialog(mContext, member, object : AddMemberDialog.OnAddMemberListener {
                                    override fun onAddMember(member: Member) {
                                        viewModel.updateMember(member, object : MemberViewModel.UpdateMemberListener {
                                            override fun onUpdateMember(member: Member) {
                                                memberList[position].gender = member.gender
                                                memberList[position].username = member.username
                                                binding.tvEmpty.visibility = if (memberList.isEmpty()) View.VISIBLE else View.GONE
                                                binding.rvMember.adapter?.notifyItemChanged(position)
                                            }
                                        })
                                    }
                                })
                            }

                            override fun onDeleteClick(member: Member) {
                                DeleteMemberDialog.createDialog(mContext, member, object : DeleteMemberDialog.OnDeleteMemberListener {
                                    override fun onMemberDelete() {
                                        viewModel.deleteMember(member)
                                        memberList.remove(member)
                                        binding.tvEmpty.visibility = if (memberList.isEmpty()) View.VISIBLE else View.GONE
                                        binding.rvMember.adapter?.notifyItemRemoved(position)
                                    }
                                }).show()
                            }

                        }).show()
                    }

                })
                binding.rvMember.layoutManager = GridLayoutManager(mContext, 3)
                binding.tvConfirm.setOnClickListener {
                    dialog.dismiss()
                    onChooseMemberListener.onChoose(mSelectedList)
                }
                binding.tvAdd.setOnClickListener {
                    AddMemberDialog.showDialog(mContext, object : AddMemberDialog.OnAddMemberListener {
                        override fun onAddMember(member: Member) {
                            viewModel.insertMember(member, object : MemberViewModel.InsertMemberListener {
                                override fun onMemberInsert(member: Member) {
                                    memberList.add(member)
                                    binding.tvEmpty.visibility = if (memberList.isEmpty()) View.VISIBLE else View.GONE
                                    binding.rvMember.adapter?.notifyItemInserted(memberList.size - 1)
                                }
                            })
                        }
                    })
                }
            }
            return dialog
        }
    }

    interface OnChooseMemberListener {
        fun onChoose(selectedMembers: MutableList<Member>)
    }
}