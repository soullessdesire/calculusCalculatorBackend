package com.example.calculuscalculator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CalculuscalculatorApplicationTests {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `test evaluate endpoint with valid expression`() {
        val response = restTemplate.getForEntity("/evaluate?expression=2+2", String::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("4", response.body)
    }

    @Test
    fun `test evaluate endpoint with invalid expression`() {
        val response = restTemplate.getForEntity("/evaluate?expression=2++2", String::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)

        // Adjust based on your actual error handling response
        val body = response.body ?: ""
        if (body == "4") {
            throw AssertionError("Expected an error, but got a valid result: $body")
        }

        assertEquals("Invalid expression", body)
    }

    @Test
    fun `test differentiate endpoint with valid input`() {
        val response = restTemplate.getForEntity("/differentiate?expression=x^2&variable=x", String::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("2*x", response.body)
    }

    @Test
    fun `test differentiate endpoint with missing variable`() {
        val response = restTemplate.getForEntity("/differentiate?expression=x^2&variable=", String::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Variable cannot be empty", response.body)
    }

    @Test
    fun `test integrate endpoint with valid input`() {
        val response = restTemplate.getForEntity("/integrate?expression=x^2&variable=x", String::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)

        val expected = normalizeExpression("1/3*x^3")
        val actual = normalizeExpression(response.body ?: "")
        assertEquals(expected, actual)
    }

    @Test
    fun `test integrate endpoint with missing expression`() {
        val response = restTemplate.getForEntity("/integrate?expression=&variable=x", String::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Expression cannot be empty", response.body)
    }

    private fun normalizeExpression(expr: String): String {
        // Very basic normalization for test comparison purposes
        return expr.replace(" ", "")
            .replace("*", "")
            .replace("1/3x^3", "x^3/3") // add more cases if needed
    }
}
