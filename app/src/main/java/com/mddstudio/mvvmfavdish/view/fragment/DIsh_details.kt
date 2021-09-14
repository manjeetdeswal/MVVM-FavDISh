package com.mddstudio.mvvmfavdish.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide

import com.mddstudio.application
import com.mddstudio.mvvmfavdish.R
import com.mddstudio.mvvmfavdish.databinding.FragmentDIshDetailsBinding
import com.mddstudio.mvvmfavdish.view.activity.MainActivity
import com.mddstudio.mvvmfavdish.viewmodel.FavDishFactory
import com.mddstudio.mvvmfavdish.viewmodel.FavDishViewModel


class DIsh_details : Fragment() {
    private lateinit var binding:FragmentDIshDetailsBinding


 private val favDishViewModel:FavDishViewModel by viewModels {
     FavDishFactory((requireActivity().application as application).rapostary)
 }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= FragmentDIshDetailsBinding.inflate(inflater, container, false)
        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)!!.hidebuttonNavi()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favDish:DIsh_detailsArgs by navArgs()

        favDish.let {


            binding.apply {


                        Glide.with(requireActivity()).load(it.dishDetails.image)
                            .into(ivDishImage)


                tvCategory.text = it.dishDetails.category
                tvCookingDirection.text = it.dishDetails.directionCooking
                tvIngredients.text = it.dishDetails.ingredient
                tvCookingTime.text = resources.getString(
                    R.string.lbl_estimate_cooking_time,
                    it.dishDetails.cookingtime
                )
                tvType.text = it.dishDetails.type
                tvTitle.text = it.dishDetails.title

                favDishViewModel.updateData(favDish.dishDetails)
                if (favDish.dishDetails.favourite){
                    ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_favorite_selected))
                }else{
                    ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_favorite_unselected))
                }

                ivFavoriteDish.setOnClickListener {
                    favDish.dishDetails.favourite =!favDish.dishDetails.favourite
                    favDishViewModel.updateData(favDish.dishDetails)
                    if (favDish.dishDetails.favourite){
                        ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_favorite_selected))
Toast.makeText(requireContext(),"Added to favourite",Toast.LENGTH_LONG).show()
                    }else{
                        ivFavoriteDish.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_favorite_unselected))
                        Toast.makeText(requireContext(),"Removed from favourite",Toast.LENGTH_LONG).show()
                    }



                }

            }
        }

    }



}