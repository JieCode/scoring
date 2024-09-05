package com.jie.scoring.ui.group

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jie.scoring.MyApplication
import com.jie.scoring.base.BaseActivity
import com.jie.scoring.data.Member
import com.jie.scoring.data.MemberViewModel
import com.jie.scoring.data.MemberViewModelFactory
import com.jie.scoring.databinding.ActivityGrouping2Binding

class Grouping2Activity : BaseActivity() {
    private var chooseDialog: Dialog? = null
    private lateinit var binding: ActivityGrouping2Binding
//    val memberDatabase: MemberRoomDatabase by lazy { MemberRoomDatabase.getDatabase(this) }

    //    private val viewModel: MemberViewModel by lazy { MemberViewModel(MemberRoomDatabase.getDatabase(this).memberDao()) }
    private val viewModel: MemberViewModel by lazy {
        MemberViewModelFactory(
            (application as MyApplication).memberDatabase.memberDao()
        ).create(MemberViewModel::class.java)
    }
    private var selectedMemberList: MutableList<Member> = mutableListOf()
    private var groupList: MutableList<Group2Adapter.GroupItem> = mutableListOf()

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent(activity, Grouping2Activity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrouping2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        selectedMemberList.addAll(MyApplication.selectedMembers)
        groupList.addAll(MyApplication.groupList)
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.tvChooseMember.setOnClickListener {
            showChooseMemberDialog()
        }
        binding.rvMember.layoutManager = GridLayoutManager(this, 3)
        binding.rvMember.adapter = MemberAdapter(this, selectedMemberList, selectedMemberList, object : MemberAdapter.OnItemClickListener {
            override fun onChoose(position: Int, member: Member) {
            }

            override fun onItemLongClick(position: Int, member: Member) {
            }

        })
        binding.tvGrouping.setOnClickListener {
            if (selectedMemberList.size < 2) {
                Toast.makeText(mContext, "至少选择两个成员", Toast.LENGTH_SHORT).show()
            } else {
                groupList.clear()
                binding.rvGrouping.adapter?.notifyDataSetChanged()
                // 分组
                if (binding.cbMixedDoubles.isChecked) {
                    // 男女混双，一男一女优先，剩余人随机
                    var memberMaleList = ArrayList<Member>()
                    var memberFemaleList = ArrayList<Member>()
                    for (member in selectedMemberList) {
                        if (member.gender == "M") {
                            memberMaleList.add(member)
                        }
                    }
                    memberMaleList.shuffle()
                    for (member in selectedMemberList) {
                        if (member.gender == "F") {
                            memberFemaleList.add(member)
                        }
                    }
                    memberFemaleList.shuffle()
                    if (memberMaleList.size > memberFemaleList.size) {
                        for (i in 0 until memberFemaleList.size) {
                            var groupItem = Group2Adapter.GroupItem()
                            groupItem.member1 = memberMaleList[i]
                            groupItem.member2 = memberFemaleList[i]
                            groupList.add(groupItem)
                        }
                        if ((memberMaleList.size - memberFemaleList.size) % 2 == 1) {
                            memberMaleList.add(Member(id = 0, "", ""))
                        }
                        for (i in memberFemaleList.size until memberMaleList.size step 2) {
                            var groupItem = Group2Adapter.GroupItem()
                            groupItem.member1 = memberMaleList[i]
                            groupItem.member2 = memberMaleList[i + 1]
                            groupList.add(groupItem)
                        }
                    } else {
                        for (i in 0 until memberMaleList.size) {
                            var groupItem = Group2Adapter.GroupItem()
                            groupItem.member1 = memberMaleList[i]
                            groupItem.member2 = memberFemaleList[i]
                            groupList.add(groupItem)
                        }
                        if ((memberFemaleList.size - memberMaleList.size) % 2 == 1) {
                            memberFemaleList.add(Member(id = 0, "", ""))
                        }
                        for (i in memberMaleList.size until memberFemaleList.size step 2) {
                            var groupItem = Group2Adapter.GroupItem()
                            groupItem.member1 = memberFemaleList[i]
                            groupItem.member2 = memberFemaleList[i + 1]
                            groupList.add(groupItem)
                        }
                    }
                } else {
                    // 随机分组，两人一组
                    var memberList = ArrayList<Member>()
                    memberList.addAll(selectedMemberList)
                    memberList.shuffle()
                    if (memberList.size % 2 == 1) {
                        memberList.add(Member(id = 0, "", ""))
                    }
                    for (i in 0 until memberList.size step 2) {
                        var groupItem = Group2Adapter.GroupItem()
                        groupItem.member1 = memberList[i]
                        groupItem.member2 = memberList[i + 1]
                        groupList.add(groupItem)
                    }
                }
                binding.rvGrouping.adapter?.notifyDataSetChanged()
                MyApplication.selectedMembers.clear()
                MyApplication.selectedMembers.addAll(selectedMemberList)
                MyApplication.groupList.clear()
                MyApplication.groupList.addAll(groupList)
            }
        }

        binding.rvGrouping.adapter = Group2Adapter(this, groupList)
        binding.rvGrouping.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun showChooseMemberDialog() {
        if (chooseDialog == null) {
            chooseDialog = ChooseMemberDialog.createDialog(mContext, viewModel, lifecycleScope, selectedMemberList, object : ChooseMemberDialog.OnChooseMemberListener {
                override fun onChoose(selectedMembers: MutableList<Member>) {
                    binding.rvMember.adapter?.notifyDataSetChanged()
                }
            })
        }
        chooseDialog!!.show()
    }
}