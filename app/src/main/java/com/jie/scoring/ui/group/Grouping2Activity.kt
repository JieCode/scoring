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
    private var selectedMemberList: ArrayList<Member> = ArrayList<Member>()
    private var groupList: ArrayList<Group2Adapter.GroupItem> = ArrayList<Group2Adapter.GroupItem>()

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
        selectedMemberList.addAll(MyApplication.selectedMemberList)
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
                    mixedDoublesGrouping()
                } else {
                    groupList.addAll(
                        randomGrouping(
                            if (selectedMemberList.size >= 8) {
                                16
                            } else {
                                8
                            }, ArrayList()
                        )
                    )
                }
                binding.rvGrouping.adapter?.notifyDataSetChanged()
                MyApplication.selectedMemberList.clear()
                MyApplication.selectedMemberList.addAll(selectedMemberList)
                MyApplication.groupList.clear()
                MyApplication.groupList.addAll(groupList)
            }
        }

        binding.rvGrouping.adapter = Group2Adapter(this, groupList)
        binding.rvGrouping.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    /**
     * 混合双打分组
     * 人数>8分32组，16轮PK
     * 人数<=8分16组，8轮PK
     */
    private fun mixedDoublesGrouping() {
        // 男女混双，一男一女优先，剩余人随机
        var memberMaleList = ArrayList<Member>()
        var memberFemaleList = ArrayList<Member>()
        for (member in selectedMemberList) {
            if (member.gender == "M") {
                memberMaleList.add(member)
            }
        }
        for (member in selectedMemberList) {
            if (member.gender == "F") {
                memberFemaleList.add(member)
            }
        }

        var groupMaleList = ArrayList<Member>()
        var groupFemaleList = ArrayList<Member>()
        groupMaleList.addAll(
            getRandomMemberList(
                ArrayList<Member>(), memberMaleList, if (selectedMemberList.size >= 8) {
                    32
                } else {
                    16
                }
            )
        )
        groupFemaleList.addAll(
            getRandomMemberList(
                ArrayList<Member>(), memberFemaleList, if (selectedMemberList.size >= 8) {
                    32
                } else {
                    16
                }
            )
        )
        for (i in 0 until groupMaleList.size step 2) {
            var groupItem = Group2Adapter.GroupItem()
            groupItem.member1 = groupMaleList[i]
            groupItem.member2 = groupFemaleList[i]
            groupItem.member3 = groupMaleList[i + 1]
            groupItem.member4 = groupFemaleList[i + 1]
            groupList.add(groupItem)
        }
    }

    /**
     * 获取随机人员列表
     * @param members 人员列表
     * @param maxGroup 最大分组数
     */
    private fun getRandomMemberList(members: ArrayList<Member>, selectedMembers: ArrayList<Member>, maxGroup: Int): List<Member> {
        if (members.size >= maxGroup) {
            return members.subList(0, maxGroup)
        }
        val memberList = ArrayList<Member>()
        memberList.addAll(selectedMembers)
        memberList.shuffle()
        if (memberList.size > 1 && members.size > 0) {
            if (members[members.size - 1].id == memberList[0].id) {
                val temp = memberList[0]
                memberList.removeAt(0)
                memberList.add(1, temp)
            }
        }
        members.addAll(memberList)
        return getRandomMemberList(members, selectedMembers, maxGroup)
    }

    private fun randomGrouping(maxGroup: Int, groupList: ArrayList<Group2Adapter.GroupItem>):List<Group2Adapter.GroupItem> {
        var memberList = ArrayList<Member>()
        if (groupList.size >= maxGroup) {
            return groupList.subList(0, maxGroup)
        }
        memberList.addAll(selectedMemberList)
        memberList.shuffle()
        if (memberList.size > 4) {
            val remainder = memberList.size % 4
            val groupMemberList = ArrayList<Member>()
            groupMemberList.addAll(memberList.subList(0, memberList.size - remainder))
            groupMemberList.shuffle()
            memberList.addAll(groupMemberList.subList(0, 4-remainder))
        }
        for (i in 0 until memberList.size step 4) {
            var groupItem = Group2Adapter.GroupItem()
            groupItem.member1 = memberList[i]
            groupItem.member2 = memberList[i + 1]
            groupItem.member3 = memberList[i + 2]
            groupItem.member4 = memberList[i + 3]
            groupList.add(groupItem)
        }
        return randomGrouping(maxGroup, groupList)
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