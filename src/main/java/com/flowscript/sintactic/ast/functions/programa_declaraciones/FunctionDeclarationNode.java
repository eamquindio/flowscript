package com.flowscript.sintactic.ast.functions.programa_declaraciones;

import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import com.flowscript.lexer.Token;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a function declaration.
 * Syntax: function functionName(param1: type1, param2: type2) -> returnType {
 * ... }
 * Contains the function signature and implementation body.
 */
public class FunctionDeclarationNode extends DeclarationNode {
  private final String name;
  private final List<ParameterNode> parameters;
  private final TypeNode returnType;
  private final BlockNode body;

  public FunctionDeclarationNode(Token functionToken, String name,
      List<ParameterNode> parameters,
      TypeNode returnType, BlockNode body) {
    super(functionToken);
    this.name = name;
    this.parameters = new ArrayList<>(parameters);
    this.returnType = returnType;
    this.body = body;
  }

  public String getName() {
    return name;
  }

  public List<ParameterNode> getParameters() {
    return new ArrayList<>(parameters);
  }

  public TypeNode getReturnType() {
    return returnType;
  }

  public String getReturnTypeName() {
    return returnType != null ? returnType.getTypeName() : null;
  }

  public BlockNode getBody() {
    return body;
  }

  @Override
  public String getDeclaredName() {
    return name;
  }

  @Override
  public String getNodeType() {
    return "FunctionDeclaration";
  }

  @Override
  public String toString() {
    return "FunctionDeclaration(" + name + ")" +
        " with " + parameters.size() + " parameters" +
        (returnType != null ? " -> " + returnType.getTypeName() : "");
  }
}
