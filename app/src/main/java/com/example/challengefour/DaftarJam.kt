package com.example.challengefour


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class DaftarJam(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "kelas") var kelas: String,
    @ColumnInfo(name = "tanggal") var tanggalAbsen: String,
    @ColumnInfo(name = "jam") var jamAbsen: String
    ):Parcelable
