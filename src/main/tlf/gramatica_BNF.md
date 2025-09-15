<functionProgram> ::= { <declaracionFuncion> | <sentenciaTope> } EOF

<sentenciaTope> ::= <sentencia>

<declaracionFuncion> ::= "function" <IDENTIFICADOR> "(" [ <listaParametros> ] ")" [ <tipoRetorno> ] <bloque>

<listaParametros> ::= <parametro> { "," <parametro> }

<parametro> ::= <IDENTIFICADOR> ":" <tipoNombre>

<tipoRetorno> ::= "->" <tipoNombre>

<bloque> ::= "{" { <sentencia> [";"] } "}"

<sentencia> ::= <asignacion>
              | <expresionStatement>
              | <sentenciaIf>
              | <sentenciaWhile>
              | <sentenciaForEach>
              | <sentenciaForRange>
              | <sentenciaReturn>
              | <sentenciaTryCatch>
              | <sentenciaThrow>
              | <sentenciaBreak>
              | <sentenciaContinue>

<asignacion> ::= <lvalue> "=" <expresion>

<lvalue> ::= <IDENTIFICADOR>
           | <lvalue> "." <IDENTIFICADOR>
           | <lvalue> "[" <expresion> "]"

<expresionStatement> ::= <expresion> [ ";" ]

<sentenciaReturn> ::= "return" [ <expresion> ] [ ";" ]

<sentenciaIf> ::= "if" <expresion> <bloque> { <parteElseIf> } [ "else" <bloque> ]

<parteElseIf> ::= "else_if" <expresion> <bloque>

<sentenciaWhile> ::= "while" <expresion> <bloque>

<sentenciaForEach> ::= "for each" <IDENTIFICADOR> "in" <expresion> <bloque>

<sentenciaForRange> ::= "for" <IDENTIFICADOR> "from" <expresion> "to" <expresion> [ "step" <expresion> ] <bloque>

<sentenciaTryCatch> ::= "try" <bloque> { "catch" "(" <IDENTIFICADOR> ")" <bloque> }

<sentenciaThrow> ::= "throw" <expresion> [ ";" ]

<sentenciaBreak> ::= "break" [ ";" ]

<sentenciaContinue> ::= "continue" [ ";" ]

<expresion> ::= <expresionOr>

<expresionOr> ::= <expresionAnd> { "or" <expresionAnd> }

<expresionAnd> ::= <expresionEq> { "and" <expresionEq> }

<expresionEq> ::= <expresionRel> { ( "==" | "!=" ) <expresionRel> }

<expresionRel> ::= <expresionAdd> { ( "<" | "<=" | ">" | ">=" ) <expresionAdd> }

<expresionAdd> ::= <expresionMul> { ( "+" | "-" ) <expresionMul> }

<expresionMul> ::= <expresionUnary> { ( "*" | "/" | "%" ) <expresionUnary> }

<expresionUnary> ::= ( "not" | "-" ) <expresionUnary>
                   | <expresionPostfijo>

<expresionPostfijo> ::= <expresionPrimario> { ( "." <IDENTIFICADOR> )
                                             | ( "." <IDENTIFICADOR> "(" [ <listaArgumentos> ] ")" )
                                             | ( "(" [ <listaArgumentos> ] ")" )
                                             | ( "[" <expresion> "]" )
                                             }

<expresionPrimario> ::= <literal>
                      | <IDENTIFICADOR>
                      | "(" <expresion> ")"
                      | <listaLiteral>
                      | <objetoLiteral>

<listaArgumentos> ::= <expresion> { "," <expresion> }

<literal> ::= "null"
            | <BOOLEAN>
            | <DECIMAL>
            | <NUMERO>
            | <STRING>

<listaLiteral> ::= "[" [ <expresion> { "," <expresion> } ] "]"

<objetoLiteral> ::= "{" [ <entradaObjeto> { "," <entradaObjeto> } ] "}"

<entradaObjeto> ::= ( <IDENTIFICADOR> | <STRING> ) ( ":" | "=" ) <expresion>

<tipoNombre> ::= "integer"
               | "decimal"
               | "boolean"
               | "text"
               | "list"
               | "object"
               | "void"
