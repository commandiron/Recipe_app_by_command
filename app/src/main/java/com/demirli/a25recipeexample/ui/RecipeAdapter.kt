package com.demirli.a25recipeexample.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demirli.a25recipeexample.R
import com.demirli.a25recipeexample.model.Recipe
import com.squareup.picasso.Picasso

class RecipeAdapter(var context: Context, var recipeList: List<Recipe>, var onRecipeClickListener: OnRecipeClickListener) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = recipeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.recipeTitle.text = recipeList[position].title

        if (recipeList[position].photoUriForPicasso == ""){
            if (Build.VERSION.SDK_INT >= 28){
                val source = ImageDecoder.createSource(context.contentResolver,recipeList[position].bitMapUri!!)
                val bitmap = ImageDecoder.decodeBitmap(source)
                holder.recipePhoto.setImageBitmap(bitmap)
            }else{
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver,recipeList[position].bitMapUri!!)
                holder.recipePhoto.setImageBitmap(bitmap)
            }
        }else{
            Picasso.get().load(recipeList[position].photoUriForPicasso).placeholder(R.drawable.ic_launcher_foreground).into(holder.recipePhoto)
        }

        holder.recipePhoto.setOnClickListener {
            onRecipeClickListener.onRecipeClick(recipeList[position])
        }
        holder.itemView.setOnClickListener {
            onRecipeClickListener.onRecipeClick(recipeList[position])
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val recipePhoto = view.findViewById<ImageView>(R.id.recipe_photo_iv)
        val recipeTitle = view.findViewById<TextView>(R.id.recipe_title_tv)
    }

    interface OnRecipeClickListener {
        fun onRecipeClick(recipe: Recipe)
    }
}