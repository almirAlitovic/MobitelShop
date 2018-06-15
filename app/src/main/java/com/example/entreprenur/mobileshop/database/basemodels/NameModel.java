package com.example.entreprenur.mobileshop.database.basemodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"itemId"},
        unique = true)})
public class NameModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "itemId")
    private Long itemId;
    @ColumnInfo(name = "naziv")
    private String naziv;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
