package com.exchangerate.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryRate(
    var date: String,
    var rate: Double
) : Parcelable