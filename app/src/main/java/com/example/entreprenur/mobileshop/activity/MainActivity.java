package com.example.entreprenur.mobileshop.activity;

import android.arch.persistence.room.Room;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.entreprenur.mobileshop.R;
import com.example.entreprenur.mobileshop.api.ApiClient;
import com.example.entreprenur.mobileshop.api.ServiceGenerator;
import com.example.entreprenur.mobileshop.api.models.BrandModel;
import com.example.entreprenur.mobileshop.api.models.CpuApiModel;
import com.example.entreprenur.mobileshop.api.models.MemoModel;
import com.example.entreprenur.mobileshop.api.models.OsApiModel;
import com.example.entreprenur.mobileshop.database.AppDatabase;
import com.example.entreprenur.mobileshop.database.basemodels.BasketItemModel;
import com.example.entreprenur.mobileshop.database.basemodels.CpuModel;
import com.example.entreprenur.mobileshop.database.basemodels.NameModel;
import com.example.entreprenur.mobileshop.database.basemodels.OsModel;
import com.example.entreprenur.mobileshop.database.basemodels.RamModel;
import com.example.entreprenur.mobileshop.fragment.BasketFragment_;
import com.example.entreprenur.mobileshop.fragment.HistoryFragment_;
import com.example.entreprenur.mobileshop.fragment.ItemDetailsFragment_;
import com.example.entreprenur.mobileshop.fragment.MainFragment_;
import com.example.entreprenur.mobileshop.fragment.RecommendedFragment_;
import com.example.entreprenur.mobileshop.fragment.ShopFragment_;
import com.example.entreprenur.mobileshop.utils.AppConstants;
import com.example.entreprenur.mobileshop.utils.SharedPreferencesUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private SharedPreferencesUtil sharedPreferencesUtil;
    public AppDatabase db;

    @AfterViews
    public void init() {
        sharedPreferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();

        initFilter();
        showMainFragment(getSupportFragmentManager());
    }

    @Background
    public void initFilter() {

        final Call<List<CpuApiModel>> cpuCall = ServiceGenerator.createService(ApiClient.class).getCpus();
        Call<List<MemoModel>> memoCall = ServiceGenerator.createService(ApiClient.class).getMemo();
        final Call<List<OsApiModel>> osCall = ServiceGenerator.createService(ApiClient.class).getOs();
        Call<List<BrandModel>> brandCall = ServiceGenerator.createService(ApiClient.class).getBrands();

        cpuCall.enqueue(new Callback<List<CpuApiModel>>() {
            @Override
            public void onResponse(Call<List<CpuApiModel>> call, Response<List<CpuApiModel>> response) {
                if (response.body() != null) {
                    List<CpuModel> cpuModels = new ArrayList<>();
                    for (CpuApiModel model : response.body()) {
                        CpuModel cpuModel = new CpuModel();
                        cpuModel.setBrojJezgri(model.BrojJezgri);
                        cpuModel.setOpis(model.Opis);
                        cpuModel.setItemId(model.Id);
                        cpuModels.add(cpuModel);
                    }
                    db.filterDao().insertCpu(cpuModels);
                }
            }

            @Override
            public void onFailure(Call<List<CpuApiModel>> call, Throwable t) {

            }
        });

        memoCall.enqueue(new Callback<List<MemoModel>>() {
            @Override
            public void onResponse(Call<List<MemoModel>> call, Response<List<MemoModel>> response) {
                if (response.body() != null) {
                    List<RamModel> ramModels = new ArrayList<>();
                    for (MemoModel model : response.body()) {
                        RamModel ramModel = new RamModel();
                        ramModel.setItemId(model.Id);
                        ramModel.setKapacitet(model.Kapacitet);
                        ramModels.add(ramModel);
                    }
                    db.filterDao().insertMemo(ramModels);
                }
            }

            @Override
            public void onFailure(Call<List<MemoModel>> call, Throwable t) {

            }
        });

        osCall.enqueue(new Callback<List<OsApiModel>>() {
            @Override
            public void onResponse(Call<List<OsApiModel>> call, Response<List<OsApiModel>> response) {
                if (response.body() != null) {
                    List<OsModel> osModels = new ArrayList<>();
                    for (OsApiModel model : response.body()) {
                        OsModel osModel = new OsModel();
                        osModel.setItemId(model.Id);
                        osModel.setNaziv(model.Naziv);
                        osModels.add(osModel);
                    }

                    db.filterDao().insertOs(osModels);
                }
            }

            @Override
            public void onFailure(Call<List<OsApiModel>> call, Throwable t) {

            }
        });

        brandCall.enqueue(new Callback<List<BrandModel>>() {
            @Override
            public void onResponse(Call<List<BrandModel>> call, Response<List<BrandModel>> response) {
                if (response.body() != null) {
                    List<NameModel> nameModels = new ArrayList<>();
                    for (BrandModel model : response.body()) {
                        NameModel nameModel = new NameModel();
                        nameModel.setItemId(model.Id);
                        nameModel.setNaziv(model.Naziv);
                        nameModels.add(nameModel);
                    }

                    db.filterDao().insertBrand(nameModels);
                }
            }

            @Override
            public void onFailure(Call<List<BrandModel>> call, Throwable t) {

            }
        });
    }

    public List<CpuModel> getCpu() {
        return db.filterDao().getCpuModels();
    }

    public void insertBasketItem(BasketItemModel basketItemModel) {
        db.filterDao().insertBasketItem(basketItemModel);
    }

    public void deleteBasketModel(BasketItemModel basketItemModel) {
        db.filterDao().deleteItem(basketItemModel);
    }

    public List<BasketItemModel> getBasketItemModels() {
        return  db.filterDao().getBasketItems();
    }

    public void deleteBasketModelItems() {
        db.filterDao().deleteBasketItems();
    }

    public List<NameModel> getBrands() {
        return db.filterDao().getNameModels();
    }

    public List<OsModel> getOs() {
        return db.filterDao().getOsModels();
    }

    public List<RamModel> getMemo() {
        return db.filterDao().getRamModels();
    }


    public List<NameModel> getBrands(int id) {
        return db.filterDao().getSpecificBrand((long) id);
    }

    public List<OsModel> getOs(int id) {
        return db.filterDao().getSpecificOs((long) id);
    }

    public List<RamModel> getMemo(int id) {
        return db.filterDao().getSpecificRam((long) id);
    }

    public List<CpuModel> getCpu(int id) {
        return db.filterDao().getSpecificCpu((long) id);
    }

    public void showMainFragment(FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment_.builder().build(), MainFragment_.class.getSimpleName())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    public void showRecommendedFragment(FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, RecommendedFragment_.builder().build(), RecommendedFragment_.class.getSimpleName())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    public void showShopFragment(FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ShopFragment_.builder().build(), ShopFragment_.class.getSimpleName())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    public void showHistoryFragment(FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, HistoryFragment_.builder().build(), HistoryFragment_.class.getSimpleName())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    public void showBasketFragment(FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, BasketFragment_.builder().build(), BasketFragment_.class.getSimpleName())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    public void showItemDetailsFragment(FragmentManager fragmentManager, final Long mobileId) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ItemDetailsFragment_.builder().mobileId(mobileId).build(), ItemDetailsFragment_.class.getSimpleName())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    public void logOut() {
        LoginActivity_.IntentBuilder_ builder = LoginActivity_.intent(MainActivity.this);
        sharedPreferencesUtil.setUserId(AppConstants.EMPTY_STRING);
        sharedPreferencesUtil.setUserName(AppConstants.EMPTY_STRING);
        builder.start();
        finish();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager != null) {
            if ((fragmentManager.getFragments() != null) && !fragmentManager.getFragments().isEmpty()
                    && fragmentManager.getFragments().get(0).getTag().equals("MainFragment_")) {
                finish();
            }
            fragmentManager.popBackStack();
        }
    }
}
