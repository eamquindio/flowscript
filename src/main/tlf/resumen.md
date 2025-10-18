StartElementParser.java


implementado para consumir las palabras clave start o inicio, el símbolo ->, y un identificador de destino.


Implementación resumida:

context.consume(TokenType.START);
context.consume(TokenType.ARROW);
Token identifier = context.consume(TokenType.IDENTIFIER);
return new StartElementNode(identifier.getValue());

TaskElementParser.java

Ahora: interpreta tareas simples (task o tarea), consumiendo el identificador asociado.


Ejemplo:

task ValidarUsuario


Código clave:

context.consume(TokenType.TASK);
Token identifier = context.consume(TokenType.IDENTIFIER);
return new TaskElementNode(identifier.getValue());

GatewayElementParser.java


Ahora: soporta compuertas (gateway o compuerta), permitiendo agregar ramas (branch) o uniones (join).


Ejemplo:

gateway decision
    branch -> ValidarDatos
    branch -> RevisarCredito


Cambios:

Se agregaron bucles para consumir múltiples cláusulas branch.

Se añadió compatibilidad con join.

ParallelBranchParser.java


parser funcional para las ramas paralelas de gateways.

Ejemplo:

branch -> VerificarCredito


Código implementado:

context.consume(TokenType.BRANCH);
context.consume(TokenType.ARROW);
Token identifier = context.consume(TokenType.IDENTIFIER);
return new ParallelBranchNode(identifier.getValue());

JoinParser.java

permite analizar la instrucción join IDENTIFIER, representando la sincronización de ramas paralelas.

Ejemplo:

join FinalizarProceso


Código:

context.consume(TokenType.JOIN);
Token identifier = context.consume(TokenType.IDENTIFIER);
return new JoinNode(identifier.getValue());

EndElementParser.java

Ahora: implementado para detectar el final del proceso (end o fin).


Ejemplo:

end


Código:

context.consume(TokenType.END);
return new EndElementNode();