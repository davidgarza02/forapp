package com.davidgarza.forapp.db.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by davidgarza on 09/10/16.
 */

public class Measure extends RealmObject {
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
}
