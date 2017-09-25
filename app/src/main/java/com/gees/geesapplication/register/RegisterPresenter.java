package com.gees.geesapplication.register;

import android.widget.Toast;

import com.gees.geesapplication.base.BasePresenter;
import com.gees.geesapplication.model.user.SignupResponse;
import com.gees.geesapplication.network.ApiClient;
import com.gees.geesapplication.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by SERVER on 12/09/2017.
 */

public class RegisterPresenter implements BasePresenter<RegisterView> {

    RegisterView registerView;

    @Override
    public void attachView(RegisterView view) {
        this.registerView = view;
    }

    @Override
    public void detachView() {
        this.registerView = null;
    }

    @Override
    public boolean isViewAttached() {
        return this.registerView != null;
    }

    public void doRegister(final String username, String email, String password){
        Retrofit retrofit = ApiClient.getClient(registerView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<SignupResponse> loginCall = apiService.createUser(username,email,password);
        loginCall.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, retrofit2.Response<SignupResponse> response) {

                if(response.body() != null){
                    SignupResponse signupResponse = response.body();
                    Boolean status = signupResponse.getStatus();

                    if(status){
                        registerView.onSuccess();
                    }else{
                        registerView.onFailed();
                    }

                }else{
                    registerView.noConnection();
                }

            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
            }
        });
    }
}
