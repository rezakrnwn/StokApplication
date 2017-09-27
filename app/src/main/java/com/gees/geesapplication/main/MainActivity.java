package com.gees.geesapplication.main;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gees.geesapplication.R;
import com.gees.geesapplication.adapter.ItemAdapter;
import com.gees.geesapplication.add.AddBarangActivity;
import com.gees.geesapplication.auth.LoginActivity;
import com.gees.geesapplication.config.SharedPreferenceLogin;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.rvItem)
    RecyclerView rvItem;

    @BindView(R.id.tvUsername)
    TextView tvUsername;

    @BindView(R.id.tvRole)
    TextView tvRole;

    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    SharedPreferenceLogin sharedPreferenceLogin = new SharedPreferenceLogin();
    MainPresenter mainPresenter = new MainPresenter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        authRoleView();

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1){
            checkPermissionForReadWriteStorage();
        }

        mainPresenter.attachView(this);
        mainPresenter.getSyncAdapter(rvItem);
        mainPresenter.getListItem(sharedPreferenceLogin.getValue(getApplicationContext()).getInt("company",0),
                sharedPreferenceLogin.getValue(this).getString("apiToken",""));
        tvUsername.setText(sharedPreferenceLogin.getValue(this).getString("name",""));
        tvRole.setText(sharedPreferenceLogin.getValue(getApplicationContext()).getString("roleName",""));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainPresenter.getListItem(sharedPreferenceLogin.getValue(getApplicationContext()).getInt("company",0),
                        sharedPreferenceLogin.getValue(getApplicationContext()).getString("apiToken",""));
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txtSearch = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                mainPresenter.getSearchResult(txtSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void authRoleView(){
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        if(sharedPreferenceLogin.getValue(this).getInt("roleId",0) == 1){
            fabAdd.setVisibility(View.VISIBLE);
        }else {
            fabAdd.setVisibility(View.GONE);
        }
    }

    void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @OnClick(R.id.fabAdd)
    void fabAdd(){
        startActivity(new Intent(MainActivity.this,AddBarangActivity.class));
        overridePendingTransition(R.anim.slide_out_up, R.anim.stay);
    }

    @OnClick(R.id.btnLogout)
    void btnLogout(){
        doLogout();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

    public void doLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharedPreferenceLogin.clearSharedPreference(getApplicationContext());
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void loadSuccess() {
        swipeRefreshLayout.setRefreshing(false);
    }

    protected void checkPermissionForReadWriteStorage() {

        final int writeExternalStorage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        final int readExternalStorage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        //final int cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        if (writeExternalStorage == PackageManager.PERMISSION_GRANTED && readExternalStorage == PackageManager.PERMISSION_GRANTED) {


        } else {
            boolean requestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
            if (requestPermissionRationale) {

                Toast.makeText(MainActivity.this, "Please provide  permission for read storage.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                intent.setData(uri);
                MainActivity.this.startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                //ActivityCompat.requestPermissions(MainActivity.this,new String[]{android.Manifest.permission.CAMERA}, 1);
            }
        }
    }
}
