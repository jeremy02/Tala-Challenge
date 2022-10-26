package com.example.myapplication.data.remote.responses

import android.os.Parcelable
import com.example.myapplication.model.CountryLocale
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Locales(
    @SerializedName("ke")
    @Expose
    var ke: CountryLocale? = null,

    @SerializedName("mx")
    @Expose
    var mx: CountryLocale? = null,

    @SerializedName("ph")
    @Expose
    var ph: CountryLocale? = null
) : Parcelable