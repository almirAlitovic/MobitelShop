package com.example.entreprenur.mobileshop.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.entreprenur.mobileshop.database.basemodels.BasketItemModel;
import com.example.entreprenur.mobileshop.database.basemodels.CpuModel;
import com.example.entreprenur.mobileshop.database.basemodels.NameModel;
import com.example.entreprenur.mobileshop.database.basemodels.OsModel;
import com.example.entreprenur.mobileshop.database.basemodels.RamModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FilterDao {

    @Insert(onConflict = REPLACE)
    void insertBasketItem(BasketItemModel basketItemModel);

    @Delete
    void deleteItem(BasketItemModel basketItemModel);

    @Insert(onConflict = REPLACE)
    void insertCpu(List<CpuModel> cpuModels);

    @Insert(onConflict = REPLACE)
    void insertMemo(List<RamModel> cpuModels);

    @Insert(onConflict = REPLACE)
    void insertBrand(List<NameModel> cpuModels);

    @Insert(onConflict = REPLACE)
    void insertOs(List<OsModel> cpuModels);

    @Query("DELETE FROM BasketItemModel")
    void deleteBasketItems();

    @Query("SELECT * FROM CpuModel")
    List<CpuModel> getCpuModels();

    @Query("SELECT * FROM BasketItemModel")
    List<BasketItemModel> getBasketItems();

    @Query("SELECT * FROM RamModel")
    List<RamModel> getRamModels();

    @Query("SELECT * FROM NameModel")
    List<NameModel> getNameModels();

    @Query("SELECT * FROM OsModel")
    List<OsModel> getOsModels();

    @Query("SELECT * FROM RamModel WHERE itemId = :id")
    List<RamModel> getSpecificRam(Long id);

    @Query("SELECT * FROM OsModel WHERE itemId = :id")
    List<OsModel> getSpecificOs(Long id);

    @Query("SELECT * FROM NameModel WHERE itemId = :id")
    List<NameModel> getSpecificBrand(Long id);

    @Query("SELECT * FROM CpuModel WHERE itemId = :id")
    List<CpuModel> getSpecificCpu(Long id);
}
