package com.davidgarza.forapp.db.model;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by davidgarza on 09/10/16.
 */

public class Recipe extends RealmObject {
    @PrimaryKey
    int id;
    String title;
    String description;
    String imagePath;
    RealmList<Item> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmList<Item> getItems() {
        return items;
    }

    public void setItems(RealmList<Item> items) {
        this.items = items;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public static int getNextId(){
        Realm realm = Realm.getDefaultInstance();

        Number currentMax = realm.where(Recipe.class).max("id");
        int nextId = 1;
        if (currentMax != null) {
            nextId = currentMax.intValue() + 1;
        }

        return nextId;
    }
}
