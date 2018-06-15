package com.example.entreprenur.mobileshop.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.entreprenur.mobileshop.R;
import com.example.entreprenur.mobileshop.fragment.LoginFragment_;
import com.example.entreprenur.mobileshop.fragment.RegisterFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewById(R.id.tab_layout)
    protected TabLayout tabLayout;
    @ViewById(R.id.view_pager)
    protected ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    @AfterViews
    public void init() {

        LoginFragment_ loginFragment_ = (LoginFragment_) LoginFragment_.builder().build();
        fragmentList.add(loginFragment_);

        RegisterFragment_ registerFragment_ = (RegisterFragment_) RegisterFragment_.builder().build();
        fragmentList.add(registerFragment_);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("LOGIN");
        tabLayout.getTabAt(1).setText("REGISTRACIJA");
    }


    public void goToMainScreen() {
        MainActivity_.IntentBuilder_ builder = MainActivity_.intent(LoginActivity.this);
        builder.start();
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
