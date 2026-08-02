package com.alselwi.productsapp.data.repository

import app.cash.turbine.test
import com.alselwi.productsapp.core.NetworkResult
import com.alselwi.productsapp.data.mapper.ProductMapper
import com.alselwi.productsapp.data.remote.datasource.ProductsDataSource
import com.alselwi.productsapp.data.remote.model.ProductResponse
import com.alselwi.productsapp.domain.entity.DomainProductResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertSame
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {

    private lateinit var productMapper: ProductMapper
    private lateinit var productsDataSource: ProductsDataSource
    private lateinit var repositoryImpl: ProductRepositoryImpl

    @Before
    fun setUp() {
        productMapper = mockk()
        productsDataSource = mockk()
        repositoryImpl = ProductRepositoryImpl(
            productMapper = productMapper,
            productsDataSource = productsDataSource
        )
    }

    @Test
    fun `getProducts emits loading then successful`() = runTest {
        val remoteResponse = ProductResponse(
            hits = emptyList()
        )
        val domainResponse = DomainProductResponse(
            hits = emptyList()
        )
        coEvery { productsDataSource.getProducts() } returns NetworkResult.Successful(remoteResponse)
        every { productMapper.toDomain(remoteResponse) } returns domainResponse
        repositoryImpl.getProducts().test {
            val loading = awaitItem()
            assertTrue(loading is NetworkResult.Loading)
            val success = awaitItem()
            assertTrue(success is NetworkResult.Successful)
            assertSame(domainResponse, (success as NetworkResult.Successful).data)
            awaitComplete()
        }
        verify(exactly = 1) { productMapper.toDomain(remoteResponse) }
    }
}