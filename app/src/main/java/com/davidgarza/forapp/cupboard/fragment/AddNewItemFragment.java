package com.davidgarza.forapp.cupboard.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.cupboard.MyCupBoardActivity;
import com.davidgarza.forapp.db.model.Item;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by davidgarza on 09/10/16.
 */
public class AddNewItemFragment extends Fragment {
    @BindView(R.id.item_type_spinner) Spinner itemTypeSpinner;
    @BindView(R.id.button_save) Button buttonSave;
    @BindView(R.id.et_item_name) EditText itemName;
    @BindView(R.id.et_item_quantity) EditText itemQuantity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_item,container,false);
        ButterKnife.bind(this,view);

        setupSpinner();
        return view;
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemTypeSpinner.setAdapter(adapter);
    }

    @OnClick(R.id.button_save)
    void saveClick(){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item = realm.createObject(Item.class,Item.getNextId());
                item.setName(itemName.getText().toString());
                item.setQuantity(Double.parseDouble(itemQuantity.getText().toString()));
            }
        });
        ((MyCupBoardActivity)getActivity()).showFragment(0);
    }

}
