package com.gees.geesapplication.auth;

import android.util.Log;
import android.widget.Toast;

import com.gees.geesapplication.base.BasePresenter;
import com.gees.geesapplication.config.SharedPreferenceLogin;
import com.gees.geesapplication.model.user.Info;
import com.gees.geesapplication.model.user.UserResponse;
import com.gees.geesapplication.network.ApiClient;
import com.gees.geesapplication.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by SERVER on 11/09/2017.
 */

public class LoginPresenter implements BasePresenter<LoginView> {

    LoginView loginView;

    @Override
    public void attachView(LoginView view) {
        this.loginView = view;
    }

    @Override
    public void detachView() {
        this.loginView = null;
    }

    @Override
    public boolean isViewAttached() {
        return this.loginView != null;
    }

    public void doLogin(final String username, String password){
        loginView.onProcess();
        Retrofit retrofit = ApiClient.getClient(loginView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<UserResponse> loginCall = apiService.doLogin(username,password);
        loginCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {

                if(response.body() != null){
                    UserResponse userResponse = response.body();
                    Info info = userResponse.getInfo();
                    Boolean status = info.getStatus();

                    if(status){
                        String mApiToken = info.getAccessToken();
                        String mName = info.getUsername();
                        int mRoleId = info.getRoleId();
                        String mRoleName = info.getRoleName();
                        int mCompany = info.getCompanyId();

                        SharedPreferenceLogin sharedPreferenceLogin = new SharedPreferenceLogin();
                        sharedPreferenceLogin.save(loginView.getContext(),mApiToken,mName,mRoleId,
                                mRoleName,mCompany);

                        loginView.onSuccess();
                    }else{
                        loginView.onFailed();
                        Toast.makeText(loginView.getContext(),"Username atau Password salah",
                                Toast.LENGTH_LONG).show();
                    }

                }else{
                    loginView.onFailed();
                    Toast.makeText(loginView.getContext(), "Cek koneksi internet Anda",
                            Toast.LENGTH_LONG).show();
                    Log.d("NO CONNECTION "," CHECK YOUR CONNECTION");
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
    }
}
