package edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.BlockNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.types.*;
import java.util.*;

public class FunctionDeclarationNode extends DeclarationNode {
    private final String name;
    private final List<ParameterNode> parameters;
    private final TypeNode returnType;
    private final BlockNode body;

    public FunctionDeclarationNode(String name, List<ParameterNode> parameters,
                                    TypeNode returnType, BlockNode body) {
        this.name = name;
        this.parameters = parameters != null ? parameters : new ArrayList<>();
        this.returnType = returnType;
        this.body = body;
    }

    public String getName() { return name; }
    public List<ParameterNode> getParameters() { return parameters; }
    public TypeNode getReturnType() { return returnType; }
    public BlockNode getBody() { return body; }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
