package com.mddstudio.mvvmfavdish.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mddstudio.mvvmfavdish.R
import com.mddstudio.mvvmfavdish.databinding.ItemCustomListBinding
import com.mddstudio.mvvmfavdish.view.activity.AddUpdActivity
import com.mddstudio.mvvmfavdish.view.fragment.AllDishesFrag

class CustomAdapter(private val activity: Activity,
private val listitmes:List<String>,
private val selection:String,
private val fragment:Fragment?) : RecyclerView.Adapter<CustomAdapter.CustomviewHolder>() {



    class CustomviewHolder( itemView: ItemCustomListBinding): RecyclerView.ViewHolder(itemView.root) {
        val customtext=itemView.customText


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomviewHolder {
        val binding=ItemCustomListBinding.inflate(LayoutInflater.from(activity),parent,false)
        return CustomviewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomviewHolder, position: Int) {
        val item=listitmes[position]
        holder.customtext.text=item
        holder.itemView.setOnClickListener {
            if (activity is AddUpdActivity){
                activity.selctedlist(item,selection)
            }
            if (fragment is AllDishesFrag){
                fragment.filterSeletion(item)
            }
        }
    }

    override fun getItemCount(): Int {
       return  listitmes.size
    }
}