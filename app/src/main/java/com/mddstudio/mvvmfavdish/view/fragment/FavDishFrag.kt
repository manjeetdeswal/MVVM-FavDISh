package com.mddstudio.mvvmfavdish.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import com.mddstudio.application

import com.mddstudio.mvvmfavdish.databinding.FragmentFavBinding
import com.mddstudio.mvvmfavdish.model.entities.FavDish
import com.mddstudio.mvvmfavdish.view.activity.MainActivity
import com.mddstudio.mvvmfavdish.view.adapter.FavDishAdapter
import com.mddstudio.mvvmfavdish.viewmodel.FavDishFactory
import com.mddstudio.mvvmfavdish.viewmodel.FavDishViewModel

class FavDishFrag : Fragment() {


    private  lateinit var binding: FragmentFavBinding

   private val favDishViewModel:FavDishViewModel by viewModels {
       FavDishFactory((requireActivity().application as application ).rapostary )
   }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentFavBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


                 binding.rvFavoriteDishesList.layoutManager = GridLayoutManager(requireContext(),2)
        val adapter=FavDishAdapter(this)
        binding.rvFavoriteDishesList.adapter=adapter
        favDishViewModel.favdisheslist.observe(viewLifecycleOwner){
            dishes->
            dishes.let {
                 if (it.isNotEmpty()){
                     Log.d("abc",it.toString())
                     binding.rvFavoriteDishesList.visibility=View.VISIBLE
                     binding.tvNoFavoriteDishesAvailable.visibility=View.GONE
                     adapter.disheslist(it)
                 }else
                 {
                     binding.rvFavoriteDishesList.visibility=View.GONE
                     binding.tvNoFavoriteDishesAvailable.visibility=View.VISIBLE
                 }
            }
        }
            }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)!!.showbuttonNavi()
        }
    }

    fun dishdetails(favDish: FavDish) {
        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)!!.hidebuttonNavi()

            if (requireActivity() is MainActivity) {
                findNavController().navigate(
                    FavDishFragDirections.actionNavigationFavToDIshDetails(
                        favDish
                    )
                )
            }
        }
    }}





