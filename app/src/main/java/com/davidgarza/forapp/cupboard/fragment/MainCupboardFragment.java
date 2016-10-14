package com.davidgarza.forapp.cupboard.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.cupboard.CupboardRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidgarza on 09/10/16.
 */
public class MainCupboardFragment extends Fragment {
    @BindView(R.id.recycler_inventory) RecyclerView recyclerInventory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_cupboard,container,false);
        ButterKnife.bind(this,view);

        setupContent();
        return view;
    }

    private void setupContent() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerInventory.setLayoutManager(linearLayoutManager);
        recyclerInventory.setAdapter(new CupboardRecyclerAdapter(getActivity()));
    }
}
