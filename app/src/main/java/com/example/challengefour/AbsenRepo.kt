package com.example.challengefour

import android.content.Context

class AbsenRepo(private val context: Context) {
    val myDatabase = AbsenDatabase.getInstance(context)
    suspend fun getAllAbsen(): List<DaftarJam>? {
        return myDatabase?.absenDao()?.getAllAbsen()
    }
    suspend fun insertAbsen(daftarJam: DaftarJam): Long? {
        return myDatabase?.absenDao()?.insertAbsen(daftarJam)
    }
    suspend fun deleteAbsen(daftarJam: DaftarJam): Int? {
        return myDatabase?.absenDao()?.deleteAbsen(daftarJam)
    }
    suspend fun editAbsen(daftarJam: DaftarJam): Int? {
        return myDatabase?.absenDao()?.deleteAbsen(daftarJam)
    }

}