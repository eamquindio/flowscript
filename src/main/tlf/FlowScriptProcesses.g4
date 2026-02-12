    grammar FlowScriptProcesses;

    // Package declaration for generated code
    @header {
        package edu.eam.ingesoft.tlf;
    }

    /*
     * GRAMÁTICA DE PROCESOS PARA FLOWSCRIPT
     *
     * Este archivo define la gramática completa para el sistema de procesos
     * y estructura del programa principal de FlowScript, incluyendo:
     * - Estructura del programa (imports, declaraciones)
     * - Declaración de procesos
     * - Nodos del proceso (start, task, end, gateway)
     * - Gateways exclusivos y paralelos
     * - Control de flujo con goto
     * - Variables globales y contexto del proceso
     */

    // ============================
    // LEXER RULES (TOKENS)
    // ============================


    PROCESO       : 'process';
    FUNCION  : 'function';
    IMPORTAR : 'import';
    IMPORTAR_JAR: 'import_jar';
    COMO: 'as';
    RETORNAR: 'return';

    INICIO : 'start';
    FIN    : 'end';
    TAREA: 'task';
    PASARELA : 'gateway';
    IR_A   : 'go_to';
    CUANDO : 'when';
    RAMA : 'branch';
    UNIR  : 'join';
    SINO   : 'else';
    PARALELO: 'parallel';
    ACCION  : 'action';

    SI : 'if';
    SINO_SI    : 'else_if';
    INTENTAR   : 'try';
    CAPTURAR : 'catch';
    LANSAR   : 'throw';
    MIENTRAS  : 'while';
    PARA     : 'for';
    CADA       : 'each';
    EN     : 'in';
    DESDE    : 'from';
    HASTA     : 'to';
    PASO      : 'step';
    ROMPER    : 'break';
    CONTINUAR   : 'continue';

    ENTERO: 'integer';
    DECIMAL    : 'decimal';
    BOOLEANO  : 'boolean';
    TEXTO   : 'text';
    LISTA    : 'list';
    OBJETO  : 'object';
    VACIO  : 'void';

    NULO  : 'null';
    VERDADERO : 'true';
    FALSO  : 'false';

    Y   : 'and';
    O  : 'or';
    NO  : 'not';

    MAS  : '+';
    MENOS : '-';
    MULTIPLICAR: '*';
    DIVIDIR : '/';
    MODULO : '%';

    MENOR_IGUA: '<=';
    MAYOR_IGUAL : '>=';
    IGUAL   : '==';
    DISTINTO : '!=';
    MENOR   : '<';
    MAYOR : '>';

    ASIGNAR : '=';
    PREGUNTA : '?';
    PUNTO      : '.';

    PARENTESIS_IZQ : '(';
    PARENTESIS_DER : ')';
    LLAVE_IZQ : '{';
    LLAVE_DER  : '}';
    CORCHETE_IZQ  : '[';
    CORCHETE_DER   : ']';
    COMA  : ',';
    PUNTO_Y_COMA   : ';';
    DOS_PUNTOS : ':';
    FLECHA  : '->';

    ENTERO_LITERAL: [0-9]+;
    DECIMAL_LITERAL: [0-9]+ '.' [0-9]+;
    CADENA_LITERAL: '"' ( '\\' . | ~["\\] )* '"';


    IDENTIFICADOR : [a-zA-ZáéíóúÁÉÍÓÚñÑ_][a-zA-Z0-9áéíóúÁÉÍÓÚñÑ_]*;
    ENTRADA : 'input';

    LINEA_COMENTARIO : '//' ~[\r\n]*  -> skip ;
    BLOQUE_COMENTARIO : '/*' .*? '*/' -> skip ;
    HASH_COMENTARIO : '#'  ~[\r\n]*  -> skip ;
    ESPACIO  : [ \t\r\n]+     -> skip ;

    // ============================
    // PARSER RULES
    // ============================

    program
        : ( declaracionImportar| declaracionImportarJar|variableGlobal| declaracionFuncion| declaracionProceso
          )* EOF
        ;

    declaracionImportar
        : IMPORTAR CADENA_LITERAL (COMO IDENTIFICADOR)? PUNTO_Y_COMA?;

    declaracionImportarJar
       : IMPORTAR_JAR CADENA_LITERAL COMO IDENTIFICADOR PUNTO_Y_COMA?;

    variableGlobal
       : IDENTIFICADOR ASIGNAR valorGlobal PUNTO_Y_COMA?;

    valorGlobal
        : ENTERO_LITERAL | DECIMAL_LITERAL| CADENA_LITERAL| VERDADERO| FALSO | NULO
        | IDENTIFICADOR;

    declaracionFuncion
        : FUNCION IDENTIFICADOR PARENTESIS_IZQ listaParametros? PARENTESIS_DER FLECHA tipo
      LLAVE_IZQ cuerpoFuncion LLAVE_DER;

    listaParametros
        : parametro (COMA parametro)*;

    parametro
       :IDENTIFICADOR DOS_PUNTOS tipo;

    tipo
        : ENTERO| DECIMAL| BOOLEANO| TEXTO| LISTA
        | OBJETO| VACIO;

    cuerpoFuncion
        : ( ~LLAVE_DER )*;

    declaracionProceso
        : PROCESO IDENTIFICADOR LLAVE_IZQ cuerpoProceso LLAVE_DER;

    cuerpoProceso
        : elementoInicio (elementoTarea | pasarelaExclusiva |pasarelaParalela)* elementoFin+;

    elementoProceso
        : elementoInicio| elementoTarea| pasarelaExclusiva| pasarelaParalela| elementoFin ;

    elementoInicio
        : INICIO FLECHA IDENTIFICADOR PUNTO_Y_COMA?       ;

    elementoFin
        : FIN IDENTIFICADOR PUNTO_Y_COMA? ;

    elementoTarea
        : TAREA IDENTIFICADOR LLAVE_IZQ ACCION DOS_PUNTOS itemsAccion LLAVE_DER;

    itemsAccion
        : sentencia*;

    sentencia
        : irA| siSino
    | intentarCapturar| paraCada| pasarelaExclusiva| pasarelaParalela| asignacion| expresionStmt;

    irA
        : IR_A IDENTIFICADOR PUNTO_Y_COMA?;

    asignacion
        : lvalue ASIGNAR expresion PUNTO_Y_COMA?;

    lvalue
        : IDENTIFICADOR (PUNTO IDENTIFICADOR | CORCHETE_IZQ expresion CORCHETE_DER)*;

    expresionStmt
        : expresion PUNTO_Y_COMA?;

    siSino
       : SI expresion bloque (SINO_SI expresion bloque)* (SINO bloque)?;

    intentarCapturar
        : INTENTAR bloque
          CAPTURAR PARENTESIS_IZQ IDENTIFICADOR PARENTESIS_DER bloque;

    paraCada
        : PARA CADA IDENTIFICADOR EN expresion bloque;

    bloque
        : LLAVE_IZQ sentencia* LLAVE_DER;

    pasarelaExclusiva
        : PASARELA IDENTIFICADOR LLAVE_IZQ cuando+ sino? LLAVE_DER;

    cuando
        : CUANDO expresion FLECHA IDENTIFICADOR PUNTO_Y_COMA?;

    sino
        : SINO FLECHA IDENTIFICADOR PUNTO_Y_COMA?;

    pasarelaParalela
    : PASARELA IDENTIFICADOR PARALELO LLAVE_IZQ ramaParalela+ unirRama LLAVE_DER;

    ramaParalela
        : RAMA FLECHA IDENTIFICADOR PUNTO_Y_COMA?;

    unirRama
      : UNIR FLECHA IDENTIFICADOR PUNTO_Y_COMA?;

    expresion
        : expresion PREGUNTA expresion DOS_PUNTOS expresion| expresion O expresion| expresion Y expresion
        | expresion (IGUAL | DISTINTO) expresion| expresion (MENOR | MENOR_IGUA | MAYOR | MAYOR_IGUAL) expresion
        | expresion (MAS | MENOS) expresion| expresion (MULTIPLICAR | DIVIDIR | MODULO) expresion
        | NO expresion| MENOS expresion
        | postfixExpr;

    postfixExpr
        : primario ( PUNTO IDENTIFICADOR| CORCHETE_IZQ expresion CORCHETE_DER
    | PARENTESIS_IZQ listaArgumentos? PARENTESIS_DER)*;

    primario
        : literal
        | ENTRADA| IDENTIFICADOR| PARENTESIS_IZQ expresion PARENTESIS_DER| objetoLiteral
    |listaLiteral;

    listaArgumentos
        : expresion (COMA expresion)*;

    literal
        : ENTERO_LITERAL
    | DECIMAL_LITERAL| CADENA_LITERAL| VERDADERO| FALSO| NULO;

    objetoLiteral
        : LLAVE_IZQ (campoObjeto (COMA campoObjeto)*)? LLAVE_DER;

    campoObjeto
        : (IDENTIFICADOR | CADENA_LITERAL) DOS_PUNTOS expresion;

    listaLiteral
        : CORCHETE_IZQ (expresion (COMA expresion)*)? CORCHETE_DER;
