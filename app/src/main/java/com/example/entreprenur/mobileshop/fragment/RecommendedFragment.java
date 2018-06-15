package com.example.entreprenur.mobileshop.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.entreprenur.mobileshop.R;
import com.example.entreprenur.mobileshop.activity.MainActivity;
import com.example.entreprenur.mobileshop.api.ApiClient;
import com.example.entreprenur.mobileshop.api.ServiceGenerator;
import com.example.entreprenur.mobileshop.api.models.MobileModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_recommended)
public class RecommendedFragment extends Fragment {

    @ViewById(R.id.main_list_id)
    protected RecyclerView mainList;
    @ViewById(R.id.progress_holder)
    protected RelativeLayout progressHolder;

    Snackbar snackbar = null;

    @AfterViews
    public void init() {
        final MainActivity activity = (MainActivity) getActivity();
        if ((activity == null) || activity.isFinishing() || !isAdded()) {
            return;
        }

        if (getView() != null) {
            snackbar = Snackbar.make(getView(), "Internet greska", 400);
        }

        Call<List<MobileModel>> call = ServiceGenerator.createService(ApiClient.class).getMobiles();

        progressHolder.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<MobileModel>>() {
            @Override
            public void onResponse(Call<List<MobileModel>> call, Response<List<MobileModel>> response) {

                progressHolder.setVisibility(View.GONE);

                if (response.body() == null) {
                    snackbar.show();
                }

                List<MobileModel> mobileModels = response.body();

                MainShopAdapter adapter = new MainShopAdapter(mobileModels, null, new MainShopAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(MobileModel mobileModel) {
                        activity.showItemDetailsFragment(getFragmentManager(), mobileModel.Id);
                    }
                }, null);

                GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
                mainList.setLayoutManager(manager);
                mainList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MobileModel>> call, Throwable t) {
                if (snackbar != null) {
                    snackbar.show();
                }

                progressHolder.setVisibility(View.GONE);
            }
        });
    }

    @Click(R.id.basket_fragment)
    public void navigateToBasket() {
        final MainActivity activity = (MainActivity) getActivity();
        if ((activity == null) || activity.isFinishing() || !isAdded()) {
            return;
        }

        activity.showBasketFragment(getFragmentManager());
    }
}