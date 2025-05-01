package com.learn.quest2.di

import com.learn.quest2.data.api.ProductAPI
import com.learn.quest2.data.dao.ProductDao
import com.learn.quest2.domain.repository.ProductRepository
import com.learn.quest2.domain.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}