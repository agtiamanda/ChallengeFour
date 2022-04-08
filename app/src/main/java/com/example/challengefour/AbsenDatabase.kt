package com.example.challengefour

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DaftarJam::class, User::class], version = 1)
abstract class AbsenDatabase : RoomDatabase(){
    abstract fun absenDao(): AbsenDao

    companion object{
        private var INSTANCE: AbsenDatabase? = null

        fun getInstance(context: Context): AbsenDatabase? {
            if(INSTANCE == null){
                synchronized(AbsenDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,AbsenDatabase::class.java, "Absen.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}