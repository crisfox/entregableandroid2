package com.example.dh.entregableandroid2.model.dao;

import com.example.dh.entregableandroid2.model.pojo.ContainerPintura;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by DH on 11/6/2018.
 */

public interface DAOPinturaRetrofitService {

    @GET("/bins/x858r")
    Call<ContainerPintura> getPinturas();


}
