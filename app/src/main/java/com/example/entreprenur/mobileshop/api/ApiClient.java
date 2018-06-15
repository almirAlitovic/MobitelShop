package com.example.entreprenur.mobileshop.api;

import com.example.entreprenur.mobileshop.api.models.BrandModel;
import com.example.entreprenur.mobileshop.api.models.CpuApiModel;
import com.example.entreprenur.mobileshop.api.models.HistoryModel;
import com.example.entreprenur.mobileshop.api.models.MemoModel;
import com.example.entreprenur.mobileshop.api.models.MobileModel;
import com.example.entreprenur.mobileshop.api.models.OrderRequest;
import com.example.entreprenur.mobileshop.api.models.OrderResponse;
import com.example.entreprenur.mobileshop.api.models.OsApiModel;
import com.example.entreprenur.mobileshop.api.models.UserModel;
import com.example.entreprenur.mobileshop.api.models.UserRegister;
import com.example.entreprenur.mobileshop.api.models.UserRegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiClient {

    @POST("api/Kupci")
    Call<UserRegisterResponse> registerUser(@Body UserRegister userRegister);

    @GET("/api/Kupci/getKupacByLogin/{userName}/{password}")
    Call<UserModel> getUser(@Path("userName") String userName, @Path("password") String password);

    @GET("/api/Mobiteli")
    Call<List<MobileModel>> getMobiles();

    @GET("/api/Mobiteli/GetMobiteliByNaziv/{naziv}")
    Call<List<MobileModel>> getMobilesByName(@Path("naziv") String name);

    @GET("/api/Mobiteli/GetMobiteliByFilteri/{brendId}/{cpuId}/{memorijaId}/{osId}")
    Call<List<MobileModel>> getMobilesFilter(@Path("brendId") int brendId, @Path("cpuId") int cpuId,
                                             @Path("memorijaId") int memorijaId, @Path("osId") int osId);

    @GET("/api/Mobiteli/{id}")
    Call<MobileModel> getMobileById(@Query("id") int id);

    @GET("/api/Brendovi")
    Call<List<BrandModel>> getBrands();

    @GET("/api/Memorija")
    Call<List<MemoModel>> getMemo();

    @GET("/api/OperativniSistem")
    Call<List<OsApiModel>> getOs();

    @GET("/api/CpuJezgre")
    Call<List<CpuApiModel>> getCpus();

    @GET("/api/Narudzbe/GetHistorijaByKupacId/{userId}")
    Call<List<HistoryModel>> getHistory(@Path("userId") Long userId);

    @POST("api/Narudzbe")
    Call<OrderResponse> sendOrder(@Body OrderRequest orderRequest);
}
