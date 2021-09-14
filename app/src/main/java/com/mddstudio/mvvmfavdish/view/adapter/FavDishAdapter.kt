package com.mddstudio.mvvmfavdish.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mddstudio.mvvmfavdish.R
import com.mddstudio.mvvmfavdish.databinding.ItemDishBinding
import com.mddstudio.mvvmfavdish.model.entities.FavDish
import com.mddstudio.mvvmfavdish.utils.Constant
import com.mddstudio.mvvmfavdish.view.activity.AddUpdActivity
import com.mddstudio.mvvmfavdish.view.fragment.AllDishesFrag
import com.mddstudio.mvvmfavdish.view.fragment.FavDishFrag

class FavDishAdapter(private val fragment:Fragment):
    RecyclerView.Adapter<FavDishAdapter.Viewholder>() {

    private var dishlist:List<FavDish> = listOf()



    class Viewholder(view:ItemDishBinding):RecyclerView.ViewHolder(view.root){

        val image=view.ivDishImage
        val titlett=view.tvDishTitle
        val ibmore =view.ibMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding= ItemDishBinding.inflate(LayoutInflater.from(fragment.context),parent,false)
         return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val dish=dishlist[position]
        Glide.with(fragment).load(dish.image).into(holder.image)
        holder.titlett.setText(dish.title)
        holder.ibmore.setOnClickListener {
            val popup =PopupMenu(fragment.requireContext(),holder.ibmore)
            popup.menuInflater.inflate(R.menu.menu_adpter,popup.menu)
            popup.setOnMenuItemClickListener {
                if (it.itemId== R.id.edit_Dia){
                    val intent=Intent(fragment.context,AddUpdActivity::class.java)
                    intent.putExtra(Constant.DISH_DETAILS,dish)

                    holder.ibmore.context.startActivity(intent)
                }else{
                   if (fragment is AllDishesFrag){
                       fragment.deletedish(dish)
                   }
                }

                true }
            popup.show()
        }
        holder.itemView.setOnClickListener {
            if (fragment is AllDishesFrag){
                fragment.dishdetails(dish)
            }
            if (fragment is FavDishFrag){
                fragment.dishdetails(dish)
            }
        }
    }

    override fun getItemCount(): Int {
      return dishlist.size
    }

    fun disheslist(list: List<FavDish>){
        dishlist=list
        notifyDataSetChanged()
    }
}