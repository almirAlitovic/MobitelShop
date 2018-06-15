package com.example.entreprenur.mobileshop.database.basemodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"itemId"},
        unique = true)})
public class RamModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "itemId")
    private Long itemId;
    @ColumnInfo(name = "kapacitet")
    private Integer kapacitet;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getKapacitet() {
        return kapacitet;
    }

    public void setKapacitet(Integer kapacitet) {
        this.kapacitet = kapacitet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
