package com.madteam.split.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.madteam.split.domain.model.Currency
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Member
import com.madteam.split.domain.model.MemberExpense
import com.madteam.split.domain.model.PaidByExpense

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

    @TypeConverter
    fun fromPaidByExpenseList(value: List<PaidByExpense>): String {
        val gson = Gson()
        val type = object : TypeToken<List<PaidByExpense>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toPaidByExpenseList(value: String): List<PaidByExpense> {
        val gson = Gson()
        val type = object : TypeToken<List<PaidByExpense>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromMemberExpenseList(value: List<MemberExpense>): String {
        val gson = Gson()
        val type = object : TypeToken<List<MemberExpense>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toMemberExpenseList(value: String): List<MemberExpense> {
        val gson = Gson()
        val type = object : TypeToken<List<MemberExpense>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromExpenseType(value: ExpenseType): String {
        val gson = Gson()
        val type = object : TypeToken<ExpenseType>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toExpenseType(value: String): ExpenseType {
        val gson = Gson()
        val type = object : TypeToken<ExpenseType>() {}.type
        return gson.fromJson(value, type)
    }
}