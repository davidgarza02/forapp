package com.davidgarza.forapp.db.model;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by davidgarza on 09/10/16.
 */

public class Item extends RealmObject {
    @PrimaryKey
    int id;
    String name;
    ItemType itemType;
    String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static int getNextId(){
        Realm realm = Realm.getDefaultInstance();

        Number currentMax = realm.where(Item.class).max("id");
        int nextId = 1;
        if (currentMax != null) {
            nextId = currentMax.intValue() + 1;
        }

        return nextId;
    }
}
