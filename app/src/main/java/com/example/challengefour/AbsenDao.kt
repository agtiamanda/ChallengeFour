package com.example.challengefour

import androidx.room.*

@Dao
    interface AbsenDao {
        @Query("SELECT * FROM User WHERE username = :username AND password = :password")
        fun signIn(username: String, password: String): Boolean

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun signUp(user: User):Long

        @Query("SELECT * FROM DaftarJam")
        fun getAllAbsen(): List<DaftarJam>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertAbsen(absen: DaftarJam):Long

        @Update
        fun updateAbsen(absen: DaftarJam):Int

        @Delete
        fun deleteAbsen(absen : DaftarJam):Int
    }
