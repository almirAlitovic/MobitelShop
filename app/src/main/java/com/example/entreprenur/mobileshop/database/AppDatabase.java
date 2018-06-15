package com.example.entreprenur.mobileshop.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.entreprenur.mobileshop.database.basemodels.BasketItemModel;
import com.example.entreprenur.mobileshop.database.basemodels.CpuModel;
import com.example.entreprenur.mobileshop.database.basemodels.NameModel;
import com.example.entreprenur.mobileshop.database.basemodels.OsModel;
import com.example.entreprenur.mobileshop.database.basemodels.RamModel;

@Database(entities = {NameModel.class, CpuModel.class, RamModel.class, OsModel.class, BasketItemModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FilterDao filterDao();
}

