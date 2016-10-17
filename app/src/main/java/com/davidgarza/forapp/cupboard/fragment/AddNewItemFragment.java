package com.davidgarza.forapp.cupboard.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.cupboard.FoodActivity;
import com.davidgarza.forapp.db.model.Item;
import com.davidgarza.forapp.db.model.ItemType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by davidgarza on 09/10/16.
 */
public class AddNewItemFragment extends Fragment {
    @BindView(R.id.item_type_spinner) Spinner itemTypeSpinner;
    @BindView(R.id.button_save) Button buttonSave;
    @BindView(R.id.et_item_name) EditText itemName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_item,container,false);
        ButterKnife.bind(this,view);

        setupSpinner();
        return view;
    }

    private void setupSpinner() {
        List<String> optionsList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ItemType> query = realm.where(ItemType.class);
        RealmResults<ItemType> result = query.findAll();

        for (int i = 0 ; i < result.size() ; i ++){
            optionsList.add(result.get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item,optionsList);
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

                String itemTypeSelected = itemTypeSpinner.getSelectedItem().toString();
                RealmQuery<ItemType> query = realm.where(ItemType.class).equalTo("name",itemTypeSelected);
                RealmResults<ItemType> result = query.findAll();

                item.setItemType(result.first());
            }
        });


        ((FoodActivity)getActivity()).showFragment(0);
    }

}
