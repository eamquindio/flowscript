package com.flowscript.runtime;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FlowScript runtime environment.
 * Provides singleton access to shared resources:
 * - HTTP client for REST calls
 * - Database connection pool (HikariCP)
 * - Thread pool for parallel execution
 */
public class FlowScriptRuntime {

    private static FlowScriptRuntime instance;

    private final HttpClient httpClient;
    private HikariDataSource dataSource;
    private final ExecutorService executorService;

    private FlowScriptRuntime() {
        // Initialize HTTP client with reasonable defaults
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();

        // Initialize thread pool for parallel execution
        // Uses available processors for optimal performance
        int processors = Runtime.getRuntime().availableProcessors();
        this.executorService = Executors.newFixedThreadPool(processors * 2);

        // Database connection pool will be initialized on demand
        this.dataSource = null;
    }

    /**
     * Gets the singleton instance of the runtime.
     */
    public static synchronized FlowScriptRuntime getInstance() {
        if (instance == null) {
            instance = new FlowScriptRuntime();
        }
        return instance;
    }

    // ========== HTTP Operations ==========

    /**
     * Performs an HTTP GET request.
     */
    public String httpGet(String url) {
        return httpGet(url, new HashMap<>());
    }

    /**
     * Performs an HTTP GET request with headers.
     */
    public String httpGet(String url, Map<String, String> headers) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(Duration.ofSeconds(30));

            // Add headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    builder.header(entry.getKey(), entry.getValue());
                }
            }

            HttpRequest request = builder.build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            throw new RuntimeException("HTTP GET failed: " + url, e);
        }
    }

    /**
     * Performs an HTTP POST request.
     */
    public String httpPost(String url, String body) {
        return httpPost(url, body, new HashMap<>());
    }

    /**
     * Performs an HTTP POST request with headers.
     */
    public String httpPost(String url, String body, Map<String, String> headers) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(body != null ? body : ""))
                    .timeout(Duration.ofSeconds(30));

            // Add default content type if not specified
            if (headers == null || !headers.containsKey("Content-Type")) {
                builder.header("Content-Type", "application/json");
            }

            // Add headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    builder.header(entry.getKey(), entry.getValue());
                }
            }

            HttpRequest request = builder.build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            throw new RuntimeException("HTTP POST failed: " + url, e);
        }
    }

    /**
     * Performs an HTTP PUT request.
     */
    public String httpPut(String url, String body) {
        return httpPut(url, body, new HashMap<>());
    }

    /**
     * Performs an HTTP PUT request with headers.
     */
    public String httpPut(String url, String body, Map<String, String> headers) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .PUT(HttpRequest.BodyPublishers.ofString(body != null ? body : ""))
                    .timeout(Duration.ofSeconds(30));

            // Add default content type if not specified
            if (headers == null || !headers.containsKey("Content-Type")) {
                builder.header("Content-Type", "application/json");
            }

            // Add headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    builder.header(entry.getKey(), entry.getValue());
                }
            }

            HttpRequest request = builder.build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            throw new RuntimeException("HTTP PUT failed: " + url, e);
        }
    }

    /**
     * Performs an HTTP DELETE request.
     */
    public String httpDelete(String url) {
        return httpDelete(url, new HashMap<>());
    }

    /**
     * Performs an HTTP DELETE request with headers.
     */
    public String httpDelete(String url, Map<String, String> headers) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .timeout(Duration.ofSeconds(30));

            // Add headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    builder.header(entry.getKey(), entry.getValue());
                }
            }

            HttpRequest request = builder.build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            throw new RuntimeException("HTTP DELETE failed: " + url, e);
        }
    }

    // ========== Database Operations ==========

    /**
     * Initializes the database connection pool.
     * This should be called before executing any database operations.
     *
     * @param jdbcUrl JDBC URL (e.g., "jdbc:postgresql://localhost:5432/mydb")
     * @param username Database username
     * @param password Database password
     */
    public void initializeDatabase(String jdbcUrl, String username, String password) {
        if (dataSource != null) {
            dataSource.close();
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);

        // Connection pool settings
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        this.dataSource = new HikariDataSource(config);
    }

    /**
     * Gets a database connection from the pool.
     */
    private Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("Database not initialized. Call initializeDatabase() first.");
        }
        return dataSource.getConnection();
    }

    /**
     * Executes a SQL query (SELECT).
     * Returns a list of maps, where each map represents a row.
     */
    public List<Map<String, Object>> executeQuery(String sql) {
        return executeQuery(sql, null);
    }

    /**
     * Executes a SQL query with parameters.
     */
    public List<Map<String, Object>> executeQuery(String sql, List<Object> parameters) {
        List<Map<String, Object>> results = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters
            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    stmt.setObject(i + 1, parameters.get(i));
                }
            }

            // Execute query
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Process each row
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = rs.getObject(i);
                        row.put(columnName, value);
                    }
                    results.add(row);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database query failed: " + sql, e);
        }

        return results;
    }

    /**
     * Executes a SQL update statement (INSERT, UPDATE, DELETE).
     * Returns the number of affected rows.
     */
    public int executeUpdate(String sql) {
        return executeUpdate(sql, null);
    }

    /**
     * Executes a SQL update statement with parameters.
     */
    public int executeUpdate(String sql, List<Object> parameters) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters
            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    stmt.setObject(i + 1, parameters.get(i));
                }
            }

            // Execute update
            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Database update failed: " + sql, e);
        }
    }

    // ========== Thread Pool Operations ==========

    /**
     * Gets the executor service for parallel execution.
     */
    public ExecutorService getExecutorService() {
        return executorService;
    }

    // ========== Shutdown ==========

    /**
     * Shuts down the runtime and releases all resources.
     * This should be called when the application is terminating.
     */
    public void shutdown() {
        // Shutdown executor service
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }

        // Close database connection pool
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    /**
     * Adds a shutdown hook to automatically clean up resources.
     */
    public static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (instance != null) {
                instance.shutdown();
            }
        }));
    }
}
