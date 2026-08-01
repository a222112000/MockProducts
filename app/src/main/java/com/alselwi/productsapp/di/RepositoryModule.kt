package com.alselwi.productsapp.di

import com.alselwi.productsapp.data.repository.ProductRepositoryImpl
import com.alselwi.productsapp.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providesProductRepository(impl: ProductRepositoryImpl): ProductRepository
}