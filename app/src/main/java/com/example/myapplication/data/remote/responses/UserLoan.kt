package com.example.myapplication.data.remote.responses
import android.os.Parcelable
import com.example.myapplication.model.CountryLocale
import com.example.myapplication.model.Loan
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserLoan(
    @SerializedName("locale")
    @Expose var locale: String? = null,

    @SerializedName("loan")
    @Expose var loan: Loan? = null,

    @SerializedName("timestamp")
    @Expose
    var timestamp: Long? = null,

    @SerializedName("username")
    @Expose
    var username: String? = null,


    var userLoanLocale: CountryLocale? = null,
) : Parcelable