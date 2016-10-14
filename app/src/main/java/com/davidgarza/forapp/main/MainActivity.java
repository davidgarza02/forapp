package com.davidgarza.forapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.cupboard.MyCupBoardActivity;
import com.davidgarza.forapp.db.model.Item;
import com.davidgarza.forapp.db.model.ItemType;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by davidgarza on 08/10/16.
 */

public class MainActivity extends AppCompatActivity {
    @BindString(R.string.app_name) String strAppname;
    @BindColor(R.color.white) int clrWhite;
    @BindView(R.id.maintoolbar) Toolbar mainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        setupDatabase();
    }

    private void setupDatabase() {
        boolean firstTime = getSharedPreferences("user",MODE_PRIVATE).getBoolean("is_first_open",true);
        if(firstTime){
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    String[] itemTypeArray = {
                            "Abarrotes",
                            "Refrigerados",
                            "Carnes",
                            "Pescados",
                            "Mariscos",
                            "Frutas",
                            "Verduras",
                            "Lacteos",
                            "Huevo",
                            "Pan",
                            "Tortilla",
                            "Alimentos Preparados",
                            "Quesos",
                            "Embutidos",
                            "Vinos y Licores"
                    };
                    for (String anItemTypeArray : itemTypeArray) {
                        ItemType itemType = realm.createObject(ItemType.class, ItemType.getNextId());
                        itemType.setName(anItemTypeArray);
                    }
                }
            });
            getSharedPreferences("user",MODE_PRIVATE).edit().putBoolean("is_first_open",false).apply();
        }

    }

    @OnClick(R.id.cardview_cupboard)
        void clickCupboard(){
        Intent i = new Intent(this,MyCupBoardActivity.class);
        startActivity(i);
    }

    private void setupToolbar() {
        setSupportActionBar(mainToolbar);
        mainToolbar.setTitleTextColor(clrWhite);
        mainToolbar.setTitle(strAppname);
    }
}
