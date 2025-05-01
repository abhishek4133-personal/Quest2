package com.learn.quest2.helper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.learn.quest2.data.model.Dimensions
import com.learn.quest2.data.model.Meta
import com.learn.quest2.data.model.ReviewsItem

class ListStringConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<String>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(json: String?): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromReviewsItemList(value: List<ReviewsItem>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toReviewsItemList(value: String?): List<ReviewsItem>? {
        val listType = object : TypeToken<List<ReviewsItem>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromMetaList(value: Meta?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMetaList(value: String?): Meta? {
        val listType = object : TypeToken<Meta>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromDimensionList(value: Dimensions?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toDimensionList(value: String?): Dimensions? {
        val listType = object : TypeToken<Dimensions>() {}.type
        return gson.fromJson(value, listType)
    }
}