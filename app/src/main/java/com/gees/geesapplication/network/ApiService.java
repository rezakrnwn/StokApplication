package com.gees.geesapplication.network;

import com.gees.geesapplication.model.customer.Customer;
import com.gees.geesapplication.model.items.Items;
import com.gees.geesapplication.model.response.ResponseAddBarang;
import com.gees.geesapplication.model.satuan.Satuan;
import com.gees.geesapplication.model.shelf.Shelf;
import com.gees.geesapplication.model.stok.AddStokBarang;
import com.gees.geesapplication.model.stok.RemoveStokBarang;
import com.gees.geesapplication.model.stok.StokById;
import com.gees.geesapplication.model.supplier.Supplier;
import com.gees.geesapplication.model.user.SignupResponse;
import com.gees.geesapplication.model.user.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by SERVER on 11/09/2017.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("stokapi/v1/login")
    Call<UserResponse> doLogin(
            @Field("LoginForm[login]") String email,
            @Field("LoginForm[password]") String password
    );

    @FormUrlEncoded
    @POST("stokapi/v1/barang/new")
    Call<ResponseAddBarang> newBarang(
            @Field("Barang[nama]") String nama,
            @Field("Barang[id_satuan]") int stok,
            @Field("Barang[id_company]") int company,
            @Query("access_token") String token

    );

    @FormUrlEncoded
    @POST("stokapi/v1/barang/stokin")
    Call<AddStokBarang> addStok(
            @Field("id_company") int companyId,
            @Field("id_barang") int barangId,
            @Field("id_shelf") int shelfId,
            @Field("id_supplier") int supplierId,
            @Field("qty") double qty,
            @Field("tanggal") String date,
            @Query("access_token") String token

    );

    @FormUrlEncoded
    @POST("stokapi/v1/barang/stokout")
    Call<RemoveStokBarang> removeStok(
            @Field("id_company") int companyId,
            @Field("id_barang") int barangId,
            @Field("id_shelf") int shelfId,
            @Field("id_customer") int customerId,
            @Field("qty") double qty,
            @Field("tanggal") String date,
            @Query("access_token") String token

    );

    @FormUrlEncoded
    @POST("stokapi/v1/signup")
    Call<SignupResponse> createUser(
            @Field("register-form[username]") String username,
            @Field("register-form[email]") String email,
            @Field("register-form[password]") String password

    );

    @GET("stokapi/v1/barang/currentstok")
    Call<Items> getBarang(
            @Query("company_id") int companyId,
            @Query("access_token") String token
    );

    @GET("stokapi/v1/satuan/list")
    Call<Satuan> getSatuan(
            @Query("company_id") int companyId,
            @Query("access_token") String token
    );

    /*@GET("stokapi/v1/satuan/list")
    Call<Satuan> getSatuan2(
            @Query("company_id") int companyId,
            @Query("access_token") String token
    );*/

    @GET("stokapi/v1/barang/orderby")
    Call<Items> sortBarangBy(
            @Query("company_id") int companyId,
            @Query("field") String field,
            @Query("sort") String sort,
            @Query("access_token") String token
    );

    @GET("stokapi/v1/shelf/list")
    Call<Shelf> getShelf(
            @Query("company_id") int companyId,
            @Query("access_token") String token
    );

    //baru
    /*@GET("stokapi/v1/barang/stokbyidbarang")
    Call<StokById> getStokById(
            @Query("id_barang") int idBarang,
            @Query("id_shelf") int idShelf,
            @Query("access_token") String token
    );*/

    //baru
    /*@GET("stokapi/v1/barang/shelfstok")
    Call<ShelfStok> getShelfStok(
            @Query("id_barang") int idBarang,
            @Query("access_token") String token
    );*/

    @GET("stokapi/v1/customer/list")
    Call<Customer> getCustomer(
            @Query("company_id") int companyId,
            @Query("access_token") String token
    );

    @GET("stokapi/v1/supplier/list")
    Call<Supplier> getSupplier(
            @Query("access_token") String token
    );

    @GET("stokapi/v1/supplier/detail")
    Call<Supplier> getDetailSupplier(
            @Query("id_supplier") String idSupplier,
            @Query("access_token") String token
    );

    @GET("stokapi/v1/customer/detail")
    Call<Customer> getCustomerDetail(
            @Query("id_customer") String idCustomer,
            @Query("access_token") String token
    );

    @GET("stokapi/v1/customer/detail")
    Observable<Customer> getRxCustomer(
            @Query("id_customer") String idCustomer,
            @Query("access_token") String token
    );
}
