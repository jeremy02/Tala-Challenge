package com.example.myapplication.data.repository

import android.content.Context
import com.example.myapplication.data.remote.responses.Locales
import com.example.myapplication.data.remote.responses.UserLoan
import com.wajahatkarim3.imagine.data.DataState
import kotlinx.coroutines.flow.Flow

/**
 * This is an interface data layer to handle communication with any data source such as Server or local database.
 * @see [TalaRepositoryImpl] for implementation of this class to utilize Github URL.
 * @author Jeremiah Thuku
 */
interface TalaRepository {
    suspend fun loadAllLoans(): Flow<DataState<List<UserLoan>>>
    suspend fun loadAllCountryCodeLocales(context: Context): Flow<DataState<Locales>>
}
