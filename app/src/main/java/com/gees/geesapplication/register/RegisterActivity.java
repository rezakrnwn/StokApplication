package com.gees.geesapplication.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.gees.geesapplication.R;
import com.gees.geesapplication.auth.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SERVER on 12/09/2017.
 */

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    @BindView(R.id.etUsername)
    EditText etUsername;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    RegisterPresenter registerPresenter = new RegisterPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initActionBar();
    }

    void initActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_go_back_24px);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @OnClick(R.id.btnDaftar)
    void btnDaftar(){
        if(etUsername.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() ||
                etPassword.getText().toString().isEmpty()){
            onFailed();
        }else{
            registerPresenter.attachView(this);
            registerPresenter.doRegister(etUsername.getText().toString(),
                    etEmail.getText().toString(),etPassword.getText().toString());
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this,"Berhasil mendaftar",
                Toast.LENGTH_LONG).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    @Override
    public void onFailed() {
        Toast.makeText(this,"Gagal mendaftar",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void noConnection() {
        Toast.makeText(this,"Cek koneksi internet anda",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
