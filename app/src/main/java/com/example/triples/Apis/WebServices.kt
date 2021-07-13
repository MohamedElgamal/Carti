package com.example.triples.Apis

import com.example.triples.DataContainers.*
import retrofit2.Call;
import retrofit2.http.*

interface WebServices {
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("auth/register")
    fun registration( @Field("name") name : String
                     ,@Field("email") email : String
                     ,@Field("password") password : String
                     ,@Field("device_name") deviceName : String ) :Call<TokenResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("auth/login")
    fun login(  @Field("email") email : String
                ,@Field("password") password : String
                ,@Field("device_name") deviceName : String) : Call<TokenResponse>
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("user")
    fun userInfo(@Header("Authorization") token : String
                 , @Field("") n: String): Call<UserResponse>
    @FormUrlEncoded
    @POST("categories/all")
    fun categories(@Field("") n: String):Call<CategoriesResponse>
    @FormUrlEncoded
    @POST("category/products")
    fun products(@Field("category_id") categoryId : String) : Call<ProductsResponse>
    @FormUrlEncoded
    @POST("product/barcode")
    fun searchByBarcode(@Field("barcode") barcode : String) :Call<ProductBarcodeResponse>
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("cart/product/add/barcode")
    fun addItemToCart(@Header("Authorization") token : String
                      , @Field("barcode") barcode : String,@Field("qty") qty :Int) :Call<CartResponse>
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("cart/product/remove/barcode")
    fun removeItemFromCart(@Header("Authorization") token : String
                      , @Field("barcode") barcode : String,@Field("qty") qty :Int) :Call<CartResponse>
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("cart/summary")
    fun cartSummary(@Header("Authorization") token : String , @Field("") n:String):Call<CartResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("cart/checkout/cashier")
    fun cashCheckout(@Header("Authorization") token : String
                     , @Field("cashier_id") qrCode : String) :Call<CashResponse>
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("cart/checkout/stripe")
    fun creditCardCheckout(@Header("Authorization") token : String
                           , @Field("") n:String) : Call<CreditCardResponse>
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("user/purchases")
    fun invoices(@Header("Authorization") token : String
                 , @Field("") n : String) : Call<InvoicesResponse>

}