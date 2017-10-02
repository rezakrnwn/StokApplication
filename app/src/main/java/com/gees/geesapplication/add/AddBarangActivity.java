package com.gees.geesapplication.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gees.geesapplication.R;
import com.gees.geesapplication.config.SharedPreferenceLogin;
import com.gees.geesapplication.main.MainActivity;
import com.gees.geesapplication.model.satuan.Satuan;
import com.gees.geesapplication.model.satuan.SatuanDatum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

/**
 * Created by SERVER on 12/09/2017.
 */

public class AddBarangActivity extends AppCompatActivity implements AddBarangView {

    @BindView(R.id.etNamaBarang)
    EditText etNamaBarang;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.spSatuan)
    Spinner spSatuan;

    SharedPreferenceLogin sharedPreferenceLogin = new SharedPreferenceLogin();
    AddBarangPresenter addBarangPresenter = new AddBarangPresenter();

    List<String> satuanList = new ArrayList<>();
    List<SatuanDatum> satuanDatumList = new ArrayList<>();

    int satuanId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barang);
        ButterKnife.bind(this);

        addBarangPresenter.attachView(this);
        addBarangPresenter.getSatuan(sharedPreferenceLogin.getValue(this).getInt("company",0),
                sharedPreferenceLogin.getValue(this).getString("apiToken",""));

        initToolbar();

        spSatuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                satuanId = satuanDatumList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void initSpinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, satuanList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSatuan.setAdapter(dataAdapter);
    }

    void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.stay,R.anim.slide_out_down);
            }
        });
    }

    @OnClick(R.id.btnSimpan)
    void btnSimpan(){
        final String namaBarang = etNamaBarang.getText().toString();

        if(namaBarang.isEmpty()){
            onFailed();
        }else{
            addBarangPresenter.attachView(this);
            addBarangPresenter.newBarang(sharedPreferenceLogin.getValue(getApplicationContext()).getInt("company",0),
                    namaBarang,satuanId,sharedPreferenceLogin.getValue(this).getString("apiToken",""));
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this,"Berhasil menambahkan Barang",
                Toast.LENGTH_LONG).show();
        startActivity(new Intent(AddBarangActivity.this, MainActivity.class));
    }

    @Override
    public void onFailed() {
        Toast.makeText(this,"Gagal menambahkan Barang",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void getArrayList(List<SatuanDatum> satuanDatumList,List<String> stringList) {
        satuanList = stringList;
        this.satuanDatumList = satuanDatumList;
        Log.d("S P I N N E R ",""+satuanList.get(0));
        initSpinner();
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
