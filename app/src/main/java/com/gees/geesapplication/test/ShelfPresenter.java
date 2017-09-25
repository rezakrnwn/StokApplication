package com.gees.geesapplication.test;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.gees.geesapplication.adapter.ShelfAdapter;
import com.gees.geesapplication.base.BasePresenter;
import com.gees.geesapplication.network.ApiClient;
import com.gees.geesapplication.network.ApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ServerToko on 20/09/2017.
 */

public class ShelfPresenter implements BasePresenter<ShelfView> {
    ShelfView shelfView;
    ShelfAdapter shelfAdapter;
    LinearLayoutManager mLayoutManager;
    //List<ShelfStokData> shelfList = new ArrayList<>();
    //List<AddShelf> stringList = new ArrayList<>();

    @Override
    public void attachView(ShelfView view) {
        this.shelfView = view;
    }

    @Override
    public void detachView() {
        this.shelfView = null;
    }

    @Override
    public boolean isViewAttached() {
        return this.shelfView != null;
    }

    /*public void getDetailStok(final int id, String token){
        *//*AddShelf addShelf = new AddShelf("0","Semua");
        stringList.add(addShelf);*//*
        shelfList.clear();
        ShelfStokData shelfStokData = new ShelfStokData("0","Semua");
        shelfList.add(shelfStokData);
        Retrofit retrofit = ApiClient.getClient(shelfView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ShelfStok> itemsCall = apiService.getShelfStok(id,token);
        itemsCall.enqueue(new Callback<ShelfStok>() {
            @Override
            public void onResponse(Call<ShelfStok> call, Response<ShelfStok> response) {

                Log.d("M A S U K","response");

                if(response.body() != null){
                    ShelfStok shelfStok = response.body();
                    List<ShelfStokData> shelfStokData = shelfStok.getData();

                    Log.d("Isinya ",""+response.body());

                    if(shelfStok.getStatus()){
                        //shelfList.add("Semua");
                        shelfList.addAll(shelfStokData);
                        shelfAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(shelfView.getContext(), "Data Kosong",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(shelfView.getContext(), "Cek koneksi internet Anda",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ShelfStok> call, Throwable t) {
                Log.d("M A S U K","failure");
            }

        });
    }*/

    public void getSyncAdapter(RecyclerView recyclerView){
        mLayoutManager = new LinearLayoutManager(shelfView.getContext(),LinearLayoutManager.HORIZONTAL,false);
        //shelfAdapter = new ShelfAdapter(shelfList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(shelfAdapter);
    }
}
