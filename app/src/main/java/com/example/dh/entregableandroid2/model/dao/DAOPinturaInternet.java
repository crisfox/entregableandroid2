package com.example.dh.entregableandroid2.model.dao;

import com.example.dh.entregableandroid2.model.pojo.ContainerPintura;
import com.example.dh.entregableandroid2.model.pojo.Pintura;
import com.example.dh.entregableandroid2.util.ResultListener;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DH on 11/6/2018.
 */

public class DAOPinturaInternet {

    private Retrofit retrofit;
    private DAOPinturaRetrofitService daoMoviesRetrofitService;

    public DAOPinturaInternet() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.myjson.com/")
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.client(httpClient.build()).build();
        daoMoviesRetrofitService = retrofit.create(DAOPinturaRetrofitService.class);
    }

    public void obtenerPinturaDeInternet(final ResultListener<List<Pintura>> listenerControler) {
        Call<ContainerPintura> containerPinturaCall = daoMoviesRetrofitService.getPinturas();
        containerPinturaCall.enqueue(new Callback<ContainerPintura>() {
            @Override
            public void onResponse(Call<ContainerPintura> call, Response<ContainerPintura> response) {
                ContainerPintura containerPintura = response.body();
                listenerControler.finish(containerPintura.getListaDePinturas());
            }

            @Override
            public void onFailure(Call<ContainerPintura> call, Throwable t) {

            }
        });
    }
}
