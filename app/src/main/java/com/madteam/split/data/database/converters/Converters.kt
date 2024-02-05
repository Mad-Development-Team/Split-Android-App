package com.madteam.split.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.madteam.split.domain.model.Currency
import com.madteam.split.domain.model.Member

class Converters {
    @TypeConverter
    fun fromMemberList(value: List<Member>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Member>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toMemberList(value: String): List<Member> {
        val gson = Gson()
        val type = object : TypeToken<List<Member>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCurrency(value: Currency): String {
        val gson = Gson()
        val type = object : TypeToken<Currency>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCurrency(value: String): Currency {
        val gson = Gson()
        val type = object : TypeToken<Currency>() {}.type
        return gson.fromJson(value, type)
    }
}