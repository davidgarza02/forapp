package com.davidgarza.forapp.main.makedish;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.db.model.Item;
import com.davidgarza.forapp.db.model.Recipe;
import com.davidgarza.forapp.detailrecipe.ActivityDetailRecipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by davidgarza on 18/10/16.
 */
public class PosibleDishesRecyclerAdapter extends RecyclerView.Adapter<PosibleDishesRecyclerAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<Recipe> result;

    public PosibleDishesRecyclerAdapter(Activity activity, ArrayList<Recipe> result) {
        this.activity = activity;
        this.result = result;
    }

    @Override
    public PosibleDishesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.posible_dishes_recycler_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PosibleDishesRecyclerAdapter.ViewHolder holder, int position) {
        final Recipe recipe = result.get(position);
        holder.recipeTitle.setText(recipe.getTitle());
        holder.recipeDescription.setText(recipe.getDescription());

        if(recipe.getImagePath().equals("hamburguesa")){
            holder.itemImage.setImageResource(R.drawable.hamburguesa);
        }else if(recipe.getImagePath().equals("ensalada")){
            holder.itemImage.setImageResource(R.drawable.papa);
        }else if(recipe.getImagePath().equals("chilaquiles")){
            holder.itemImage.setImageResource(R.drawable.chilaquiles);
        }else if(recipe.getImagePath().equals("")){
            holder.itemImage.setImageResource(R.drawable.placeholder);
        }else{
            Bitmap d = new BitmapDrawable(activity.getResources() , recipe.getImagePath()).getBitmap();
            int nh = (int) ( d.getHeight() * (512.0 / d.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);
            holder.itemImage.setImageBitmap(scaled);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(activity, ActivityDetailRecipe.class);
                i.putExtra("recipe_id",recipe.getId());
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_title) TextView recipeTitle;
        @BindView(R.id.recipe_description) TextView recipeDescription;
        @BindView(R.id.item_image) ImageView itemImage;
        @BindView(R.id.main_container) ViewGroup container;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
