package com.davidgarza.forapp.main.makedish;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.main.exploredishes.DishesRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidgarza on 16/10/16.
 */
public class MainMakeDishFragment extends Fragment {
    @BindView(R.id.recycler_makedish) RecyclerView recyclermakedish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_make_dish,container,false);
        ButterKnife.bind(this,view);

        setupContent();
        return view;
    }

    private void setupContent() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclermakedish.setLayoutManager(linearLayoutManager);
        recyclermakedish.setAdapter(new MakeDishRecyclerAdapter(getActivity()));
    }
}
