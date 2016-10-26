package com.davidgarza.forapp.main;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.cupboard.FoodActivity;
import com.davidgarza.forapp.db.model.Item;
import com.davidgarza.forapp.db.model.ItemType;
import com.davidgarza.forapp.db.model.Recipe;
import com.davidgarza.forapp.main.exploredishes.ExploreDishesActivity;
import com.davidgarza.forapp.main.makedish.MakeDishActivity;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

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
        verifyStoragePermissions(this);
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @OnClick(R.id.cardview_cupboard)
        void clickCupboard(){
        Intent i = new Intent(this,FoodActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.cardview_exploredishes)
        void clickExploreDishes(){
        Intent i = new Intent(this,ExploreDishesActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.cardview_makedish)
    void clickMakeDish(){
        Intent i = new Intent(this,MakeDishActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.cardview_about)
    void clickAbout(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fragment_about);


        Button okButton = (Button) dialog.findViewById(R.id.button_ok);
        // if button is clicked, close the custom dialog
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setupToolbar() {
        setSupportActionBar(mainToolbar);
        mainToolbar.setTitleTextColor(clrWhite);
        mainToolbar.setTitle(strAppname);
    }

    private void setupDatabase() {
        boolean firstTime = getSharedPreferences("user",MODE_PRIVATE).getBoolean("is_first_open",true);
        if(firstTime){
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    String[] itemTypeArray = {"Abarrotes","Refrigerados","Carnes","Pescados","Mariscos","Frutas","Verduras","Lacteos",
                            "Huevo","Pan","Tortilla","Alimentos Preparados","Quesos","Embutidos","Vinos y Licores","Condimentos"};
                    for (String anItemTypeArray : itemTypeArray) {
                        ItemType itemType = realm.createObject(ItemType.class, ItemType.getNextId());
                        itemType.setName(anItemTypeArray);
                    }
                }
            });

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    String[] itemNameArray = {"Carne Molida", "Cebolla", "Cilantro", "Pan Molido", "Mostaza", "Huevo", "Pan de Hamburguesa", "Tomate", "Lechuga",
                            "Queso Amarillo", "Jamon", "Aguacate", "Sal", "Pimienta", "Papa", "Vinagre", "Aceite de Oliva", "Tortillas", "Chile Huajillo",
                            "Chile de Arbol", "Ajo", "Crema", "Queso", "Salsa Verde"
                    };
                    String[] itemTypeArray = {"Carnes", "Verduras", "Verduras", "Abarrotes", "Abarrotes", "Huevo", "Pan", "Verduras", "Verduras", "Quesos",
                            "Embutidos", "Verduras", "Condimentos", "Condimentos", "Verduras", "Condimentos", "Abarrotes", "Tortilla", "Condimentos","Condimentos",
                            "Condimentos","Lacteos", "Quesos", "Condimentos"

                    };

                    for (int i = 0; i < itemNameArray.length ; i ++) {
                        Item item = realm.createObject(Item.class,Item.getNextId());
                        item.setName(itemNameArray[i]);
                        RealmQuery<ItemType> query = realm.where(ItemType.class).equalTo("name",itemTypeArray[i]);
                        RealmResults<ItemType> result = query.findAll();
                        item.setItemType(result.first());
                    }
                }
            });

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    String[][] ingredientsArray = {
                            {"Carne Molida","Cebolla","Cilantro","Pan Molido","Mostaza","Huevo","Pan de Hamburguesa", "Tomate", "Lechuga", "Queso Amarillo",
                                    "Jamon", "Aguacate", "Sal", "Pimienta"},
                            {"Papa","Cebolla","Vinagre","Sal","Pimienta","Aceite de Oliva"},
                            {"Tortillas","Chile Huajillo","Chile de Arbol","Ajo","Sal","Crema","Queso","Salsa Verde"}
                    };
                    String[] descriptions = {
                                "En un bowl revolver la carne molida, la cebolla picada, el cilantro, el pan molido, la cucharada de mostaza y el huevo. Luego salpimentar a tu gusto para despues dejar reposar en el refrigerador durante 10-15 min.\n Luego formar las bolitas de carne para aplastarlas y extenderlas lo mas que se pueda, ya que al cocerlas se van a encojer un poco. Despues pasarlas a un sarten con lo minimo de aceite y dejarlas ahi hasta que deje de estar cruda la carne.\n Ya que estan cosidas, y aún estando en el sarte, ponemos encima de la carne una rebanada de queso amarillo y de jamon. Dejamos que se derrita un poco y volteamos con todo y carne.\n Poner sobre la carne el pan que va debajo de la hamburgesa ya con aguacate. Dejamos dorar un poco y volteamos para que el pan se caliente.\n Por ultimo, ponemos sobre el jamon la lechuga, el jitomate, la cebolla y el pan que va arriba de la hamburguesa ya con mostaza. Volteamos completa la hamburguesa, dejamos dorar un poco y listo.",
                                "Cocer la papa.\n Dejar enfríar pelar y rebanar.\n Mezclar cebolla, papa, vinagre, aceite, sal y pimienta.\n Buen provecho.",
                                "Se parten las tortillas en cuadritos no muy chicos, se doran en aceite caliente y luego se escurren.\n Aparte se cuecen los chiles ya cocidos se licúan con un poco de agua sal y ajo, se cuela y se guisa en poquito aceite ya guisado el chile se agregan las tortillitas doradas y se revuelven en el chile y listo sirves y acompañas con queso, crema y salsa (es opcional) también puedes acompañar con frijoles refritos."
                    };
                    String[] titles = {
                            "Hamburgesas",
                            "Ensalada sencilla de papa",
                            "Chilaquiles"
                    };
                    String[] images = {
                            "hamburguesa",
                            "ensalada",
                            "chilaquiles"
                    };
                    for (int i = 0; i < descriptions.length ; i ++) {
                        Recipe recipe = realm.createObject(Recipe.class,Recipe.getNextId());
                        recipe.setTitle(titles[i]);
                        recipe.setDescription(descriptions[i]);
                        if (i <= 2)
                            recipe.setImagePath(images[i]);
                        else
                            recipe.setImagePath("");
                        RealmList<Item> ingredients = new RealmList<Item>();
                        for (int j = 0; j < ingredientsArray[i].length ; j++){
                            RealmQuery<Item> query = realm.where(Item.class).equalTo("name",ingredientsArray[i][j]);
                            RealmResults<Item> result = query.findAll();

                            ingredients.add(result.first());
                        }
                        recipe.setItems(ingredients);
                    }

                }
            });
            getSharedPreferences("user",MODE_PRIVATE).edit().putBoolean("is_first_open",false).apply();
        }

    }

}
