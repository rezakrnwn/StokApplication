package com.gees.geesapplication.add;

import android.util.Log;
import android.widget.Toast;

import com.gees.geesapplication.base.BasePresenter;
import com.gees.geesapplication.model.response.ResponseAddBarang;
import com.gees.geesapplication.model.satuan.Satuan;
import com.gees.geesapplication.model.satuan.SatuanDatum;
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

public class AddBarangPresenter implements BasePresenter<AddBarangView> {
    AddBarangView addBarangView;
    List<SatuanDatum> satuanDatumList = new ArrayList<>();

    @Override
    public void attachView(AddBarangView view) {
        this.addBarangView = view;
    }

    @Override
    public void detachView() {
        this.addBarangView = null;
    }

    @Override
    public boolean isViewAttached() {
        return this.addBarangView != null;
    }

    public void getSatuan(int companyId,String token){
        final List<String> stringList = new ArrayList<>();
        Retrofit retrofit = ApiClient.getClient(addBarangView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Satuan> satuanCall = apiService.getSatuan(companyId,token);
        satuanCall.enqueue(new Callback<Satuan>() {
            @Override
            public void onResponse(Call<Satuan> call, Response<Satuan> response) {

                if(response.body() != null){
                    if(response.raw().cacheResponse() != null){}
                    Satuan satuan = response.body();

                    if(satuan.getStatus()){
                        satuanDatumList = satuan.getData();
                        Log.d("DatumList ",""+satuanDatumList.size());
                        for(int i=0;i<satuanDatumList.size();i++){
                            stringList.add(satuanDatumList.get(i).getNama());
                        }
                        addBarangView.getArrayList(satuanDatumList,stringList);
                    }else{
                        Toast.makeText(addBarangView.getContext(),"Data satuan kosong"
                                ,Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(addBarangView.getContext(),
                            "Cek koneksi anda",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Satuan> call, Throwable t) {

            }
        });
        //return stringList;
    }

    void newBarang(int company,String name, int stok, String token){
        Retrofit retrofit = ApiClient.getClient(addBarangView.getContext());
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseAddBarang> addCall = apiService.newBarang(name,stok,company,token);
        addCall.enqueue(new Callback<ResponseAddBarang>() {
            @Override
            public void onResponse(Call<ResponseAddBarang> call, retrofit2.Response<ResponseAddBarang> response) {

                if(response.body() != null){
                    ResponseAddBarang responseAddBarang = response.body();
                    Boolean status = responseAddBarang.getStatus();

                    if(status){
                        Toast.makeText(addBarangView.getContext(),"Berhasil",
                                Toast.LENGTH_LONG).show();
                        addBarangView.onSuccess();
                    }else{
                        Toast.makeText(addBarangView.getContext(),"Input Gagal",
                                Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(addBarangView.getContext(),"Cek Koneksi Internet",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseAddBarang> call, Throwable t) {
            }
        });
    }
}
