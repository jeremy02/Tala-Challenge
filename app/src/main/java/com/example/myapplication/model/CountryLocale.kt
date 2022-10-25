package com.example.myapplication.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CountryLocale(
    @SerializedName("currency")
    @Expose
    var currency: String? = null,

    @SerializedName("loanLimit")
    @Expose
    var loanLimit: Int? = null,

    @SerializedName("timezone")
    @Expose
    var timezone: Int? = null,

    var localeCode: String? = null
) : Parcelable