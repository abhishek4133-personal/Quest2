package com.learn.quest2.di

import android.app.Application
import androidx.room.Room
import com.learn.quest2.data.api.ProductAPI
import com.learn.quest2.data.dao.ProductDao
import com.learn.quest2.data.db.ProductDatabase
import com.learn.quest2.helper.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProductAPI() : ProductAPI{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .client(client)
            .build()
            .create(ProductAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideProductDao(
        app: Application
    ): ProductDao {
        return Room.databaseBuilder(
            app,
            ProductDatabase::class.java,
            ProductDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
            .productDao()
    }
}