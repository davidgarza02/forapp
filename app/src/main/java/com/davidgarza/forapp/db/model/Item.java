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
    double quantity;
    Measure measure;
    ItemType itemType;
    Date lastUpdate;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
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
