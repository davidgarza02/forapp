package com.davidgarza.forapp.cupboard;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.db.model.Item;
import com.davidgarza.forapp.db.model.Recipe;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by davidgarza on 09/10/16.
 */
public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.ViewHolder> {
    private Activity activity;
    private RealmResults<Item> result;
    private DecimalFormat decimalFormat;
    public FoodRecyclerAdapter(Activity activity) {
        this.activity = activity;

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Item> query = realm.where(Item.class);
        result = query.findAll();

        decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalSeparatorAlwaysShown(false);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.food_recycler_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = result.get(position);
        holder.itemName.setText(item.getName());
        holder.itemType.setText(item.getItemType().getName());

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Recipe> query = realm.where(Recipe.class).equalTo("items.name", item.getName());
        RealmResults<Recipe> result = query.findAll();
        holder.numberRecipes.setText(String.valueOf(result.size()));
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_name) TextView itemName;
        @BindView(R.id.item_type) TextView itemType;
        @BindView(R.id.number_recipes) TextView numberRecipes;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
