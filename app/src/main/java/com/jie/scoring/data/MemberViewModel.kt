package com.jie.scoring.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * @CreateDate: 2024/9/2
 * @author: jingjie
 * @desc:
 */
class MemberViewModel(private val memberDao: MemberDao) : ViewModel() {
    fun insertMember(member: Member) {
        viewModelScope.launch {
            memberDao.insert(member)
        }
    }

    fun updateMember(member: Member, updateMemberListener: UpdateMemberListener) {
        viewModelScope.launch {
            memberDao.update(member)
            updateMemberListener.onUpdateMember(member)
        }
    }
    fun insertMember(member: Member,insertMemberListener: InsertMemberListener) {
        viewModelScope.launch {
            val id = memberDao.insert(member)
            member.id = id.toInt()
            insertMemberListener.onMemberInsert(member)
        }
    }

    fun getAllMembers(): MutableList<Member> {
        return memberDao.getAllMembers()
    }

    fun deleteMember(member: Member) {
        viewModelScope.launch {
            memberDao.delete(member)
        }
    }

    fun deleteMemberById(id: Int) {
        viewModelScope.launch {
            memberDao.deleteMemberById(id)
        }
    }

    interface GetAllMemberListener {
        fun onGetAllMember(memberList: MutableList<Member>)
    }

    interface InsertMemberListener {
        fun onMemberInsert(member: Member)
    }

    interface UpdateMemberListener {
        fun onUpdateMember(member: Member)
    }
}
/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class MemberViewModelFactory(private val memberDao: MemberDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel(memberDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}