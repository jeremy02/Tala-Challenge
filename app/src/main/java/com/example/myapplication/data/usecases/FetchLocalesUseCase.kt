package com.example.myapplication.data.usecases

import android.content.Context
import com.example.myapplication.data.repository.TalaRepository
import javax.inject.Inject

/**
 * A use-case to load the locales from locales.json file in assets.
 * @author Jeremiah Thuku
 */
class FetchLocalesUseCase @Inject constructor(
    private val repository: TalaRepository
    ) {
    suspend operator fun invoke(context: Context) = repository.loadAllCountryCodeLocales(context)
}
