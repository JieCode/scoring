package com.jie.scoring.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @CreateDate: 2024/9/2
 * @author: jingjie
 * @desc:
 */
@Database(entities = [Member::class], version = 1, exportSchema = false)
abstract class MemberRoomDatabase: RoomDatabase() {
    abstract fun memberDao(): MemberDao
    companion object {
        @Volatile
        private var INSTANCE: MemberRoomDatabase? = null
        fun getDatabase(context: Context): MemberRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemberRoomDatabase::class.java,
                    "member_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}