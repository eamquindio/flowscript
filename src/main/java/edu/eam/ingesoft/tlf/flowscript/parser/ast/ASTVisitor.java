package edu.eam.ingesoft.tlf.flowscript.parser.ast;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.literals.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.types.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.*;

/**
 * Interfaz para el patrón Visitor sobre el AST.
 * Permite implementar operaciones sobre el árbol sin modificar las clases de nodos.
 */
public interface ASTVisitor {
    // Declaraciones
    void visit(ProgramNode node);
    void visit(FunctionDeclarationNode node);
    void visit(ProcessDeclarationNode node);

    // Tipos y Parámetros
    void visit(TypeNode node);
    void visit(ParameterNode node);

    // Statements
    void visit(BlockNode node);
    void visit(VariableDeclarationStatementNode node);
    void visit(AssignmentStatementNode node);
    void visit(ReturnStatementNode node);
    void visit(ExpressionStatementNode node);
    void visit(IfStatementNode node);
    void visit(ForStatementNode node);
    void visit(GotoStatementNode node);

    // Expresiones
    void visit(BinaryExpressionNode node);
    void visit(UnaryExpressionNode node);
    void visit(FunctionCallNode node);
    void visit(IdentifierNode node);
    void visit(MemberAccessNode node);
    void visit(IndexAccessNode node);

    // Literales
    void visit(IntegerLiteralNode node);
    void visit(DecimalLiteralNode node);
    void visit(BooleanLiteralNode node);
    void visit(StringLiteralNode node);
    void visit(NullLiteralNode node);
    void visit(ListLiteralNode node);
    void visit(ObjectLiteralNode node);

    // Elementos de Proceso
    void visit(StartElementNode node);
    void visit(TaskElementNode node);
    void visit(ExclusiveGatewayNode node);
    void visit(ParallelGatewayNode node);
    void visit(EndElementNode node);
}
