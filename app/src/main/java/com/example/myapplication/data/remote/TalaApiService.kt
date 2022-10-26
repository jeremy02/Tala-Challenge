package com.example.myapplication.data.remote

import com.example.myapplication.data.remote.responses.UserLoan
import retrofit2.http.GET

interface TalaApiService {

    // Get all the loans for all users
    @GET("testData.json?raw=true")
    suspend fun loadAllLoans(): ApiResponse<List<UserLoan>>

    companion object {
        const val BASE_API_URL = "https://github.com/jeremy02/Tala-Challenge/blob/main/app/src/main/assets/"
    }
}
