package com.example.challengefour

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.challengefour.databinding.ItemAbsenBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AbsenAdapter: RecyclerView.Adapter<AbsenAdapter.ViewHolder>(){
    private val listAbsen= mutableListOf<DaftarJam>()
    //memanggil secondary constructor dari EditAbsensi

    lateinit var repo: AbsenRepo

    class ViewHolder(val binding: ItemAbsenBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAbsenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

        //nge-push layout
    }

    override fun getItemCount(): Int {
        return listAbsen.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            tvJam.text = listAbsen[position].jamAbsen
            tvKelas.text = listAbsen[position].kelas
            tvTanggal.text = listAbsen[position].tanggalAbsen

            holder.binding.btnEdit.setOnClickListener {
                val activity = it.context as MainActivity
                val editDialFrag = EditAbsensi(listAbsen[position])
                editDialFrag.show(activity.supportFragmentManager,null)
                //untuk menerima data yang telah ditambahkan db dan untuk mengedit kembali
            }
            holder.binding.btnDelete.setOnClickListener {
                AlertDialog.Builder(it.context).setPositiveButton("Ya"){ p0, p1 ->
                    val mDb = AbsenDatabase.getInstance(holder.itemView.context)
                    GlobalScope.async {
                        val result = repo.deleteAbsen(listAbsen[position])

                        (holder.itemView.context as MainActivity).runOnUiThread{
                            if (result != 0){
                                Toast.makeText(it.context, "${listAbsen[position].kelas} berhasil dihapus!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(it.context, "${listAbsen[position].kelas} gagal dihapus!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        (holder.itemView.context as HomeFragment).fetchData()
                    }
                }.setNegativeButton("Tidak"){ p0, p1 ->
                    p0.dismiss()
                }
                    .setMessage("Apakah Anda Yakin ingin menghapus data ${listAbsen[position].kelas}")
                    .setTitle("Konfirmasi Hapus").create().show()
            }

            //semacam oncreate (buat nampilin data di layout yg dituju)
        }

    }
    fun setData(absenList: List<DaftarJam>){
        listAbsen.clear()
        listAbsen.addAll(absenList)
        notifyDataSetChanged()
    }

}