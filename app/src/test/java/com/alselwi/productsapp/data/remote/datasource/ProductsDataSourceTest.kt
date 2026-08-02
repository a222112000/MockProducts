package com.alselwi.productsapp.data.remote.datasource

import com.alselwi.productsapp.core.NetworkResult
import com.alselwi.productsapp.data.remote.api.ProductApi
import com.alselwi.productsapp.data.remote.model.ProductResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertSame
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ProductsDataSourceTest {

    private lateinit var api: ProductApi
    private lateinit var dataSource: ProductsDataSource

    @Before
    fun setUp() {
        api = mockk()
        dataSource = ProductsDataSource(api)
    }

    @Test
    fun `getProducts returns successful`() = runTest {
        val productRes = ProductResponse(
            hits = emptyList()
        )

        coEvery { api.getProducts() } returns Response.success(productRes)
        val result  = dataSource.getProducts()
        assertTrue(result is NetworkResult.Successful)
        val success = result as NetworkResult.Successful
        assertSame(productRes, success.data)
    }

}