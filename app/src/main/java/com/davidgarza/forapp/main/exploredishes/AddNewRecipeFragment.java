package com.davidgarza.forapp.main.exploredishes;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.cupboard.FoodActivity;
import com.davidgarza.forapp.db.model.Item;
import com.davidgarza.forapp.db.model.ItemType;
import com.davidgarza.forapp.db.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by davidgarza on 16/10/16.
 */
public class AddNewRecipeFragment extends Fragment {
    @BindView(R.id.et_recipe_title) EditText recipeTitle;
    @BindView(R.id.et_recipe_description) EditText recipeDescription;
    @BindView(R.id.ingredients_container) LinearLayout ingredientsContainer;

    ArrayList<String> ingredients = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_recipe,container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    @OnClick(R.id.add_ingredient)
    void addClick(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_ingredient);
        final LinearLayout fromCatalogContainer = (LinearLayout) dialog.findViewById(R.id.from_catalog_container);
        final LinearLayout newIngredientContainer = (LinearLayout) dialog.findViewById(R.id.new_ingredient_container);

        final Spinner ingredientsSpinner = (Spinner) dialog.findViewById(R.id.spinner_ingredients);

        final EditText itemName = (EditText) dialog.findViewById(R.id.et_item_name);
        final Spinner itemtypeSpinner = (Spinner) dialog.findViewById(R.id.item_type_spinner);


        List<String> optionsList = new ArrayList<>();
        final Realm realm = Realm.getDefaultInstance();
        RealmQuery<Item> query = realm.where(Item.class);
        RealmResults<Item> result = query.findAll();
        for (int i = 0 ; i < result.size() ; i ++){
            optionsList.add(result.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item,optionsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingredientsSpinner.setAdapter(adapter);

        optionsList = new ArrayList<>();
        RealmQuery<ItemType> queryit = realm.where(ItemType.class);
        RealmResults<ItemType> resultit = queryit.findAll();
        for (int i = 0 ; i < resultit.size() ; i ++){
            optionsList.add(resultit.get(i).getName());
        }
        ArrayAdapter<String> adapterit = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item,optionsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemtypeSpinner.setAdapter(adapterit);

        final RadioButton rbFromCatalog = (RadioButton) dialog.findViewById(R.id.radio_catalog);
        rbFromCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbFromCatalog.isChecked()){
                    fromCatalogContainer.setVisibility(View.VISIBLE);
                    newIngredientContainer.setVisibility(View.GONE);
                }else{
                    fromCatalogContainer.setVisibility(View.GONE);
                    newIngredientContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        final RadioButton rbNewIngredient = (RadioButton) dialog.findViewById(R.id.radio_new_ingredient);
        rbNewIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rbNewIngredient.isChecked()){
                    fromCatalogContainer.setVisibility(View.GONE);
                    newIngredientContainer.setVisibility(View.VISIBLE);
                }else{
                    fromCatalogContainer.setVisibility(View.VISIBLE);
                    newIngredientContainer.setVisibility(View.GONE);
                }
            }
        });

        Button dialogCancel = (Button) dialog.findViewById(R.id.dialog_cancel);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button dialogAdd = (Button) dialog.findViewById(R.id.dialog_add);
        dialogAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbNewIngredient.isChecked()){
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Item item = realm.createObject(Item.class,Item.getNextId());
                            item.setName(itemName.getText().toString());

                            String itemTypeSelected = itemtypeSpinner.getSelectedItem().toString();
                            RealmQuery<ItemType> query = realm.where(ItemType.class).equalTo("name",itemTypeSelected);
                            RealmResults<ItemType> result = query.findAll();

                            item.setItemType(result.first());
                        }
                    });
                    ingredients.add(itemName.getText().toString());
                    updateIngredients(itemName.getText().toString());

                }else{
                    String itemSelected = ingredientsSpinner.getSelectedItem().toString();
                    ingredients.add(itemSelected);
                    updateIngredients(itemSelected);

                }
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void updateIngredients(String newIngredient) {
        TextView textView = new TextView(getActivity());
        textView.setText(newIngredient);
        ingredientsContainer.addView(textView);
    }


    @OnClick(R.id.button_save_dish)
    void saveClick(){

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Recipe recipe = realm.createObject(Recipe.class,Recipe.getNextId());
                recipe.setTitle(recipeTitle.getText().toString());
                recipe.setDescription(recipeDescription.getText().toString());
                RealmList<Item> ingredientsObject = new RealmList<Item>();

                for (String ingredient : ingredients){
                    RealmQuery<Item> query = realm.where(Item.class).equalTo("name",ingredient);
                    RealmResults<Item> result = query.findAll();
                    ingredientsObject.add(result.first());
                }
                recipe.setItems(ingredientsObject);
            }
        });

        ((ExploreDishesActivity)getActivity()).showFragment(0);
    }
}
