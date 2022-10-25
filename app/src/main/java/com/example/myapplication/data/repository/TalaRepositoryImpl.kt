package com.example.myapplication.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.myapplication.data.remote.*
import com.example.myapplication.data.remote.responses.Locales
import com.example.myapplication.data.remote.responses.UserLoan
import com.example.myapplication.utils.StringUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wajahatkarim3.imagine.data.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

/**
 * This is an implementation of [TalaRepository] to handle communication with [TalaApiService] server.
 * @author Jeremiah Thuku
 */
class TalaRepositoryImpl @Inject constructor(
    private val stringUtils: StringUtils,
    private val apiService: TalaApiService
) : TalaRepository {

    // Load or Get All Locales from Assets Folder
    // This can be moved into another class but we just need to demonstrate
    // Reading of a file from Assets Folder as if we were calling an API endpoint
    // Gson() provides fromJson methods which deserializing json, i.e parse json string to Locales object
    @WorkerThread
    override suspend fun loadAllCountryCodeLocales(context: Context): Flow<DataState<Locales>> {
        lateinit var localesJsonString: String
        var localesData:Locales? = null

        try {
            localesJsonString = context.assets.open("locales.json")
                .bufferedReader()
                .use { it.readText() }
            val localesListType = object : TypeToken<Locales>() {}.type
            localesData = Gson().fromJson(localesJsonString, localesListType)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return if(localesData != null) {
            flow {
                emit(DataState.success(localesData))
            }.flowOn(Dispatchers.IO)
        } else{
            flow {
                emit(DataState.error<Locales>(stringUtils.somethingWentWrong()))
            }.flowOn(Dispatchers.IO)
        }
    }

    // Load or Get All Loans
    @WorkerThread
    override suspend fun loadAllLoans(): Flow<DataState<List<UserLoan>>> {
        return flow {
            apiService.loadAllLoans().apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(DataState.success(it))
                    }
                }
                // handle the case when the API request gets an error response.
                // e.g. internal server error.
            }.onErrorSuspend {
                emit(DataState.error<List<UserLoan>>(message()))

                // handle the case when the API request gets an exception response.
                // e.g. network connection error.
            }.onExceptionSuspend {
                if (this.exception is IOException) {
                    emit(DataState.error<List<UserLoan>>(stringUtils.noNetworkErrorMessage()))
                } else {
                    emit(DataState.error<List<UserLoan>>(stringUtils.somethingWentWrong()))
                }
            }
        }
    }
}
