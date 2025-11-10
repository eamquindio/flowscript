package com.flowscript.semantic.types;

public class Type {
    public static final Type INTEGER = new Type("entero");
    public static final Type DECIMAL = new Type("decimal");
    public static final Type BOOLEAN = new Type("booleano");
    public static final Type TEXT = new Type("texto");
    public static final Type LIST = new Type("lista");
    public static final Type OBJECT = new Type("objeto");
    public static final Type NULL = new Type("nulo");
    public static final Type VOID = new Type("vacio");
    public static final Type ANY = new Type("any");  // For dynamic/unknown types

    private final String name;

    private Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isNumeric() {
        return this.equals(INTEGER) || this.equals(DECIMAL);
    }

    public boolean isComparable() {
        return isNumeric() || this.equals(TEXT);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Type)) return false;
        Type other = (Type) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
