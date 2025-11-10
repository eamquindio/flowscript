package com.flowscript.semantic.errors;

public class ErrorCodes {
    // Variable errors (E001-E003)
    public static final String VARIABLE_NOT_DECLARED = "E001";
    public static final String VARIABLE_ALREADY_DECLARED = "E002";

    // Function errors (E003-E008)
    public static final String FUNCTION_NOT_DECLARED = "E003";
    public static final String FUNCTION_ALREADY_DECLARED = "E004";
    public static final String WRONG_ARGUMENT_COUNT = "E005";
    public static final String TYPE_MISMATCH = "E006";
    public static final String INVALID_OPERATION = "E007";
    public static final String MISSING_RETURN = "E008";

    // Process errors (E009-E015)
    public static final String PROCESS_NODE_NOT_FOUND = "E009";
    public static final String PROCESS_NO_START = "E010";
    public static final String PROCESS_NO_END = "E011";
    public static final String MULTIPLE_START_ELEMENTS = "E012";
    public static final String INVALID_GATEWAY_STRUCTURE = "E013";
    public static final String ENTRADA_IN_FUNCTION = "E014";
    public static final String GOTO_IN_FUNCTION = "E015";

    // Type errors (E016-E018)
    public static final String PROPERTY_ACCESS_NON_OBJECT = "E016";
    public static final String INDEX_ACCESS_NON_LIST = "E017";
    public static final String NON_BOOLEAN_CONDITION = "E018";

    // Import errors (E019)
    public static final String MODULE_NOT_FOUND = "E019";

    // Gateway errors (E020-E021)
    public static final String INSUFFICIENT_BRANCHES = "E020";
    public static final String MISSING_JOIN = "E021";

    // Additional errors (E022-E027)
    public static final String NON_INTEGER_INDEX = "E022";
    public static final String INVALID_RETURN_TYPE = "E023";
    public static final String BREAK_OUTSIDE_LOOP = "E024";
    public static final String CONTINUE_OUTSIDE_LOOP = "E025";
    public static final String BREAK_IN_PROCESS = "E026";
    public static final String CONTINUE_IN_PROCESS = "E027";

    // Warnings (W001-W014)
    public static final String VARIABLE_NEVER_USED = "W001";
    public static final String UNREACHABLE_CODE = "W002";
    public static final String UNREACHABLE_NODE = "W003";
    public static final String CONSTANT_CONDITION = "W004";
    public static final String DIVISION_BY_ZERO = "W005";
    public static final String INFINITE_LOOP = "W006";
    public static final String SHADOWING_VARIABLE = "W007";
    public static final String PROPERTY_MAY_NOT_EXIST = "W008";
    public static final String INDEX_OUT_OF_RANGE = "W009";
    public static final String TERNARY_TYPE_MISMATCH = "W010";
    public static final String IMPLICIT_TYPE_CONVERSION = "W011";
    public static final String UNUSED_PARAMETER = "W012";
    public static final String DUPLICATE_BRANCH = "W013";
    public static final String MISSING_ELSE = "W014";
}
