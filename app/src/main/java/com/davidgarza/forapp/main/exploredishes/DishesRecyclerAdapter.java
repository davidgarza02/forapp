package com.davidgarza.forapp.main.exploredishes;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.cupboard.FoodRecyclerAdapter;
import com.davidgarza.forapp.db.model.Item;
import com.davidgarza.forapp.db.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by davidgarza on 16/10/16.
 */
public class DishesRecyclerAdapter extends RecyclerView.Adapter<DishesRecyclerAdapter.ViewHolder> {
    private Activity activity;
    private RealmResults<Recipe> result;

    public DishesRecyclerAdapter(Activity activity) {
        this.activity = activity;

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Recipe> query = realm.where(Recipe.class);
        result = query.findAll();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recipe_recycler_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = result.get(position);
        holder.recipeTitle.setText(recipe.getTitle());
        holder.recipeDescription.setText(recipe.getDescription());
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_title) TextView recipeTitle;
        @BindView(R.id.recipe_description) TextView recipeDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
