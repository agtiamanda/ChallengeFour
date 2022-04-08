package com.example.challengefour

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.challengefour.databinding.FragmentSignInBinding
import com.example.challengefour.databinding.FragmentSignUpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding?=null
    private val binding get() = _binding!!
    private var mDb: AbsenDatabase?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = AbsenDatabase.getInstance(requireContext())
        binding.btnDaftar.setOnClickListener {
            if(binding.etUsername.text.isNullOrEmpty()){
                Toast.makeText(context, "Data masih kosong", Toast.LENGTH_SHORT).show();
            }else{
                binding.etEmail.text.isNullOrEmpty();
                binding.etPassword.text.isNullOrEmpty()
                Toast.makeText(context, "Data masih kosong", Toast.LENGTH_SHORT).show()
            }
            val objectUser = User(
                null,binding.etUsername.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
            GlobalScope.async {
                val result = mDb?.absenDao()?.signUp(objectUser)
                runBlocking(Dispatchers.Main) {
                    if (result!=0.toLong()){
                        Toast.makeText(context, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                    }else{
                        result == null
                        Toast.makeText(context, "Pendaftaran Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}