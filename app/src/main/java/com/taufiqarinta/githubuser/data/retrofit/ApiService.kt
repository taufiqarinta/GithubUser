package com.taufiqarinta.githubuser.data.retrofit

import com.taufiqarinta.githubuser.data.response.DetailUserResponse
import com.taufiqarinta.githubuser.data.response.User
import com.taufiqarinta.githubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUSer (
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username : String
    ): Call<DetailUserResponse>


    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers (
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing (
        @Path("username") username: String
    ): Call<ArrayList<User>>
}