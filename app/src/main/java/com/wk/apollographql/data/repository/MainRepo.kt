package com.wk.apollographql.data.repository

import com.wk.apollographql.data.models.DetailedCountry
import com.wk.apollographql.data.models.SimpleCountry

interface MainRepo {

    suspend fun getCountries() : List<SimpleCountry>
    suspend fun getCountry(code : String) : DetailedCountry?
}