package com.jie.scoring

import android.app.Application
import com.jie.scoring.data.Member
import com.jie.scoring.data.MemberRoomDatabase
import com.jie.scoring.ui.group.Group2Adapter

/**
 * @CreateDate: 2024/9/2
 * @author: jingjie
 * @desc:
 */
class MyApplication : Application() {
    val memberDatabase: MemberRoomDatabase by lazy { MemberRoomDatabase.getDatabase(this) }

    companion object {
        var selectedMembers: MutableList<Member> = mutableListOf()
        var groupList: MutableList<Group2Adapter.GroupItem> = mutableListOf()
    }
}