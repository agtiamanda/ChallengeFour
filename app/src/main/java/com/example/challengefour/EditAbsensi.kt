package com.example.challengefour

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.challengefour.databinding.FragmentEditAbsensiBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*


class EditAbsensi() : DialogFragment() {
    lateinit var listAbsen: DaftarJam
    private var mDb: AbsenDatabase?=null

    lateinit var repo: AbsenRepo

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

        repo = AbsenRepo(requireContext())
        mDb = AbsenDatabase.getInstance(requireContext())

        val kalender = Calendar.getInstance()
        val pilihTanggal = DatePickerDialog.OnDateSetListener(){ view, year, month, dayOfMonth->
            kalender.set(Calendar.YEAR,year)
            kalender.set(Calendar.MONTH,month)
            kalender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateLabel(kalender)

        }

        binding.etDate.setOnClickListener{
            DatePickerDialog(requireContext(), pilihTanggal, kalender.get(Calendar.YEAR),
                kalender.get(Calendar.MONTH),
                kalender.get(Calendar.DAY_OF_MONTH)).show()

        }

        if(this::listAbsen.isInitialized){
            binding.etKelas.setText(listAbsen.kelas)
            binding.etDate.setText(listAbsen.tanggalAbsen)
            binding.etAbsen.setText(listAbsen.jamAbsen)
        }
        binding.btnUpdate.setOnClickListener{
            val objekAbsen = listAbsen
            objekAbsen.kelas = binding.etKelas.text.toString()
            objekAbsen.tanggalAbsen = binding.etDate.text.toString()
            objekAbsen.jamAbsen = binding.etAbsen.text.toString()

            GlobalScope.async {
                val result = repo.editAbsen(objekAbsen)
                runBlocking(Dispatchers.Main) {
                    if(result!=0){
                        Toast.makeText(requireContext(), "data telah diubah", Toast.LENGTH_SHORT).show()
//                        findNavController().navigate(R.id.action_editAbsensi_to_homeFragment)
                    }else{
                        Toast.makeText(requireContext(), "data belum terubah", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            dialog?.dismiss()
        }


    }

    private fun updateLabel(kalender: Calendar) {
            val format = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(format, Locale.CHINA)
            binding.etDate.setText(sdf.format(kalender.time))

        }

    }

