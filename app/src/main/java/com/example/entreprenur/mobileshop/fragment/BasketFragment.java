package com.example.entreprenur.mobileshop.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.entreprenur.mobileshop.R;
import com.example.entreprenur.mobileshop.activity.MainActivity;
import com.example.entreprenur.mobileshop.api.ApiClient;
import com.example.entreprenur.mobileshop.api.ServiceGenerator;
import com.example.entreprenur.mobileshop.api.models.MobileModel;
import com.example.entreprenur.mobileshop.api.models.OrderRequest;
import com.example.entreprenur.mobileshop.api.models.OrderResponse;
import com.example.entreprenur.mobileshop.database.basemodels.BasketItemModel;
import com.example.entreprenur.mobileshop.utils.SharedPreferencesUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_basket)
public class BasketFragment extends Fragment {

    @ViewById(R.id.main_list)
    protected RecyclerView mainList;
    @ViewById(R.id.value_text)
    protected TextView valueText;

    private MainShopAdapter adapter;
    private List<BasketItemModel> basketItemModels = null;
    private int totalAmount = 0;

    private SharedPreferencesUtil sharedPreferencesUtil;

    @AfterViews
    public void init() {
        final MainActivity mainActivity = (MainActivity) getActivity();
        if ((mainActivity == null) || mainActivity.isFinishing() || !isAdded()) {
            return;
        }

        sharedPreferencesUtil = new SharedPreferencesUtil(mainActivity);

        basketItemModels = mainActivity.getBasketItemModels();
        if (basketItemModels == null || basketItemModels.isEmpty()) {
            return;
        }

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mainList.setLayoutManager(manager);

        final List<MobileModel> mobileModels = new ArrayList<>();
        final List<Integer> mobileAmounts = new ArrayList<>();
        for (BasketItemModel basketItemModel : basketItemModels) {
            totalAmount += basketItemModel.getPrice() * basketItemModel.getAmoount();
            MobileModel mobileModel = new MobileModel();

            mobileModel.Naziv = basketItemModel.getName();
            mobileModel.SlikaUrl = basketItemModel.getImage();
            mobileModel.Cijena = basketItemModel.getPrice();

            mobileAmounts.add(basketItemModel.getAmoount());
            mobileModels.add(mobileModel);
        }

        valueText.setText(String.format("%s KM", String.valueOf(totalAmount)));

        adapter = new MainShopAdapter(mobileModels,mobileAmounts,  null, new MainShopAdapter.OnItemBasketClickListener() {
            @Override
            public void onItemLongClick(int position) {

                BasketItemModel basketItemModel = basketItemModels.get(position);
                totalAmount -= basketItemModel.getPrice() * basketItemModel.getAmoount();
                valueText.setText(String.format("%s KM", String.valueOf(totalAmount)));

                mainActivity.deleteBasketModel(basketItemModel);

                basketItemModels.remove(position);
                mobileModels.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        mainList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Click(R.id.buy)
    public void buyItems() {

        final MainActivity mainActivity = (MainActivity) getActivity();
        if ((mainActivity == null) || mainActivity.isFinishing() || !isAdded()) {
            return;
        }

        if (basketItemModels == null || basketItemModels.isEmpty()) {
            Snackbar.make(getView(), "Korpa je prazna", 700).show();
            return;
        }

        Random rand = new Random();
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.UkupniIznos = totalAmount;
        orderRequest.BrojNarudzbe = rand.nextInt(50000) + 100;
        orderRequest.KupacId = Integer.valueOf(sharedPreferencesUtil.getUserID());
        orderRequest.Datum = new Date();

        List<OrderRequest.OrderItem> orderItems = new ArrayList<>();

        for (BasketItemModel basketItemModel : basketItemModels) {
            OrderRequest.OrderItem orderItem = new OrderRequest.OrderItem();

            orderItem.Cijena = basketItemModel.getPrice();
            orderItem.Kolicina = basketItemModel.getAmoount();
            orderItem.Iznos = basketItemModel.getAmoount() * basketItemModel.getAmoount();
            orderItem.MobiteliId = basketItemModel.getId();

            orderItems.add(orderItem);
        }

        orderRequest.stavke = orderItems;

        Call<OrderResponse> call = ServiceGenerator.createService(ApiClient.class).sendOrder(orderRequest);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (!response.isSuccessful()) {
                    Snackbar.make(getView(), "Dogodila se greška", 500).show();
                }

                mainActivity.deleteBasketModelItems();
                basketItemModels = null;
                adapter.deleteAll();

                Snackbar.make(getView(), "Kupovina uspješno završena", 700).show();
                valueText.setText(String.format("%s KM", String.valueOf(0)));
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Snackbar.make(getView(), "Dogodila se greška", 500).show();
            }
        });

    }
}
