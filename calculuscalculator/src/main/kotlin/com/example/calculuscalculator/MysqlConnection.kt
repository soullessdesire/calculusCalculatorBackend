package com.example.calculuscalculator

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object MysqlConnection {

    private const val URL = "jdbc:mysql://localhost:3306/calculus_db"
    private const val USER = "root"
    private const val PASSWORD = "password"

    init {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
        } catch (e: ClassNotFoundException) {
            println("MySQL JDBC Driver not found.")
            e.printStackTrace()
        }
    }

    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (e: SQLException) {
            println("Connection to MySQL failed.")
            e.printStackTrace()
            null
        }
    }
}