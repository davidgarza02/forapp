package com.davidgarza.forapp.main.makedish;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.db.model.Recipe;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by davidgarza on 18/10/16.
 */
public class ShowPosibleDishesFragment extends Fragment {
    @BindView(R.id.recycler_showpossibledishes) RecyclerView recyclerPossibleDishes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posible_dishes,container,false);
        ButterKnife.bind(this,view);
        setupContent();

        return view;
    }

    private void setupContent() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerPossibleDishes.setLayoutManager(linearLayoutManager);

        ArrayList<String> ingredients = ((MakeDishActivity)getActivity()).checkedChecks;
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Recipe> query = realm.where(Recipe.class);

//        for (int i = 0; i < ingredients.size(); i++){
//            query.equalTo("items", ingredients.get(i));
//        }

        recyclerPossibleDishes.setAdapter(new PosibleDishesRecyclerAdapter(getActivity(),query.findAll()));
    }
}
