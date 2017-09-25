package com.gees.geesapplication.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.gees.geesapplication.R;
import com.gees.geesapplication.config.SharedPreferenceLogin;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ServerToko on 20/09/2017.
 */

public class ShelfActivity extends AppCompatActivity implements ShelfView {

    @BindView(R.id.rvShelf)
    RecyclerView rvShelf;

    ShelfPresenter shelfPresenter = new ShelfPresenter();
    SharedPreferenceLogin sharedPreferenceLogin = new SharedPreferenceLogin();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        shelfPresenter.attachView(this);
        /*shelfPresenter.getSyncAdapter(rvShelf);
        shelfPresenter.getDetailStok(20,
                sharedPreferenceLogin.getValue(getApplicationContext()).getString("apiToken",""));*/
    }

    @Override
    public Context getContext() {
        return this;
    }
}
