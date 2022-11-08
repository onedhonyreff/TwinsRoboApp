package com.codepan.twinsrobo_apps.APIs;

import com.codepan.twinsrobo_apps.Models.ServerResponse;
import com.codepan.twinsrobo_apps.Models.ResponseModelInfoLomba;
import com.codepan.twinsrobo_apps.Models.ResponseModelLogin;
import com.codepan.twinsrobo_apps.Models.ResponseModelUser;
import com.codepan.twinsrobo_apps.Models.ResponseModelWishlist;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface APIRequestData {

    @FormUrlEncoded
    @POST("retrieve_user.php")
    Call<ResponseModelUser> ardRetrieveUser(
            @Field("id_user") String id_user
    );

    // OTHER CONNECTIONS

    @Multipart
    @POST("upload_video.php")
    Call<ServerResponse> upload(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
    );

    @GET("retrieve_info_lomba.php")
    Call<ResponseModelInfoLomba> ardRetrieveDataInfoLomba();

    @FormUrlEncoded
    @POST("retrieve_info_lomba_spesifik.php")
    Call<ResponseModelInfoLomba> ardRetrieveDataInfoLombaSpesifik(
            @Field("kategori_info_lomba") String kategori_info_lomba
    );

    @FormUrlEncoded
    @POST("retrieve_login.php")
    Call<ResponseModelLogin> ardRetrieveLogin(
            @Field("username_or_email") String username_or_email,
            @Field("password") String password,
            @Field("metode") String metode
    );

    @FormUrlEncoded
    @POST("retrieve_login.php")
    Call<ResponseModelLogin> ardCekRegister(
            @Field("username_or_email") String username_or_email,
            @Field("metode") String metode
    );

    @FormUrlEncoded
    @POST("create_user.php")
    Call<ResponseModelUser> ardRegister(
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username,
            @Field("nama_depan") String nama_depan,
            @Field("nama_belakang") String nama_belakang,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("nama_sekolah") String nama_sekolah,
            @Field("string_foto") String string_foto
    );

    @FormUrlEncoded
    @POST("update_user.php")
    Call<ResponseModelUser> ardUpdateUser(
            @Field("id_user") String id_user,
            @Field("username") String username,
            @Field("email") String email,
            @Field("nama_depan") String nama_depan,
            @Field("nama_belakang") String nama_belakang,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("asal_sekolah") String asal_sekolah,
            @Field("string_foto") String string_foto,
            @Field("password_user") String password_user
    );

    @FormUrlEncoded
    @POST("wishlist_info_lomba.php")
    Call<ResponseModelWishlist> ardWishlistInfoLomba(
            @Field("id_user") String id_lomba,
            @Field("id_event") String id_event,
            @Field("mode") String mode
    );

    @FormUrlEncoded
    @POST("update_password.php")
    Call<ResponseModelUser> ardUpdatePassword(
            @Field("email_user") String id_lomba,
            @Field("new_password") String id_event
    );
}

