package com.mddstudio.mvvmfavdish.view.fragment

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mddstudio.mvvmfavdish.R
import com.mddstudio.mvvmfavdish.databinding.FragmentNotificationsBinding
import com.mddstudio.mvvmfavdish.model.entities.RandomDishdata

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mddstudio.application
import com.mddstudio.mvvmfavdish.model.entities.FavDish
import com.mddstudio.mvvmfavdish.utils.Constant
import com.mddstudio.mvvmfavdish.view.activity.MainActivity
import com.mddstudio.mvvmfavdish.viewmodel.FavDishFactory
import com.mddstudio.mvvmfavdish.viewmodel.FavDishViewModel
import com.mddstudio.mvvmfavdish.viewmodel.RandomDIshModel


class RandomDishFrag : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var randomviewmodel: RandomDIshModel
    private var mProgressDialog: Dialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)!!.hidebuttonNavi()
        }



        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomviewmodel = ViewModelProvider(this).get(RandomDIshModel::class.java)
        randomviewmodel.getRandomDishApi()
        randObserver()
        binding.srlRandomDish.setOnRefreshListener {
            randomviewmodel.getRandomDishApi()
        }
    }

    override fun onResume() {
        super.onResume()

    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun randObserver() {
        randomviewmodel.ranadomDishResponse.observe(viewLifecycleOwner) {

                randomResponse ->
            randomResponse.let {
                Log.d("abc", randomResponse.toString())
                setRandomDishResponnse(randomResponse.recipes[0])
                if (binding.srlRandomDish.isRefreshing) {
                    binding.srlRandomDish.isRefreshing = false
                }
            }
        }
        randomviewmodel.randomdishError.observe(viewLifecycleOwner) {

                randomResponse ->
            randomResponse.let {
                Log.d("abcd", randomResponse.toString())
            }
        }
        randomviewmodel.loadrandomdish.observe(viewLifecycleOwner) {

                randomResponse ->
            randomResponse.let {
                Log.d("abce", randomResponse.toString())
                if (randomResponse && !binding!!.srlRandomDish.isRefreshing) {
                    showCustomProgressDialog() // Used to show the progress dialog
                } else {
                    hideProgressDialog()
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setRandomDishResponnse(recipes: RandomDishdata.Recipe) {
        Glide.with(requireActivity()).load(recipes.image)
            .into(binding.ivDishImage)
        binding.apply {

            tvTitle.text = recipes.title
            var dishtype = "other"
            if (recipes.dishTypes.isNotEmpty()) {
                dishtype = recipes.dishTypes[0]
                tvType.text = dishtype
            }
            tvCategory.text = "other"
            var ingredient = ""
            for (value in recipes.extendedIngredients) {
                if (ingredient.isEmpty()) {
                    ingredient = value.original
                } else {
                    ingredient = ingredient + ",\n" + value.original
                }
            }
            tvIngredients.text = ingredient
            tvCookingDirection.text =
                Html.fromHtml(recipes.instructions, Html.FROM_HTML_MODE_COMPACT)
            ivFavoriteDish.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_favorite_unselected
                )
            )
            var isfav = false

            tvCookingTime.text = resources.getString(
                com.mddstudio.mvvmfavdish.R.string.lbl_estimate_cooking_time,
                recipes.readyInMinutes.toString()
            )

            ivFavoriteDish.setOnClickListener {

                if (isfav == true) {
                    Toast.makeText(requireContext(), "Already Added", Toast.LENGTH_LONG).show()
                } else {


                    val favdish = FavDish(
                        recipes.id, recipes.image, Constant.DISH_IMAGE_ONLINE,
                        recipes.title, dishtype, "other", ingredient,
                        recipes.readyInMinutes.toString(), recipes.instructions, false
                    )
                    val favDishViewModel: FavDishViewModel by viewModels {
                        FavDishFactory((requireActivity().application as application).rapostary)
                    }
                    favDishViewModel.insertData(favdish)
                    isfav = true

                    ivFavoriteDish.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_favorite_selected
                        )
                    )
                    Toast.makeText(requireContext(), "Added to Favourite", Toast.LENGTH_LONG).show()
                }


            }


        }

    }
    private fun showCustomProgressDialog() {
        mProgressDialog = Dialog(requireActivity())

        mProgressDialog?.let {
            it.setContentView(R.layout.dialog_custom_progress)

            it.show()
        }
    }

    private fun hideProgressDialog() {
        mProgressDialog?.let {
            it.dismiss()
        }
    }

}