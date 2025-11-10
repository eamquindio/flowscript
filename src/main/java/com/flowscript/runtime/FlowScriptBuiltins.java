package com.flowscript.runtime;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

/**
 * FlowScript built-in functions.
 * Provides standard library functionality for FlowScript programs.
 */
public class FlowScriptBuiltins {

    private static final Gson gson = new Gson();

    // ========== I/O Functions ==========

    /**
     * Prints a message to the console.
     * FlowScript: imprimir(mensaje)
     */
    public static void imprimir(Object mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Reads a line from the console.
     * FlowScript: leer() -> texto
     */
    public static String leer() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException("Error reading from console", e);
        }
    }

    // ========== HTTP Functions ==========

    /**
     * Performs an HTTP GET request.
     * FlowScript: httpGet(url) -> texto
     */
    public static String httpGet(String url) {
        return FlowScriptRuntime.getInstance().httpGet(url);
    }

    /**
     * Performs an HTTP GET request with headers.
     * FlowScript: httpGet(url, headers) -> texto
     */
    public static String httpGet(String url, Map<String, String> headers) {
        return FlowScriptRuntime.getInstance().httpGet(url, headers);
    }

    /**
     * Performs an HTTP POST request.
     * FlowScript: httpPost(url, body) -> texto
     */
    public static String httpPost(String url, String body) {
        return FlowScriptRuntime.getInstance().httpPost(url, body);
    }

    /**
     * Performs an HTTP POST request with headers.
     * FlowScript: httpPost(url, body, headers) -> texto
     */
    public static String httpPost(String url, String body, Map<String, String> headers) {
        return FlowScriptRuntime.getInstance().httpPost(url, body, headers);
    }

    /**
     * Performs an HTTP PUT request.
     * FlowScript: httpPut(url, body) -> texto
     */
    public static String httpPut(String url, String body) {
        return FlowScriptRuntime.getInstance().httpPut(url, body);
    }

    /**
     * Performs an HTTP DELETE request.
     * FlowScript: httpDelete(url) -> texto
     */
    public static String httpDelete(String url) {
        return FlowScriptRuntime.getInstance().httpDelete(url);
    }

    // ========== Database Functions ==========

    /**
     * Executes a SQL query (SELECT).
     * FlowScript: consultar(sql) -> lista<objeto>
     */
    public static List<Map<String, Object>> consultar(String sql) {
        return FlowScriptRuntime.getInstance().executeQuery(sql);
    }

    /**
     * Executes a SQL query with parameters.
     * FlowScript: consultar(sql, parametros) -> lista<objeto>
     */
    public static List<Map<String, Object>> consultar(String sql, List<Object> parametros) {
        return FlowScriptRuntime.getInstance().executeQuery(sql, parametros);
    }

    /**
     * Executes a SQL statement (INSERT, UPDATE, DELETE).
     * FlowScript: ejecutar(sql) -> entero
     */
    public static int ejecutar(String sql) {
        return FlowScriptRuntime.getInstance().executeUpdate(sql);
    }

    /**
     * Executes a SQL statement with parameters.
     * FlowScript: ejecutar(sql, parametros) -> entero
     */
    public static int ejecutar(String sql, List<Object> parametros) {
        return FlowScriptRuntime.getInstance().executeUpdate(sql, parametros);
    }

    // ========== JSON Functions ==========

    /**
     * Parses a JSON string into an object.
     * FlowScript: parseJson(json) -> objeto
     */
    public static Map<String, Object> parseJson(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return gson.fromJson(element, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON: " + json, e);
        }
    }

    /**
     * Converts an object to JSON string.
     * FlowScript: toJson(objeto) -> texto
     */
    public static String toJson(Object objeto) {
        try {
            return gson.toJson(objeto);
        } catch (Exception e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
    }

    // ========== String Functions ==========

    /**
     * Returns the length of a string.
     * FlowScript: longitud(texto) -> entero
     */
    public static int longitud(String texto) {
        return texto != null ? texto.length() : 0;
    }

    /**
     * Returns a substring.
     * FlowScript: subcadena(texto, inicio, fin) -> texto
     */
    public static String subcadena(String texto, int inicio, int fin) {
        if (texto == null) return "";
        return texto.substring(inicio, Math.min(fin, texto.length()));
    }

    /**
     * Checks if a string contains another string.
     * FlowScript: contiene(texto, buscar) -> booleano
     */
    public static boolean contiene(String texto, String buscar) {
        return texto != null && texto.contains(buscar);
    }

    /**
     * Converts a string to uppercase.
     * FlowScript: mayusculas(texto) -> texto
     */
    public static String mayusculas(String texto) {
        return texto != null ? texto.toUpperCase() : "";
    }

    /**
     * Converts a string to lowercase.
     * FlowScript: minusculas(texto) -> texto
     */
    public static String minusculas(String texto) {
        return texto != null ? texto.toLowerCase() : "";
    }

    /**
     * Splits a string by a delimiter.
     * FlowScript: dividir(texto, delimitador) -> lista<texto>
     */
    public static List<String> dividir(String texto, String delimitador) {
        if (texto == null) return new ArrayList<>();
        return Arrays.asList(texto.split(delimitador));
    }

    /**
     * Joins a list of strings with a delimiter.
     * FlowScript: unir(lista, delimitador) -> texto
     */
    public static String unir(List<String> lista, String delimitador) {
        if (lista == null) return "";
        return String.join(delimitador, lista);
    }

    /**
     * Trims whitespace from a string.
     * FlowScript: recortar(texto) -> texto
     */
    public static String recortar(String texto) {
        return texto != null ? texto.trim() : "";
    }

    /**
     * Replaces all occurrences of a substring.
     * FlowScript: reemplazar(texto, buscar, reemplazo) -> texto
     */
    public static String reemplazar(String texto, String buscar, String reemplazo) {
        return texto != null ? texto.replace(buscar, reemplazo) : "";
    }

    // ========== List Functions ==========

    /**
     * Adds an element to a list.
     * FlowScript: agregar(lista, elemento) -> nulo
     */
    public static void agregar(List<Object> lista, Object elemento) {
        if (lista != null) {
            lista.add(elemento);
        }
    }

    /**
     * Removes an element from a list.
     * FlowScript: remover(lista, elemento) -> booleano
     */
    public static boolean remover(List<Object> lista, Object elemento) {
        return lista != null && lista.remove(elemento);
    }

    /**
     * Returns the size of a list.
     * FlowScript: tamano(lista) -> entero
     */
    public static int tamano(List<?> lista) {
        return lista != null ? lista.size() : 0;
    }

    /**
     * Checks if a list contains an element.
     * FlowScript: contieneLista(lista, elemento) -> booleano
     */
    public static boolean contieneLista(List<?> lista, Object elemento) {
        return lista != null && lista.contains(elemento);
    }

    /**
     * Gets an element at a specific index.
     * FlowScript: obtener(lista, indice) -> objeto
     */
    public static Object obtener(List<?> lista, int indice) {
        if (lista == null || indice < 0 || indice >= lista.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + indice);
        }
        return lista.get(indice);
    }

    /**
     * Sets an element at a specific index.
     * FlowScript: establecer(lista, indice, elemento) -> nulo
     */
    public static void establecer(List<Object> lista, int indice, Object elemento) {
        if (lista == null || indice < 0 || indice >= lista.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + indice);
        }
        lista.set(indice, elemento);
    }

    /**
     * Clears all elements from a list.
     * FlowScript: limpiar(lista) -> nulo
     */
    public static void limpiar(List<?> lista) {
        if (lista != null) {
            lista.clear();
        }
    }

    // ========== Map/Object Functions ==========

    /**
     * Gets a value from a map.
     * FlowScript: obtenerClave(mapa, clave) -> objeto
     */
    public static Object obtenerClave(Map<String, Object> mapa, String clave) {
        return mapa != null ? mapa.get(clave) : null;
    }

    /**
     * Sets a value in a map.
     * FlowScript: establecerClave(mapa, clave, valor) -> nulo
     */
    public static void establecerClave(Map<String, Object> mapa, String clave, Object valor) {
        if (mapa != null) {
            mapa.put(clave, valor);
        }
    }

    /**
     * Checks if a map contains a key.
     * FlowScript: tieneClave(mapa, clave) -> booleano
     */
    public static boolean tieneClave(Map<String, ?> mapa, String clave) {
        return mapa != null && mapa.containsKey(clave);
    }

    /**
     * Returns all keys from a map.
     * FlowScript: claves(mapa) -> lista<texto>
     */
    public static List<String> claves(Map<String, ?> mapa) {
        return mapa != null ? new ArrayList<>(mapa.keySet()) : new ArrayList<>();
    }

    /**
     * Returns all values from a map.
     * FlowScript: valores(mapa) -> lista<objeto>
     */
    public static List<Object> valores(Map<String, ?> mapa) {
        return mapa != null ? new ArrayList<>(mapa.values()) : new ArrayList<>();
    }

    // ========== Math Functions ==========

    /**
     * Returns the absolute value.
     * FlowScript: abs(numero) -> numero
     */
    public static double abs(double numero) {
        return Math.abs(numero);
    }

    /**
     * Returns the ceiling value.
     * FlowScript: techo(numero) -> entero
     */
    public static int techo(double numero) {
        return (int) Math.ceil(numero);
    }

    /**
     * Returns the floor value.
     * FlowScript: piso(numero) -> entero
     */
    public static int piso(double numero) {
        return (int) Math.floor(numero);
    }

    /**
     * Rounds a number.
     * FlowScript: redondear(numero) -> entero
     */
    public static int redondear(double numero) {
        return (int) Math.round(numero);
    }

    /**
     * Returns the maximum of two numbers.
     * FlowScript: maximo(a, b) -> numero
     */
    public static double maximo(double a, double b) {
        return Math.max(a, b);
    }

    /**
     * Returns the minimum of two numbers.
     * FlowScript: minimo(a, b) -> numero
     */
    public static double minimo(double a, double b) {
        return Math.min(a, b);
    }

    /**
     * Returns a random number between 0 and 1.
     * FlowScript: aleatorio() -> decimal
     */
    public static double aleatorio() {
        return Math.random();
    }

    /**
     * Returns the power of a number.
     * FlowScript: potencia(base, exponente) -> decimal
     */
    public static double potencia(double base, double exponente) {
        return Math.pow(base, exponente);
    }

    /**
     * Returns the square root.
     * FlowScript: raiz(numero) -> decimal
     */
    public static double raiz(double numero) {
        return Math.sqrt(numero);
    }

    // ========== Type Conversion Functions ==========

    /**
     * Converts a value to integer.
     * FlowScript: aEntero(valor) -> entero
     */
    public static int aEntero(Object valor) {
        if (valor instanceof Number) {
            return ((Number) valor).intValue();
        }
        if (valor instanceof String) {
            return Integer.parseInt((String) valor);
        }
        throw new IllegalArgumentException("Cannot convert to integer: " + valor);
    }

    /**
     * Converts a value to decimal.
     * FlowScript: aDecimal(valor) -> decimal
     */
    public static double aDecimal(Object valor) {
        if (valor instanceof Number) {
            return ((Number) valor).doubleValue();
        }
        if (valor instanceof String) {
            return Double.parseDouble((String) valor);
        }
        throw new IllegalArgumentException("Cannot convert to decimal: " + valor);
    }

    /**
     * Converts a value to string.
     * FlowScript: aTexto(valor) -> texto
     */
    public static String aTexto(Object valor) {
        return valor != null ? valor.toString() : "";
    }

    /**
     * Converts a value to boolean.
     * FlowScript: aBooleano(valor) -> booleano
     */
    public static boolean aBooleano(Object valor) {
        if (valor instanceof Boolean) {
            return (Boolean) valor;
        }
        if (valor instanceof String) {
            return Boolean.parseBoolean((String) valor);
        }
        return valor != null;
    }

    // ========== Utility Functions ==========

    /**
     * Sleeps for a specified number of milliseconds.
     * FlowScript: esperar(milisegundos) -> nulo
     */
    public static void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sleep interrupted", e);
        }
    }

    /**
     * Returns the current timestamp in milliseconds.
     * FlowScript: tiempoActual() -> entero
     */
    public static long tiempoActual() {
        return System.currentTimeMillis();
    }

    /**
     * Creates a new empty list.
     * FlowScript: nuevaLista() -> lista<objeto>
     */
    public static List<Object> nuevaLista() {
        return new ArrayList<>();
    }

    /**
     * Creates a new empty map.
     * FlowScript: nuevoObjeto() -> objeto
     */
    public static Map<String, Object> nuevoObjeto() {
        return new HashMap<>();
    }
}
