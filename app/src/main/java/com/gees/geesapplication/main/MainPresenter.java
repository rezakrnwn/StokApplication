package com.gees.geesapplication.main;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.gees.geesapplication.adapter.ItemAdapter;
import com.gees.geesapplication.base.BasePresenter;
import com.gees.geesapplication.model.items.Barang;
import com.gees.geesapplication.model.items.Items;
import com.gees.geesapplication.network.ApiClient;
import com.gees.geesapplication.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by SERVER on 12/09/2017.
 */

public class MainPresenter implements BasePresenter<MainView> {
    MainView mainView;
    ItemAdapter itemAdapter;
    List<Barang> itemsList = new ArrayList<>();
    ArrayList<Barang>  barangArrayList = new ArrayList<>();
    LinearLayoutManager mLayoutManager;

    @Override
    public void attachView(MainView view) {
        this.mainView = view;
    }

    @Override
    public void detachView() {
        this.mainView = null;
    }

    @Override
    public boolean isViewAttached() {
        return this.mainView != null;
    }

    public void getListItem(int company,String token){
        Log.d("T E S T","123");
        Log.d("T E S T","321");
        Retrofit retrofit = ApiClient.getClient(mainView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Items> itemsCall = apiService.getBarang(company,token);
        itemsCall.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {

                Log.d("M A S U K","response");

                if(response.body() != null){
                    Items items = response.body();
                    List<Barang> barangList = items.getBarang();

                    if(items.getStatus()){
                        for(int i=0;i<barangList.size();i++){
                            barangList.get(i).setRecyclerType(0);
                        }

                        itemsList.clear();
                        itemsList.addAll(barangList);
                        barangArrayList.clear();
                        barangArrayList.addAll(barangList);
                        itemAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(mainView.getContext(), "Data Kosong",
                                Toast.LENGTH_LONG).show();
                    }

                    mainView.loadSuccess();
                }else{
                    Toast.makeText(mainView.getContext(), "Cek koneksi internet Anda",
                            Toast.LENGTH_LONG).show();
                    Log.d("NO CONNECTION "," CHECK YOUR CONNECTION");
                }
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
                Log.d("M A S U K","failure");
                Toast.makeText(mainView.getContext(), "Cek koneksi internet Anda",
                        Toast.LENGTH_LONG).show();
                mainView.loadSuccess();
            }
        });
    }

    public void sortItemBy(int company, String column, String sort ,String token){
        Log.d("T E S T","123");
        Log.d("T E S T","321");
        Retrofit retrofit = ApiClient.getClient(mainView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Items> itemsCall = apiService.sortBarangBy(company,column,sort,token);
        itemsCall.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {

                Log.d("M A S U K","response");

                if(response.body() != null){
                    Items items = response.body();
                    List<Barang> barangList = items.getBarang();

                    if(items.getStatus()){
                        for(int i=0;i<barangList.size();i++){
                            barangList.get(i).setRecyclerType(0);
                        }

                        itemsList.clear();
                        itemsList.addAll(barangList);
                        barangArrayList.clear();
                        barangArrayList.addAll(barangList);
                        itemAdapter.notifyDataSetChanged();
                    }else{
                        /*Toast.makeText(mainView.getContext(), "Data Kosong",
                                Toast.LENGTH_LONG).show();*/
                        itemsList.clear();
                        barangArrayList.clear();
                        itemAdapter.notifyDataSetChanged();
                    }

                    mainView.loadSuccess();
                }else{
                    /*Toast.makeText(mainView.getContext(), "Cek koneksi internet Anda",
                            Toast.LENGTH_LONG).show();*/
                    Log.d("NO CONNECTION "," CHECK YOUR CONNECTION");
                    itemsList.clear();
                    barangArrayList.clear();
                    itemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
                Log.d("M A S U K","failure");
                /*Toast.makeText(mainView.getContext(), "Cek koneksi internet Anda",
                        Toast.LENGTH_LONG).show();*/
                mainView.loadSuccess();
            }
        });
    }

    public void getSearchResult(String text){
        itemAdapter.filter(text);
        Log.d("GetSearch","111111");
    }

    public void getSyncAdapter(RecyclerView recyclerView){
        mLayoutManager = new LinearLayoutManager(mainView.getContext());
        itemAdapter = new ItemAdapter(itemsList,barangArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);
    }
}
