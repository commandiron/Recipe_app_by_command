package com.demirli.a25recipeexample.ui

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.demirli.a25recipeexample.R
import com.demirli.a25recipeexample.model.Recipe
import com.demirli.a25recipeexample.util.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private var selectedPicture: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        intent.let {
            val recipe = intent.getParcelableExtra<Recipe>(Constants.EXTRA_RECIPE)
            if(recipe != null){

                if(recipe.photoUriForPicasso != ""){
                    Picasso.get().load(recipe.photoUriForPicasso).into(detail_recipe_photo_iv)
                    detail_recipe_title_et.setText(recipe.title)
                    detail_recipe_ingredients_et.setText(recipe.ingredients)
                    detail_recipe_instructions_et.setText(recipe.instructions)
                    save_btn.visibility = View.INVISIBLE
                }else{
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,recipe.bitMapUri!!)
                    detail_recipe_photo_iv.setImageBitmap(bitmap)
                    detail_recipe_title_et.setText(recipe.title)
                    detail_recipe_ingredients_et.setText(recipe.ingredients)
                    detail_recipe_instructions_et.setText(recipe.instructions)
                    save_btn.visibility = View.INVISIBLE
                }

            }else{
                save_btn.visibility = View.VISIBLE
                detail_recipe_photo_iv.setOnClickListener {
                    selectImage()
                }
            }
        }

        save_btn.setOnClickListener {
            val recipe = Recipe(detail_recipe_title_et.text.toString(),
                "",
                selectedPicture,
                detail_recipe_ingredients_et.text.toString(),
                detail_recipe_instructions_et.text.toString())
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.EXTRA_NEW_RECIPE, recipe)
            startActivity(intent)
        }
    }

    fun selectImage(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), Constants.SELECT_IMAGE_PERMISSION_REQUEST_CODE)
        }else{
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentToGallery, Constants.SELECT_IMAGE_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.SELECT_IMAGE_PERMISSION_REQUEST_CODE){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intentToGallery, Constants.SELECT_IMAGE_REQUEST_CODE)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.SELECT_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){
            selectedPicture = data.data

            if (selectedPicture != null){
                if (Build.VERSION.SDK_INT >= 28){
                    val source = ImageDecoder.createSource(this.contentResolver,selectedPicture!!)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    detail_recipe_photo_iv.setImageBitmap(bitmap)
                }else{
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedPicture)
                    detail_recipe_photo_iv.setImageBitmap(bitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
