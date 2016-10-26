package com.davidgarza.forapp.detailrecipe;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.db.model.Item;
import com.davidgarza.forapp.db.model.Recipe;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by davidgarza on 25/10/16.
 */

public class ActivityDetailRecipe extends AppCompatActivity {
    @BindView(R.id.detail_toolbar) Toolbar toolbar;
    @BindString(R.string.detail_recipe) String strRecipe;
    @BindColor(R.color.white) int clrWhite;

    @BindView(R.id.recipe_image) ImageView recipeImage;
    @BindView(R.id.recipe_title) TextView recipeTitle;
    @BindView(R.id.ingredients_container) LinearLayout ingredientsContainer;
    @BindView(R.id.recipe_description) TextView recipeDescriptions;

    int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        ButterKnife.bind(this);
        recipeId = getIntent().getExtras().getInt("recipe_id");

        setupToolbar();
        setupRecipe();
        setupIngredients();
    }

    private void setupRecipe() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Recipe> query = realm.where(Recipe.class).equalTo("id",recipeId);
        RealmResults<Recipe> result = query.findAll();
        Recipe recipe = result.get(0);

        recipeTitle.setText(recipe.getTitle());
        recipeDescriptions.setText(recipe.getDescription());

        if(recipe.getImagePath().equals("hamburguesa")){
            recipeImage.setImageResource(R.drawable.hamburguesa);
        }else if(recipe.getImagePath().equals("ensalada")){
            recipeImage.setImageResource(R.drawable.papa);
        }else if(recipe.getImagePath().equals("chilaquiles")){
            recipeImage.setImageResource(R.drawable.chilaquiles);
        }else if(recipe.getImagePath().equals("")){
            recipeImage.setImageResource(R.drawable.placeholder);
        }else{
            Bitmap d = new BitmapDrawable(getResources() , recipe.getImagePath()).getBitmap();
            int nh = (int) ( d.getHeight() * (512.0 / d.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
            recipeImage.setImageBitmap(scaled);
        }

        for (Item item : recipe.getItems()){
            TextView textView = new TextView(this);
            textView.setTextSize(18);
            textView.setText(item.getName());
            ingredientsContainer.addView(textView);
        }
    }

    private void setupIngredients() {

    }

    private void setupToolbar() {
        toolbar.setTitleTextColor(clrWhite);
        toolbar.setTitle(strRecipe);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
