package com.example.entreprenur.mobileshop.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.entreprenur.mobileshop.R;
import com.example.entreprenur.mobileshop.activity.LoginActivity;
import com.example.entreprenur.mobileshop.api.ApiClient;
import com.example.entreprenur.mobileshop.api.ServiceGenerator;
import com.example.entreprenur.mobileshop.api.models.UserModel;
import com.example.entreprenur.mobileshop.utils.SharedPreferencesUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {

    @ViewById(R.id.input_email)
    protected EditText inputEmail;
    @ViewById(R.id.input_password)
    protected EditText inputPassword;
    @ViewById(R.id.progress_holder)
    protected RelativeLayout progressHolder;

    SharedPreferencesUtil sharedPreferencesUtil;

    @AfterViews
    public void init () {
        sharedPreferencesUtil = new SharedPreferencesUtil(getContext());
    }


    @Click(resName = "login_button")
    public void login() {
        final LoginActivity activity = (LoginActivity) getActivity();
        //userSharedPreferences = new UserSharedPreferences(activity);

        if (isAllFill()) {
            String username = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();

            progressHolder.setVisibility(View.VISIBLE);
            Call<UserModel> call = ServiceGenerator.createService(ApiClient.class).getUser(username, password);

            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    progressHolder.setVisibility(View.GONE);
                    if (activity == null || activity.isFinishing() || !isAdded()) {
                        Toast.makeText(activity, "Pojavila se greska", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (response == null || !response.isSuccessful()) {
                        Toast.makeText(activity, "Korisnicko ime ili lozinka nisu tacni", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    loginWithUser(response.body());
                    activity.goToMainScreen();
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    progressHolder.setVisibility(View.GONE);
                    Toast.makeText(activity, "Pojavila se greska", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Korisnicko ime ili lozinka nedostaju", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginWithUser(UserModel user) {
        sharedPreferencesUtil.setUserId(String.valueOf(user.Id));
        sharedPreferencesUtil.setUserName(user.KorisnickoIme);
    }

    private boolean isAllFill() {
        return !inputEmail.getText().toString().isEmpty()
                && !inputPassword.getText().toString().isEmpty();
    }
}
