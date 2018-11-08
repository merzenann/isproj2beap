package com.example.merzensumagaysay.thesis2.api;

import com.example.merzensumagaysay.thesis2.model.Crud;
import com.example.merzensumagaysay.thesis2.model.Message;
import com.example.merzensumagaysay.thesis2.model.SafeExits;
import com.example.merzensumagaysay.thesis2.model.User1;
import com.example.merzensumagaysay.thesis2.model.Value;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface InterfaceAPI {

    @FormUrlEncoded
    @POST("loginapp/login.php") //@POST("loginapp/login.php")
    Call<User1> performUserLogin(@Field("username") String UserName,
                                 @Field("password") String UserPassword);


    @FormUrlEncoded
    @POST("CRUD/insert.php")
    Call<Crud> add(@Field("id") String id,
                   @Field("calamityName") String calamityName,
                   @Field("description") String description,
                   @Field ("something") String something);

    @GET("CRUD/view.php")
    Call<Value> view();


    @FormUrlEncoded
    @POST("CRUD/search.php")
    Call  <Value> search(@Field("search") String search);

    @FormUrlEncoded
    @POST("CRUD/delete.php")
    Call  <Value> delete(@Field("id") String id);

    @FormUrlEncoded
    @POST("CRUD/update.php")
    Call<Value> update(@Field("id") String id,
                       @Field("calamityName") String calamityName,
                       @Field("description") String description,
                       @Field ("something") String something);
    @FormUrlEncoded
    @POST("CRUD/messages/send.php")
    Call<Message> sendMessage(@Field("receiverID") int receiverID,
                              @Field("senderID") int senderID,
                              @Field("message") String message);

    @FormUrlEncoded
    @POST("CRUD/messages/changestatus.php")
    Call<Message> updateMessage(@Field("id") int id,
                                @Field("receiverID") int receiverID);

    @GET("CRUD/messages/pending.php")
    Call <Value> getPendingMessage();


    @FormUrlEncoded
    @POST("CRUD/upload.php")
    Call<Value> uploadImage(@Field("calamityName") String calamityName,
                            @Field("image") String image);
    @GET("CRUD/alert/alert.php")
    Call<Value> sendAlert();

    @FormUrlEncoded
    @POST("CRUD/alert/alertmessage.php")
    Call<Value> alertUsers(@Field("calamityID") int calamityID,
                           @Field("userID") int userID);


    @Headers("content-type:application/json")
    @GET("CRUD/fetch.php")
    Call<List<SafeExits>> getExit();

    @FormUrlEncoded
    @POST("CRUD/upd.php")
    Call<ResponseBody> updateExit(@Field("exitID") int exitID,
                                  @Field("iStatus") int iStatus);


    @Headers("content-type:application/json")
    @GET("admin/get.php/")
    Call<List<SafeExits>> getMessage();

    @FormUrlEncoded
    @POST("admin/ins.php/")
    Call<String> sendMessage(@Field("exitID") int exitID,
                             @Field("instruction") String instruction);
}
