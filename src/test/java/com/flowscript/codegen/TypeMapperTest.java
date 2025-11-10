package com.flowscript.codegen;

import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeMapperTest {

    private TypeMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TypeMapper();
    }

    // ========== Primitive Types ==========

    @Test
    void testMapType_Entero() {
        TypeNode typeNode = new TypeNode("entero");
        assertEquals("int", mapper.mapType(typeNode));
    }

    @Test
    void testMapType_Decimal() {
        TypeNode typeNode = new TypeNode("decimal");
        assertEquals("double", mapper.mapType(typeNode));
    }

    @Test
    void testMapType_Booleano() {
        TypeNode typeNode = new TypeNode("booleano");
        assertEquals("boolean", mapper.mapType(typeNode));
    }

    @Test
    void testMapType_Texto() {
        TypeNode typeNode = new TypeNode("texto");
        assertEquals("String", mapper.mapType(typeNode));
    }

    @Test
    void testMapType_Nulo() {
        TypeNode typeNode = new TypeNode("nulo");
        assertEquals("void", mapper.mapType(typeNode));
    }

    @Test
    void testMapType_Null() {
        assertEquals("void", mapper.mapType(null));
    }

    // ========== Generic Types ==========

    @Test
    void testMapType_Lista_SinTipo() {
        TypeNode typeNode = new TypeNode("lista");
        assertEquals("List<Object>", mapper.mapType(typeNode));
    }

    @Test
    void testMapType_Lista_Entero() {
        TypeNode elementType = new TypeNode("entero");
        TypeNode typeNode = new TypeNode("lista", elementType);
        assertEquals("List<Integer>", mapper.mapType(typeNode));
    }

    @Test
    void testMapType_Lista_Texto() {
        TypeNode elementType = new TypeNode("texto");
        TypeNode typeNode = new TypeNode("lista", elementType);
        assertEquals("List<String>", mapper.mapType(typeNode));
    }

    @Test
    void testMapType_Lista_Objeto() {
        TypeNode elementType = new TypeNode("objeto");
        TypeNode typeNode = new TypeNode("lista", elementType);
        assertEquals("List<Map<String, Object>>", mapper.mapType(typeNode));
    }

    @Test
    void testMapType_Lista_Anidada() {
        TypeNode innerType = new TypeNode("entero");
        TypeNode innerList = new TypeNode("lista", innerType);
        TypeNode outerList = new TypeNode("lista", innerList);
        assertEquals("List<List<Integer>>", mapper.mapType(outerList));
    }

    @Test
    void testMapType_Objeto() {
        TypeNode typeNode = new TypeNode("objeto");
        assertEquals("Map<String, Object>", mapper.mapType(typeNode));
    }

    // ========== Boxed Types ==========

    @Test
    void testMapTypeBoxed_Entero() {
        TypeNode typeNode = new TypeNode("entero");
        assertEquals("Integer", mapper.mapTypeBoxed(typeNode));
    }

    @Test
    void testMapTypeBoxed_Decimal() {
        TypeNode typeNode = new TypeNode("decimal");
        assertEquals("Double", mapper.mapTypeBoxed(typeNode));
    }

    @Test
    void testMapTypeBoxed_Booleano() {
        TypeNode typeNode = new TypeNode("booleano");
        assertEquals("Boolean", mapper.mapTypeBoxed(typeNode));
    }

    @Test
    void testMapTypeBoxed_Texto() {
        TypeNode typeNode = new TypeNode("texto");
        assertEquals("String", mapper.mapTypeBoxed(typeNode));
    }

    @Test
    void testMapTypeBoxed_Null() {
        assertEquals("Void", mapper.mapTypeBoxed(null));
    }

    @Test
    void testMapTypeBoxed_Lista() {
        TypeNode elementType = new TypeNode("entero");
        TypeNode typeNode = new TypeNode("lista", elementType);
        assertEquals("List<Integer>", mapper.mapTypeBoxed(typeNode));
    }

    @Test
    void testMapTypeBoxed_Objeto() {
        TypeNode typeNode = new TypeNode("objeto");
        assertEquals("Map<String, Object>", mapper.mapTypeBoxed(typeNode));
    }

    // ========== Type Name Mapping ==========

    @Test
    void testMapTypeName_Entero() {
        assertEquals("int", mapper.mapTypeName("entero"));
    }

    @Test
    void testMapTypeName_Decimal() {
        assertEquals("double", mapper.mapTypeName("decimal"));
    }

    @Test
    void testMapTypeName_Booleano() {
        assertEquals("boolean", mapper.mapTypeName("booleano"));
    }

    @Test
    void testMapTypeName_Texto() {
        assertEquals("String", mapper.mapTypeName("texto"));
    }

    @Test
    void testMapTypeName_Lista() {
        assertEquals("List<Object>", mapper.mapTypeName("lista"));
    }

    @Test
    void testMapTypeName_Objeto() {
        assertEquals("Map<String, Object>", mapper.mapTypeName("objeto"));
    }

    @Test
    void testMapTypeName_Nulo() {
        assertEquals("void", mapper.mapTypeName("nulo"));
    }

    @Test
    void testMapTypeName_Unknown() {
        assertEquals("CustomType", mapper.mapTypeName("CustomType"));
    }

    // ========== Default Values ==========

    @Test
    void testGetDefaultValue_Int() {
        assertEquals("0", mapper.getDefaultValue("int"));
    }

    @Test
    void testGetDefaultValue_Double() {
        assertEquals("0.0", mapper.getDefaultValue("double"));
    }

    @Test
    void testGetDefaultValue_Boolean() {
        assertEquals("false", mapper.getDefaultValue("boolean"));
    }

    @Test
    void testGetDefaultValue_String() {
        assertEquals("null", mapper.getDefaultValue("String"));
    }

    @Test
    void testGetDefaultValue_List() {
        assertEquals("null", mapper.getDefaultValue("List<Integer>"));
    }

    @Test
    void testGetDefaultValue_Object() {
        assertEquals("null", mapper.getDefaultValue("Map<String, Object>"));
    }
}
