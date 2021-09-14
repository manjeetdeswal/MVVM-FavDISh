package com.mddstudio.mvvmfavdish.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mddstudio.application
import com.mddstudio.mvvmfavdish.R
import com.mddstudio.mvvmfavdish.databinding.DialogCustomListBinding
import com.mddstudio.mvvmfavdish.databinding.FragmentAllDishesBinding
import com.mddstudio.mvvmfavdish.model.entities.FavDish
import com.mddstudio.mvvmfavdish.utils.Constant
import com.mddstudio.mvvmfavdish.view.activity.AddUpdActivity
import com.mddstudio.mvvmfavdish.view.activity.MainActivity
import com.mddstudio.mvvmfavdish.view.adapter.CustomAdapter
import com.mddstudio.mvvmfavdish.view.adapter.FavDishAdapter
import com.mddstudio.mvvmfavdish.viewmodel.FavDishFactory
import com.mddstudio.mvvmfavdish.viewmodel.FavDishViewModel

class AllDishesFrag : Fragment() {


    private lateinit var binding: FragmentAllDishesBinding
    private lateinit var favDishAdapter: FavDishAdapter
    private lateinit var customdialo: Dialog

    private val favDishViewModel: FavDishViewModel by viewModels {
        FavDishFactory((requireActivity().application as application).rapostary)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllDishesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvDishesList.layoutManager = GridLayoutManager(requireContext(), 2)
        favDishAdapter = FavDishAdapter(this)
        binding.rvDishesList.adapter = favDishAdapter

        favDishViewModel.alldisheslist.observe(viewLifecycleOwner) { dishes ->
            dishes.let {
                if (it.isEmpty()) {
                    binding.tvNoDishesAddedYet.visibility = View.VISIBLE
                    binding.rvDishesList.visibility = View.GONE
                } else {
                    binding.tvNoDishesAddedYet.visibility = View.GONE
                    binding.rvDishesList.visibility = View.VISIBLE
                    favDishAdapter.disheslist(it)
                }
                for (item in it) {
                    Log.d("abc", it.toString())

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
        }
        findNavController().navigate(
            AllDishesFragDirections.actionNavigationHomeToDIshDetails(
                favDish
            )
        )
    }

    fun deletedish(favDish: FavDish) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Delete Dish")
        dialog.setMessage("Are you sure you want to delete this dish")
        dialog.setIcon(android.R.drawable.ic_dialog_alert)
        dialog.setPositiveButton("Yes") { dialogin, _ ->
            favDishViewModel.deleteData(favDish)
            dialogin.dismiss()
        }
        dialog.setNegativeButton("No") { dinfer, _ ->
            dinfer.dismiss()

        }
        val bulider: AlertDialog = dialog.create()
        bulider.setCancelable(false)
        bulider.show()
    }

    private fun filterDishdialog() {
        customdialo = Dialog(requireContext())
        val binding: DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)
        customdialo.setContentView(binding.root)
        binding.textView.setText("Select item to filter")
        val disgtype = Constant.dishTypes()
        disgtype.add(0, Constant.ALL_ITEM)
        binding.listrec.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CustomAdapter(requireActivity(), disgtype, Constant.FILTER_SEL, this)

        binding.listrec.adapter = adapter
        customdialo.show()


    }

    fun filterSeletion(filteritem: String) {

        customdialo.dismiss()

        if (filteritem == Constant.ALL_ITEM) {
            favDishViewModel.alldisheslist.observe(viewLifecycleOwner) { dishes ->
                dishes.let {
                    if (it.isEmpty()) {
                        binding.tvNoDishesAddedYet.visibility = View.VISIBLE
                        binding.rvDishesList.visibility = View.GONE
                    } else {
                        binding.tvNoDishesAddedYet.visibility = View.GONE
                        binding.rvDishesList.visibility = View.VISIBLE
                        favDishAdapter.disheslist(it)
                    }

                }
            }
        } else {
            favDishViewModel.filterdData(filteritem).observe(viewLifecycleOwner) {
                dishes->
                dishes.let {
                    if (it.isNotEmpty()){
                        binding.rvDishesList.visibility =View.VISIBLE
                        binding.tvNoDishesAddedYet.visibility=View.GONE
                        favDishAdapter.disheslist(it)
                    }else{
                        binding.rvDishesList.visibility =View.GONE
                        binding.tvNoDishesAddedYet.visibility=View.VISIBLE
                    }
                }

            }

        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addItem -> startActivity(Intent(context, AddUpdActivity::class.java))

            R.id.filter_dish ->
                filterDishdialog()

        }


        return super.onOptionsItemSelected(item)
    }
}