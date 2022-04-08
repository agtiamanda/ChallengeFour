package com.example.challengefour

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.challengefour.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    private var _binding :FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private var mDb : AbsenDatabase?= null
    private lateinit var adapter : AbsenAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AbsenAdapter()
        binding.rvAbsen.adapter = adapter
        fetchData()
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_inputAbsensi)
        }
    }

    fun fetchData() {
        GlobalScope.launch {
            val listAbsensi = mDb?.absenDao()?.getAllAbsen()
            runBlocking(Dispatchers.Main) {
                if(listAbsensi.isNullOrEmpty()){
//                    binding.rvAbsen.visibility = View.GONE
                }else{
//                    binding.rvAbsen.visibility = View.VISIBLE
//                    binding.ivClock.visibility = View.GONE
                }
                listAbsensi?.let {
                    adapter.setData(it)

                }
            }
        }
    }
    override fun onResume(){
        super.onResume()
        fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        AbsenDatabase.destroyInstance()
    }
}