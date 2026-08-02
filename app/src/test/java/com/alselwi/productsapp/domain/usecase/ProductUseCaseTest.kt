package com.alselwi.productsapp.domain.usecase

import app.cash.turbine.test
import com.alselwi.productsapp.core.AppError
import com.alselwi.productsapp.core.NetworkResult
import com.alselwi.productsapp.domain.entity.DomainProductResponse
import com.alselwi.productsapp.domain.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertSame
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ProductUseCaseTest {

    private lateinit var repository: ProductRepository
    private lateinit var useCase: ProductUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = ProductUseCase(repository)
    }

    @Test
    fun `invoke emits loading`()= runTest {
        every { repository.getProducts() } returns flowOf(NetworkResult.Loading)
        useCase().test {
            val res = awaitItem()
            assertTrue(res is NetworkResult.Loading)
            awaitComplete()
        }
        verify(exactly = 1) { repository.getProducts() }
    }

    @Test
    fun `invoke emits successful result with same domain response`() = runTest {
        val domainResponse = DomainProductResponse(
            hits = emptyList()
        )

        every {
            repository.getProducts()
        } returns flowOf(
            NetworkResult.Successful(domainResponse)
        )

        useCase().test {
            val result = awaitItem()

            assertTrue(result is NetworkResult.Successful)

            val success = result as NetworkResult.Successful

            assertSame(
                domainResponse,
                success.data
            )

            awaitComplete()
        }

        verify(exactly = 1) {
            repository.getProducts()
        }
    }

    @Test
    fun `invoke emits error when repository emits error`() = runTest {
        val appError = AppError(
            code = 500,
            message = "Server error"
        )

        every {
            repository.getProducts()
        } returns flowOf(
            NetworkResult.Error(appError)
        )

        useCase().test {
            val result = awaitItem()

            assertTrue(result is NetworkResult.Error)

            val errorResult = result as NetworkResult.Error

            assertEquals(
                500,
                errorResult.error.code
            )

            assertEquals(
                "Server error",
                errorResult.error.message
            )

            awaitComplete()
        }

        verify(exactly = 1) {
            repository.getProducts()
        }
    }
}