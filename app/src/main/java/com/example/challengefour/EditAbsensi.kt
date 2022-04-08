package com.example.challengefour

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.challengefour.databinding.FragmentEditAbsensiBinding


class EditAbsensi() : DialogFragment() {
    lateinit var listAbsen: DaftarJam
    private var mDb: AbsenDatabase?=null
    constructor(listAbsen:DaftarJam): this(){
        this.listAbsen= listAbsen

    }
    private var _binding :FragmentEditAbsensiBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditAbsensiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = AbsenDatabase.getInstance(requireContext())
        binding.etKelas.setText(listAbsen.kelas)
        binding.etDate.setText(listAbsen.tanggalAbsen)
        binding.etAbsen.setText(listAbsen.jamAbsen)
    }


}