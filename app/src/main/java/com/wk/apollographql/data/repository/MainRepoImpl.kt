package com.wk.apollographql.data.repository


import com.apollographql.apollo3.ApolloClient
import com.wk.apollographql.CountriesQuery
import com.wk.apollographql.CountryQuery
import com.wk.apollographql.data.models.DetailedCountry
import com.wk.apollographql.data.models.SimpleCountry
import com.wk.apollographql.data.models.toDetailedCountry
import com.wk.apollographql.data.models.toSimpleCountry
import javax.inject.Inject

class MainRepoImpl @Inject constructor(private val apolloClient: ApolloClient) : MainRepo {
    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient.query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toSimpleCountry() }
            ?: emptyList()
    }


    override suspend fun getCountry(code: String): DetailedCountry? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }

}