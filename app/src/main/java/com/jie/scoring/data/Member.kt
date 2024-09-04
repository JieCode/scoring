package com.jie.scoring.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @CreateDate: 2024/9/2
 * @author: jingjie
 * @desc:
 */
@Entity(tableName = "member")
data class Member(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "username")
    var username: String,
    // 性别：男M，女F, 未知U
    @ColumnInfo(name = "gender")
    var gender: String
)