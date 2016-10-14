package com.davidgarza.forapp.db.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by davidgarza on 09/10/16.
 */

public class ItemType extends RealmObject {
    @PrimaryKey
    int id;
    String name;

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

    public static int getNextId(){
        Realm realm = Realm.getDefaultInstance();

        Number currentMax = realm.where(ItemType.class).max("id");
        int nextId = 1;
        if (currentMax != null) {
            nextId = currentMax.intValue() + 1;
        }

        return nextId;
    }
}
