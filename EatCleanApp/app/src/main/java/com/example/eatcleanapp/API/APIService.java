package com.example.eatcleanapp.API;


import com.example.eatcleanapp.model.favoriterecipes;
import com.example.eatcleanapp.model.recipes;
import com.example.eatcleanapp.model.users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
     public static final String URL = "https://msteatclean.000webhostapp.com/";
     Gson gson = new GsonBuilder()
             .setDateFormat("yyyy MM dd HH:mm:ss")
             .setLenient()
             .create();
     APIService apiService = new Retrofit.Builder()
             .baseUrl(URL)
             .addConverterFactory(GsonConverterFactory.create(gson))
             .build()
             .create(APIService.class);

     @GET("getUser.php")
     Call<List<users>> getUser();

     @FormUrlEncoded
     @POST("addFavoriteRecipes.php")
     Call<favoriterecipes> addFavoriteRecipes (@Field("IDUser") String IDUser,
                                               @Field("IDRecipes") String IDRecipes);

     @GET("getFavoriteRecipes_User.php")
     Call<List<recipes>> getFavorites_User(@Query("IDUser") String IDUser);

     @GET("deleteFavoriteRecipes.php")
     Call<favoriterecipes> deleteFavoriteRecipes(@Query("IDUser") String IDUser,
                                                 @Query("IDRecipes") String IDRecipes);

     @POST("updateUser.php")
     @FormUrlEncoded
     Call<users> updateUser(@Query("IDUser") String IDUser,
                           @Field("Email") String Email,
                           @Field("FullName") String FullName);
     @GET("getUserByUsername.php")
     Call<users> getUserByUsername(@Query("Username") String Username);

     @POST("changePass.php")
     @FormUrlEncoded
     Call<users> changePass(@Query("IDUser") String IDUser,
                            @Field("Password") String Password);
}
