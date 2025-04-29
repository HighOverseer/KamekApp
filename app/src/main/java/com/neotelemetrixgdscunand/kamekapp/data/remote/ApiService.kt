package com.neotelemetrixgdscunand.kakaoxpert.data.remote

import com.neotelemetrixgdscunand.kakaoxpert.data.remote.dto.LoginDto
import com.neotelemetrixgdscunand.kakaoxpert.data.remote.dto.RegisterDto
import com.neotelemetrixgdscunand.kakaoxpert.data.remote.dto.Response
import com.neotelemetrixgdscunand.kamekapp.data.remote.dto.WeatherForecastItemDto
import com.neotelemetrixgdscunand.kamekapp.data.remote.dto.WeatherForecastOverviewDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("no_hp") handphoneNumberOrEmail:String,
        @Field("password") password:String,
        @Field("confirm_password") passwordConfirmation:String,
        @Field("name") name:String
    ): Response<RegisterDto>

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("no_hp") handphoneNumberOrEmail:String,
        @Field("password") password:String
    ):Response<LoginDto>

    @GET("weather-home")
    suspend fun getWeatherForecastOverview(
        @Query("lat") latitude:Float,
        @Query("lon") longitude:Float
    ):Response<WeatherForecastOverviewDto>

    @GET("weather")
    suspend fun getWeatherForecastForSevenDays(
        @Query("lat") latitude:Float,
        @Query("lon") longitude:Float
    ):Response<List<WeatherForecastItemDto>>

}