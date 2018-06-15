package com.example.entreprenur.mobileshop.database.basemodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"itemId"},
        unique = true)})
public class BasketItemModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "amount")
    private Integer amoount;
    @ColumnInfo(name = "price")
    private Integer price;
    @ColumnInfo(name = "itemId")
    private Long itemId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "image")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAmoount() {
        return amoount;
    }

    public void setAmoount(Integer amoount) {
        this.amoount = amoount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
