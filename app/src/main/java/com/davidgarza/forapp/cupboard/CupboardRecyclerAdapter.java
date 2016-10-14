package com.davidgarza.forapp.cupboard;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.db.model.Item;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by davidgarza on 09/10/16.
 */
public class CupboardRecyclerAdapter extends RecyclerView.Adapter<CupboardRecyclerAdapter.ViewHolder> {
    private Activity activity;
    private RealmResults<Item> result;
    private DecimalFormat decimalFormat;
    public CupboardRecyclerAdapter(Activity activity) {
        this.activity = activity;

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Item> query = realm.where(Item.class);
        result = query.findAll();

        decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalSeparatorAlwaysShown(false);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.cupboard_recycler_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemName.setText(result.get(position).getName());
        holder.itemQuantity.setText(String.valueOf(decimalFormat.format(result.get(position).getQuantity())));
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_name) TextView itemName;
        @BindView(R.id.item_quantity) TextView itemQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
