package com.mddstudio.mvvmfavdish.view.activity

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mddstudio.application
import com.mddstudio.mvvmfavdish.R
import com.mddstudio.mvvmfavdish.databinding.ActivityAddUpdBinding
import com.mddstudio.mvvmfavdish.databinding.DialogCustomListBinding
import com.mddstudio.mvvmfavdish.databinding.GallerycameraDailogBinding
import com.mddstudio.mvvmfavdish.model.entities.FavDish
import com.mddstudio.mvvmfavdish.utils.Constant
import com.mddstudio.mvvmfavdish.view.adapter.CustomAdapter
import com.mddstudio.mvvmfavdish.viewmodel.FavDishFactory
import com.mddstudio.mvvmfavdish.viewmodel.FavDishViewModel
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*

class AddUpdActivity : AppCompatActivity() {
    companion object {
        private const val IMAGEDIR = "FAVDISH"
    }

    private var mfavdishdeta: FavDish? = null
    private val favDishViewModel: FavDishViewModel by viewModels {
        FavDishFactory((application as application).rapostary)
    }

    private var imagepath: String = ""
    private lateinit var dialogImg: Dialog
    private lateinit var dialogList: Dialog

    private lateinit var binding: ActivityAddUpdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (intent.hasExtra(Constant.DISH_DETAILS)) {
            mfavdishdeta = intent.getParcelableExtra(Constant.DISH_DETAILS)
        }
        setuptoolbar()
        binding.apply {


            mfavdishdeta?.let {
                imagepath = it.image
                Glide.with(applicationContext).load(imagepath).into(ivDishImage)

                etTitle.setText(it.title)
                etType.setText(it.type)
               etCategory.setText(it.category)
                etIngredients.setText(it.ingredient)
               etCookingTime.setText(it.cookingtime)
                etDirectionToCook.setText(it.directionCooking)
                btnAddDish.setText("Update Dish")
            }

        }

        binding.apply {

            ivAddDishImage.setOnClickListener {
                customdialog()
            }
            etType.setOnClickListener {
                listDialog("SELECT DISH TYPE", Constant.dishTypes(), Constant.DISH_TYPE)

            }
            etCookingTime.setOnClickListener {
                listDialog("SELECT TIME ", Constant.dishCookTime(), Constant.DISH_COOKING_TIME)

            }
            etCategory.setOnClickListener {
                listDialog("SELECT CATEGORIES", Constant.dishCategories(), Constant.DISH_CATEGORY)

            }
            btnAddDish.setOnClickListener {
                val title = binding.etTitle.text.toString().trim { it <= ' ' }
                val type = binding.etType.text.toString().trim { it <= ' ' }
                val category = binding.etCategory.text.toString().trim { it <= ' ' }
                val ingredient = binding.etIngredients.text.toString().trim { it <= ' ' }
                val cookingTIme = binding.etCookingTime.text.toString().trim { it <= ' ' }
                val direction = binding.etDirectionToCook.text.toString().trim { it <= ' ' }

                when {
                    TextUtils.isEmpty(imagepath) -> {
                        Toast.makeText(
                            this@AddUpdActivity,
                            resources.getString(R.string.err_msg_select_dish_image),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    TextUtils.isEmpty(title) -> {
                        Toast.makeText(
                            this@AddUpdActivity,
                            resources.getString(R.string.err_msg_enter_dish_title),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    TextUtils.isEmpty(type) -> {
                        Toast.makeText(
                            this@AddUpdActivity,
                            resources.getString(R.string.err_msg_select_dish_type),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    TextUtils.isEmpty(category) -> {
                        Toast.makeText(
                            this@AddUpdActivity,
                            resources.getString(R.string.err_msg_select_dish_category),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    TextUtils.isEmpty(ingredient) -> {
                        Toast.makeText(
                            this@AddUpdActivity,
                            resources.getString(R.string.err_msg_enter_dish_ingredients),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    TextUtils.isEmpty(cookingTIme) -> {
                        Toast.makeText(
                            this@AddUpdActivity,
                            resources.getString(R.string.err_msg_select_dish_cooking_time),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    TextUtils.isEmpty(direction) -> {
                        Toast.makeText(
                            this@AddUpdActivity,
                            resources.getString(R.string.err_msg_enter_dish_cooking_instructions),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {

                         var dishid= 0
                        var imageSource =Constant.DISH_IMAGE_LOCAL
                        var favoritFish =false
                        mfavdishdeta?.let {
                            if(it.id != 0){
                                dishid =it.id
                                imageSource=it.imageSource
                                favoritFish =it.favourite
                            }
                        }
                        val favdish = FavDish(
                            dishid, imagepath, imageSource,
                            title, type, category, ingredient, cookingTIme, direction, favoritFish
                        )
                        if (dishid == 0){


                            favDishViewModel.insertData(favdish)
                            Toast.makeText(
                                this@AddUpdActivity,
                                "Data Added",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            favDishViewModel.updateData(favdish)
                            Toast.makeText(
                                this@AddUpdActivity,
                                "Updated",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        finish()

                    }
                }
            }
        }

    }

    fun selctedlist(item: String, selecion: String) {
        when (selecion) {
            Constant.DISH_CATEGORY -> {
                dialogList.dismiss()
                binding.etCategory.setText(item)
            }
            Constant.DISH_COOKING_TIME -> {
                dialogList.dismiss()
                binding.etCookingTime.setText(item)
            }
            Constant.DISH_TYPE -> {
                dialogList.dismiss()
                binding.etType.setText(item)
            }
        }

    }

    private fun setuptoolbar() {
        setSupportActionBar(binding.toolbarAddDishActivity)
        if (mfavdishdeta != null && mfavdishdeta!!.id != 0) {
            supportActionBar?.let {
                it.title = "Edit Dish"
            }
        } else {
            supportActionBar.let {
                it?.title = "Add Dish"
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        binding.toolbarAddDishActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun customdialog() {
        dialogImg = Dialog(this)
        val binding = GallerycameraDailogBinding.inflate(layoutInflater)
        dialogImg.setContentView(binding.root)
        binding.tvCamera.setOnClickListener {

            Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                        if (p0!!.areAllPermissionsGranted()) {
                            Toast.makeText(
                                this@AddUpdActivity,
                                "Permission Granted",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, 1)

                        } else {
                            showdia()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        showdia()
                    }

                }).onSameThread().check()


        }
        binding.tvGallery.setOnClickListener {
            Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                        if (p0!!.areAllPermissionsGranted()) {
                            val intent = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )
                            startActivityForResult(intent, 2)

                        } else {
                            showdia()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        showdia()
                    }

                }).onSameThread().check()
            dialogImg.dismiss()
        }

        dialogImg.show()

    }

    private fun listDialog(title: String, list: List<String>, selecion: String) {
        dialogList = Dialog(this)
        val binding = DialogCustomListBinding.inflate(layoutInflater)
        dialogList.setContentView(binding.root)
        binding.textView.text = title
        binding.listrec.layoutManager = LinearLayoutManager(this)
        val adapterView = CustomAdapter(this, list, selecion,null)
        binding.listrec.adapter = adapterView
        dialogList.show()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                data.let {
                    val thmumnail = data?.extras?.get("data") as Bitmap
                    Glide.with(this).load(thmumnail)
                        .centerCrop().into(binding.ivDishImage)
                    binding.ivDishImage.setImageBitmap(thmumnail)

                    imagepath = saveImmage(thmumnail)
                    Snackbar.make(binding.ivDishImage, imagepath, Snackbar.LENGTH_LONG).show()
                    binding.ivAddDishImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_edit
                        )
                    )
                }
            }
            if (requestCode == 2) {
                data.let {
                    val thmumnail = data?.data
                    Glide.with(this).load(thmumnail)
                        .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                resource?.let {
                                    val bitmap = resource.toBitmap()
                                    imagepath = saveImmage(bitmap)

                                }
                                return false
                            }

                        }).into(binding.ivDishImage)
                    //   binding.ivDishImage.setImageURI(thmumnail)
                    binding.ivAddDishImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_edit
                        )
                    )
                }
            }

        }
    }

    private fun saveImmage(bitmap: Bitmap): String {

        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper
            .getDir(IMAGEDIR, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()


        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.absolutePath


    }

    private fun showdia() {
        AlertDialog.Builder(this).setMessage("Pls give Permission")
            .setPositiveButton("Go to settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}