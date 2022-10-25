package com.example.myapplication.model

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Loan(
    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("level")
    @Expose
    var level: String? = null,

    @SerializedName("due")
    @Expose
    var due: Int? = null,

    @SerializedName("dueDate")
    @Expose
    var dueDate: Long? = null,

    @SerializedName("approved")
    @Expose
    var approved: Int? = null
) : Parcelable