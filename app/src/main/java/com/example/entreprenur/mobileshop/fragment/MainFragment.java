package com.example.entreprenur.mobileshop.fragment;

import android.support.v4.app.Fragment;

import com.example.entreprenur.mobileshop.R;
import com.example.entreprenur.mobileshop.activity.MainActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    @Click(R.id.recommendedItem)
    public void onRecommendedItemClick() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if ((mainActivity == null) || mainActivity.isFinishing() || !isAdded()) {
            return;
        }
        mainActivity.showRecommendedFragment(getFragmentManager());
    }

    @Click(R.id.doShop)
    public void onDoShopClick() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if ((mainActivity == null) || mainActivity.isFinishing() || !isAdded()) {
            return;
        }
        mainActivity.showShopFragment(getFragmentManager());
    }

    @Click(R.id.itemHistory)
    public void onHistoryItemClick() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if ((mainActivity == null) || mainActivity.isFinishing() || !isAdded()) {
            return;
        }
        mainActivity.showHistoryFragment(getFragmentManager());
    }

    @Click(R.id.basket_item)
    public void onBasketItemClick() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if ((mainActivity == null) || mainActivity.isFinishing() || !isAdded()) {
            return;
        }
        mainActivity.showBasketFragment(getFragmentManager());
    }

    @Click(R.id.logOutItem)
    public void onLogOutClick() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if ((mainActivity == null) || mainActivity.isFinishing() || !isAdded()) {
            return;
        }

        mainActivity.logOut();
    }
}
