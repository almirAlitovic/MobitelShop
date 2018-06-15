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
import com.example.entreprenur.mobileshop.api.models.UserRegister;
import com.example.entreprenur.mobileshop.api.models.UserRegisterResponse;
import com.example.entreprenur.mobileshop.utils.SharedPreferencesUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_register)
public class RegisterFragment extends Fragment {

    @ViewById(R.id.input_name)
    protected EditText inputName;
    @ViewById(R.id.input_lastname)
    protected EditText inputLastName;
    @ViewById(R.id.input_email)
    protected EditText inputEmail;
    @ViewById(R.id.input_address)
    protected EditText inputAddress;
    @ViewById(R.id.input_username)
    protected EditText inputUsername;
    @ViewById(R.id.input_password)
    protected EditText inputPassword;
    @ViewById(R.id.progress_holder)
    protected RelativeLayout progressHolder;


    SharedPreferencesUtil sharedPreferencesUtil;

    @AfterViews
    public void init () {
        sharedPreferencesUtil = new SharedPreferencesUtil(getContext());
    }

    @Click(R.id.btn_register)
    public void registerUser() {

        LoginActivity activity = (LoginActivity) getActivity();
        if(activity == null || activity.isFinishing() || !isAdded()) {
            return;
        }

        if (checkIsEverythingSet()) {
            register();
        } else {
            Toast.makeText(activity, "Morate ispuniti sva polja", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkIsEverythingSet() {
        return !inputName.getText().toString().isEmpty()
                && !inputLastName.getText().toString().isEmpty()
                && !inputEmail.getText().toString().isEmpty()
                && !inputAddress.getText().toString().isEmpty()
                && !inputUsername.getText().toString().isEmpty()
                && !inputPassword.getText().toString().isEmpty();
    }

    private void register() {
        progressHolder.setVisibility(View.VISIBLE);
        String name = inputName.getText().toString();
        String lastname = inputLastName.getText().toString();
        String email = inputEmail.getText().toString();
        String pass = inputPassword.getText().toString();

        UserRegister userRegister = new UserRegister();
        userRegister.setEmail(email);
        userRegister.setIme(name);
        userRegister.setLozinka(pass);
        userRegister.setPrezime(lastname);
        userRegister.setKorisnickoIme(name);
        userRegister.setStatus(true);
        userRegister.setDatumRegistracije(new Date());

        final LoginActivity activity = (LoginActivity) getActivity();
        if (activity == null || activity.isFinishing() || !isAdded()) {
            return;
        }

        Call<UserRegisterResponse> call = ServiceGenerator.createService(ApiClient.class).registerUser(userRegister);

        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {

                progressHolder.setVisibility(View.GONE);

                if (response == null || !response.isSuccessful()) {
                    Toast.makeText(activity, "Dogodila se greska", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginWithUser(response.body());
                activity.goToMainScreen();

            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                progressHolder.setVisibility(View.GONE);
                Toast.makeText(activity, "Dogodila se greska", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loginWithUser(UserRegisterResponse user) {
        sharedPreferencesUtil.setUserId(String.valueOf(user.Id));
        sharedPreferencesUtil.setUserName(user.KorisnickoIme);
    }
}
