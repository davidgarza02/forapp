package com.davidgarza.forapp.main.makedish;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.cupboard.FoodRecyclerAdapter;
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
 * Created by davidgarza on 16/10/16.
 */
public class MakeDishRecyclerAdapter extends RecyclerView.Adapter<MakeDishRecyclerAdapter.ViewHolder> {
    private Activity activity;
    private RealmResults<Item> result;
    public MakeDishRecyclerAdapter(Activity activity) {
        this.activity = activity;

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Item> query = realm.where(Item.class);
        result = query.findAllSorted("name");

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.items_recycler_item,parent,false);

        return new MakeDishRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Item item = result.get(position);
        holder.checkbox.setText(item.getName());
        if(((MakeDishActivity)activity).checkedChecks.contains(item.getName())){
            holder.checkbox.setChecked(true);
        }else{
            holder.checkbox.setChecked(false);
        }
        holder.checkbox.setOnCheckedChangeListener(null);

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ((MakeDishActivity)activity).checkedChecks.add(item.getName());
                else
                    ((MakeDishActivity)activity).checkedChecks.remove(item.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.main_container) LinearLayout mainContainer;
        @BindView(R.id.checkbox) CheckBox checkbox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            setIsRecyclable(false);
        }
    }
}
