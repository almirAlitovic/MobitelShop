package com.example.entreprenur.mobileshop.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.entreprenur.mobileshop.R;
import com.example.entreprenur.mobileshop.activity.MainActivity;
import com.example.entreprenur.mobileshop.api.ApiClient;
import com.example.entreprenur.mobileshop.api.ServiceGenerator;
import com.example.entreprenur.mobileshop.api.models.HistoryModel;
import com.example.entreprenur.mobileshop.utils.SharedPreferencesUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_history)
public class HistoryFragment extends Fragment {

    @ViewById(R.id.main_list)
    protected RecyclerView mainList;
    @ViewById(R.id.progress_holder)
    protected RelativeLayout progressHolder;

    HistoryAdapter historyAdapter;
    SharedPreferencesUtil sharedPreferencesUtil;
    Snackbar snackbar = null;

    @AfterViews
    public void init() {
        final MainActivity activity = (MainActivity) getActivity();
        if((activity == null) || activity.isFinishing() || !isAdded()) {
            return;
        }
        if (getView() != null) {
            snackbar = Snackbar.make(getView(), "Internet greska", 400);
        }

        sharedPreferencesUtil = new SharedPreferencesUtil(activity);

        Call<List<HistoryModel>> call = ServiceGenerator.createService(ApiClient.class).getHistory(Long.valueOf(sharedPreferencesUtil.getUserID()));

        call.enqueue(new Callback<List<HistoryModel>>() {
            @Override
            public void onResponse(Call<List<HistoryModel>> call, Response<List<HistoryModel>> response) {
                progressHolder.setVisibility(View.GONE);

                if ((response.body() == null) || !response.isSuccessful()) {
                    if (snackbar != null) {
                        snackbar.show();
                    }
                }

                List<HistoryModel> list = response.body();

                historyAdapter = new HistoryAdapter(list, new HistoryAdapter.OnHistoryItemClick() {
                    @Override
                    public void onHistoryItemClick(HistoryModel historyModel) {
                        Toast.makeText(activity, historyModel.Naziv, Toast.LENGTH_SHORT).show();
                    }
                });

                GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 2);
                mainList.setLayoutManager(gridLayoutManager);
                mainList.setAdapter(historyAdapter);
                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<HistoryModel>> call, Throwable t) {
                progressHolder.setVisibility(View.GONE);

                if (snackbar != null) {
                    snackbar.show();
                }
            }
        });

    }
}
