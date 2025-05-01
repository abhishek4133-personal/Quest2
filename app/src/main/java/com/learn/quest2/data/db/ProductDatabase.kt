package com.learn.quest2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.learn.quest2.data.dao.ProductDao
import com.learn.quest2.data.entity.ProductEntity
import com.learn.quest2.helper.ListStringConverter


@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(
    ListStringConverter::class
)

abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        const val DATABASE_NAME = "product_db"
    }
}