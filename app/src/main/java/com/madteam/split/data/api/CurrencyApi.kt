package com.madteam.split.data.api

import com.madteam.split.data.config.EndpointsConstants.GET_CURRENCIES
import com.madteam.split.data.model.response.CurrencyDTO
import retrofit2.http.GET

interface CurrencyApi {

    @GET(GET_CURRENCIES)
    suspend fun getCurrencies(): List<CurrencyDTO>
}