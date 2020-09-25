package com.hamzeh.moraghebehapp.retrofit;

import com.hamzeh.moraghebehapp.data.database.entities.ResultsEntity;
import com.hamzeh.moraghebehapp.data.pojo.AmalApiPojo;
import com.hamzeh.moraghebehapp.data.database.entities.ArbayiinEntity;
import com.hamzeh.moraghebehapp.data.pojo.LoginInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("/wordpress/wp-json/moraghebeh/v1/login/")
    Call<LoginInfo> userInfo(
            @Query("user") String user,
            @Query("pass") String pass
    );

    @GET("/wordpress/wp-json/moraghebeh/v1/manageArbayiin")
    Call<List<ArbayiinEntity>> getArbayiin(
            @Query("userId") int id
    );

    @GET("/wordpress/wp-json/moraghebeh/v1/manageAmal")
    Call<List<AmalApiPojo>> getAmal(
            @Query("userId") int id,
            @Query("arbayiinId") int arbayiinId
    );

    /**
     * Gets results from server in the form of @List<ResultsEntity>>
     * Used in ResultsCall class
     *
     * @param id userId
     * @param arbayiinId arbayiinId
     * @return returns a List of ResultsEntity
     */
    @GET("/wordpress/wp-json/moraghebeh/v1/getResults")
    Call<List<ResultsEntity>> getResults(
            @Query("userId") int id,
            @Query("arbayiinId") int arbayiinId

    );

    /**
     * Sends results to the server
     * Used in ResultsCall class
     *
     * @param result All Results laved in @CareTaker in type of string with "," delimiter
     * @param arbayiinId arbayiinId
     * @param day the day number of type String
     * @param userId the userId of type integer
     * @return returns the return of wp_insert_post() from wordpress
     */
    @POST("/wordpress/wp-json/moraghebeh/v1/manageAmal/")
    Call<ResponseBody> postResults(
            @Query("results") String result,
            @Query("arbayiin") int arbayiinId,
            @Query("day") String day,
            @Query("author") int userId

    );

    @POST("/wordpress/wp-json/moraghebeh/v1/manageAmal")
    Observable<Integer> insertResults(
            @Query("results") String result,
            @Query("arbayiin") int arbayiinId,
            @Query("day") String day,
            @Query("author") int userId
    );
}
