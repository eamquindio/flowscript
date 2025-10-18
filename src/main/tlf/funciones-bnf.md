programa ::= funcion*

funcion ::= "function" id "(" parametros? ")" ("->" tipo)? bloque

parametros ::= parametro ("," parametro)*

parametro ::= id ":" tipo

tipo ::= "integer" | "decimal" | "boolean" | "text" | "list" | "object" | "void"

bloque ::= "{" sentencias? "}"

sentencias ::= sentencia*

sentencia ::= decl | si | mientras | para_cada | para_desde | intentar | lanzar | retornar | romper | continuar | expr | bloque

decl ::= id "=" expr

si ::= "if" expr bloque ("else_if" expr bloque)* ("else" bloque)?

mientras ::= "while" expr bloque

para_cada ::= "for" "each" id "in" expr bloque

para_desde ::= "for" id "from" expr "to" expr ("step" expr)? bloque

intentar ::= "try" bloque "catch" "(" id ")" bloque

lanzar ::= "throw" expr

retornar ::= "return" expr?

romper ::= "break"

continuar ::= "continue"

expr ::= expr "or" expr
      | expr "and" expr
      | expr ("==" | "!=") expr
      | expr ("<" | ">" | "<=" | ">=") expr
      | expr ("+" | "-") expr
      | expr ("*" | "/" | "%") expr
      | "not" expr
      | "-" expr
      | expr "." id
      | expr "[" expr "]"
      | llamada
      | primario

llamada ::= "(" argumentos? ")"

argumentos ::= expr ("," expr)*

primario ::= id | literal | objeto | lista | "(" expr ")"

literal ::= entero | decimal | "true" | "false" | "null" | texto

objeto ::= "{" pares? "}"

pares ::= par ("," par)*

par ::= (id | texto) ":" expr

lista ::= "[" elementos? "]"

elementos ::= expr ("," expr)*
