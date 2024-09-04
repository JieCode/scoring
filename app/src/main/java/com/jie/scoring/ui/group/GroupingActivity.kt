package com.jie.scoring.ui.group

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jie.scoring.base.BaseActivity
import com.jie.scoring.databinding.ActivityGroupingBinding

class GroupingActivity : BaseActivity() {
    private lateinit var binding: ActivityGroupingBinding
    private var groupList = ArrayList<GroupAdapter.GroupItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {

        binding.rvGrouping.adapter = GroupAdapter(this, groupList)
        binding.rvGrouping.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvGrouping.setHasFixedSize(true)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tvGrouping.setOnClickListener {
            if (groupList.isNotEmpty()){
                groupList.clear()
                binding.rvGrouping.adapter?.notifyDataSetChanged()
            }
            var totalMember = binding.etMemberTotal.text.toString().toInt()
            if (binding.cbDoubles.isChecked) {
                // 双打
                if (totalMember > 2) {
                    // 两两一组随机打乱
                    var memberList = ArrayList<Int>()
                    for (i in 1..totalMember) {
                        memberList.add(i)
                    }
                    // 打乱
                    memberList.shuffle()
                    // 随机分组
                    var groupMember = ArrayList<Int>()
                    for (i in 0 until memberList.size) {
                        groupMember.add(memberList[i])
                        if (i % 2 == 1) {
                            var groupItem = GroupAdapter.GroupItem()
                            groupItem.member1 = groupMember[0]
                            groupItem.member2 = groupMember[1]
                            groupList.add(groupItem)
                            groupMember.clear()
                        }
                    }
                    if (groupMember.size == 1) {
                        var groupItem = GroupAdapter.GroupItem()
                        groupItem.member1 = groupMember[0]
                        groupList.add(groupItem)
                        groupMember.clear()
                    }
                    binding.rvGrouping.adapter?.notifyDataSetChanged()
                }
            } else {
                // 单打 直接排序
                var memberList = ArrayList<Int>()
                for (i in 1..totalMember) {
                    memberList.add(i)
                }
                // 排序
                memberList.sort()
                // 随机分组
                for (i in 0 until memberList.size) {
                    var groupItem = GroupAdapter.GroupItem()
                    groupItem.member1 = memberList[i]
                    groupList.add(groupItem)
                }
                binding.rvGrouping.adapter?.notifyDataSetChanged()
            }
        }
    }

    companion object {
        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, GroupingActivity::class.java))
        }
    }
}