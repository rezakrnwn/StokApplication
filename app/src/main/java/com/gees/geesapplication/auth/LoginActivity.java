package com.gees.geesapplication.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gees.geesapplication.R;
import com.gees.geesapplication.config.SharedPreferenceLogin;
import com.gees.geesapplication.main.MainActivity;
import com.gees.geesapplication.register.RegisterActivity;
import com.gees.geesapplication.utils.KeyboardLayoutListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SERVER on 11/09/2017.
 */

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.pbLogin)
    ProgressBar pbLogin;

    @BindView(R.id.rlRoot)
    RelativeLayout rlRoot;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.tvWrongLogin)
    TextView tvWrongLogin;

    LoginPresenter loginPresenter = new LoginPresenter();
    SharedPreferenceLogin sharedPreferenceLogin = new SharedPreferenceLogin();
    KeyboardLayoutListener keyboardLayoutListener;

    private boolean loggedIn = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        keyboardLayoutListener = new KeyboardLayoutListener(rlRoot);
        keyboardLayoutListener.attachKeyboardListeners();

        pbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFailed();
            }
        });
    }

    @OnClick(R.id.btnLogin)
    void btnLogin(){

        String username = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        loginPresenter.attachView(this);
        loginPresenter.doLogin(username,password);
    }

    @OnClick(R.id.btnRegister)
    void btnRegister(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onProcess() {
        pbLogin.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);
    }

    @Override
    public void onFailed() {
        pbLogin.setVisibility(View.GONE);
        btnLogin.setEnabled(true);
        tvWrongLogin.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loggedIn = sharedPreferenceLogin.getValue(this).getBoolean(SharedPreferenceLogin.LOGGEDIN_SHARED_PREF,false);

        if(loggedIn){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardLayoutListener.setRelativeLayout(2);
        keyboardLayoutListener.removeLayoutListener();
    }
}
