package com.jie.scoring.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * @CreateDate: 2024/9/2
 * @author: jingjie
 * @desc:
 */
@Dao
interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(member: Member):Long

    @Update
    suspend fun update(member: Member)

    @Delete
    suspend fun delete(member: Member)

    /**
     * 获取全部用户
     */
    @Query("SELECT * FROM member Order By id ASC")
    fun getAllMembers(): MutableList<Member>

    /**
     * 根据性别获取用户
     */
    @Query("SELECT * FROM member WHERE gender=:gender Order By username ASC")
    fun getMembersByByGender(gender: String): MutableList<Member>

    @Query("SELECT * FROM member WHERE id=:id")
    fun getMemberById(id: Int): Member

    suspend fun deleteMemberById(id: Int) {
        delete(getMemberById(id))
    }
}