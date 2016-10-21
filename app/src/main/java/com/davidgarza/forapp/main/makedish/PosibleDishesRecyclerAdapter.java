package com.davidgarza.forapp.main.makedish;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.db.model.Item;
import com.davidgarza.forapp.db.model.Recipe;

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
