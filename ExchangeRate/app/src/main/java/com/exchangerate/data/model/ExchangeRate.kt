package com.exchangerate.data.model

import io.realm.RealmList
import io.realm.RealmObject

open class ExchangeRate(
    var rates: RealmList<Rate>? = null,
    var base: String = "",
    var date: String = "",
    var time: Time? = null
) : RealmObject()

open class Rate(
    var currency: String = "",
    var rate: Double = 0.0
) : RealmObject()

open class Time(
    var hour: Int = 0,
    var minute: Int = 0
) : RealmObject()