package com.example.challengefour

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.challengefour.databinding.FragmentSignInBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private var mDb: AbsenDatabase? = null


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        mDb = AbsenDatabase.getInstance(requireContext())
        binding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)

        }
        binding.btnSignin.setOnClickListener {
            if (binding.etUsername.text.isNullOrEmpty()) {
                Toast.makeText(context, "data harus di isi", Toast.LENGTH_SHORT).show()
            } else {

                GlobalScope.async {
                    val result = mDb?.absenDao()?.signIn(
                        binding.etUsername.text.toString(),
                        binding.etPass.text.toString()
                        //async dari coroutine yang jalan duluan untuk nampilin

                    )
                    runBlocking(Dispatchers.Main) {
                        if (result == true) {
                            Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                        } else {
                            Toast.makeText(context, "Login Gagal", Toast.LENGTH_SHORT).show()
                            //sebagai background
                        }
                    }
                }
            }


        }

    }
}