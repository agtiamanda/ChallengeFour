package com.example.challengefour

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ContentView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.challengefour.databinding.FragmentInputAbsensiBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*


class InputAbsensi : DialogFragment() {

    private var _binding: FragmentInputAbsensiBinding? = null
    private val binding get() = _binding!!

    private var mDb: AbsenDatabase?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputAbsensiBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = AbsenDatabase.getInstance(requireContext())


        val kalender = Calendar.getInstance()
        val pilihTanggal = DatePickerDialog.OnDateSetListener(){view,year,month,dayOfMonth->
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

        binding.btnSave.setOnClickListener {
            if (binding.etKelas.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Kolom masih kosong", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val objekAbsen = DaftarJam(
                    null,
                    binding.etKelas.text.toString(),
                    binding.etDate.text.toString(),
                    binding.etAbsen.text.toString()
                )
                GlobalScope.async {
                    val hasil = mDb?.absenDao()?.insertAbsen(objekAbsen)
                    runBlocking{
                        if(hasil != 0.0.toLong()){
                            Toast.makeText(context, "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "Gagal Ditambahkan", Toast.LENGTH_SHORT).show()
                        }
                    }
                    dialog?.dismiss()
                    //jika udh selesai ditambahin, langsung ditutup
                }



            }
        }

    }

    private fun updateLabel(kalender: Calendar) {
        val format = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(format, Locale.CHINA)
        binding.etDate.setText(sdf.format(kalender.time))

    }


}