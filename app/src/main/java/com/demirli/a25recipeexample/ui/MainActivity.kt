package com.demirli.a25recipeexample.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.demirli.a25recipeexample.R
import com.demirli.a25recipeexample.model.Recipe
import com.demirli.a25recipeexample.util.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecipeAdapter.OnRecipeClickListener {

    private lateinit var adapter: RecipeAdapter

    private lateinit var recipeList: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createDbForList()

        //New Recipe Button
        add_recipe_fab.setOnClickListener {
            startActivity(Intent(this,
                DetailsActivity::class.java))
        }
        //New Recipe Intent
        intent.let {
            val newRecipe = intent.getParcelableExtra<Recipe>(Constants.EXTRA_NEW_RECIPE)
            if (newRecipe != null){
                recipeList.add(newRecipe)
            }
        }

        //Adapter
        adapter = RecipeAdapter(this, recipeList,this)
        recyclerView.layoutManager = GridLayoutManager(this,2)
        recyclerView.adapter = adapter
    }

    private fun createDbForList(){
        val uri = Uri.parse("")

        recipeList = arrayListOf(
            Recipe(
                "MIHLANMIŞ ET",
                "https://refika01.mncdn.com/wp-content/uploads/2020/03/Picture1.png",
                uri,
                "ingredients", "instructions"
            ),
            Recipe(
                "TAŞ KÖFTE TARİFİ",
                "https://refika01.mncdn.com/wp-content/uploads/2020/01/3O7A2740.jpg",
                uri,
                "ingredients", "instructions"
            ),
            Recipe(
                "BEŞAMEL SOSLU TAVUK KÖFTELİ MAKARNA",
                "https://refika01.mncdn.com/wp-content/uploads/2020/03/AY6I0639-2-scaled.jpg",
                uri,
                "ingredients", "instructions"
            )
        )
    }

    override fun onRecipeClick(recipe: Recipe) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.EXTRA_RECIPE,recipe)
        startActivity(intent)
    }
}
