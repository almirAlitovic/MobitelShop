package com.example.entreprenur.mobileshop.database.basemodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"itemId"},
        unique = true)})
public class CpuModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "itemId")
    private Long itemId;
    @ColumnInfo(name = "brojJezgri")
    private Integer brojJezgri;
    @ColumnInfo(name = "opis")
    private String opis;

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getBrojJezgri() {
        return brojJezgri;
    }

    public void setBrojJezgri(Integer brojJezgri) {
        this.brojJezgri = brojJezgri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
