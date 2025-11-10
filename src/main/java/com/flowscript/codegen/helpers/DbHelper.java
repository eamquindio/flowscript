package com.flowscript.codegen.helpers;

import java.sql.*;
import java.util.*;

/**
 * Helper class for database operations in generated FlowScript code.
 * Provides methods for executing SQL commands and queries.
 *
 * This class will be copied into generated code to provide db.ejecutar() and db.consultar() functionality.
 */
public class DbHelper {

    // Default connection URL (can be overridden)
    private static String connectionUrl = "jdbc:h2:mem:flowscript;DB_CLOSE_DELAY=-1";
    private static String username = "sa";
    private static String password = "";

    /**
     * Configure the database connection.
     * Call this before using db.ejecutar() or db.consultar().
     */
    public static void configure(String url, String user, String pass) {
        connectionUrl = url;
        username = user;
        password = pass;
    }

    /**
     * Executes a SQL command (INSERT, UPDATE, DELETE).
     * Corresponds to db.ejecutar(query, params) in FlowScript.
     *
     * @param query SQL query with placeholders (?)
     * @param params List of parameters to bind
     * @return Number of affected rows
     */
    public static int execute(String query, List<Object> params) {
        try (Connection conn = DriverManager.getConnection(connectionUrl, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Bind parameters
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            // Execute update
            int affectedRows = stmt.executeUpdate();

            System.out.println("[DB] Executed: " + query + " | Affected rows: " + affectedRows);
            return affectedRows;

        } catch (SQLException e) {
            System.err.println("[DB ERROR] Failed to execute: " + query);
            System.err.println("[DB ERROR] " + e.getMessage());
            throw new RuntimeException("Database execution error: " + e.getMessage(), e);
        }
    }

    /**
     * Executes a SQL query (SELECT).
     * Corresponds to db.consultar(query, params) in FlowScript.
     *
     * @param query SQL query with placeholders (?)
     * @param params List of parameters to bind
     * @return List of maps, where each map represents a row with column names as keys
     */
    public static List<Map<String, Object>> query(String query, List<Object> params) {
        List<Map<String, Object>> results = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionUrl, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Bind parameters
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            // Execute query
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Process each row
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();

                    // Add each column to the row map
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i);
                        Object value = rs.getObject(i);
                        row.put(columnName, value);
                    }

                    results.add(row);
                }
            }

            System.out.println("[DB] Queried: " + query + " | Results: " + results.size() + " rows");
            return results;

        } catch (SQLException e) {
            System.err.println("[DB ERROR] Failed to query: " + query);
            System.err.println("[DB ERROR] " + e.getMessage());
            throw new RuntimeException("Database query error: " + e.getMessage(), e);
        }
    }

    /**
     * Initialize an in-memory H2 database with sample tables.
     * Useful for testing and demos.
     */
    public static void initializeSampleDatabase() {
        try (Connection conn = DriverManager.getConnection(connectionUrl, username, password);
             Statement stmt = conn.createStatement()) {

            // Create sample tables
            stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "nombre VARCHAR(100), " +
                        "email VARCHAR(100), " +
                        "edad INT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS productos (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "nombre VARCHAR(100), " +
                        "precio DECIMAL(10,2), " +
                        "stock INT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS ordenes (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "cliente_email VARCHAR(100), " +
                        "producto_id INT, " +
                        "cantidad INT, " +
                        "total DECIMAL(10,2), " +
                        "estado VARCHAR(50))");

            System.out.println("[DB] Sample database initialized");

        } catch (SQLException e) {
            System.err.println("[DB ERROR] Failed to initialize database: " + e.getMessage());
        }
    }
}
