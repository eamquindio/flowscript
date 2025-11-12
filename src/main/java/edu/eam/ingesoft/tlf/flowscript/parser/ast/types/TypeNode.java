package edu.eam.ingesoft.tlf.flowscript.parser.ast.types;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class TypeNode extends ASTNode {
    public enum TypeKind { ENTERO, DECIMAL, BOOLEANO, TEXTO, LISTA, OBJETO, VACIO }
    private final TypeKind kind;
    
    public TypeNode(TypeKind kind) { this.kind = kind; }
    public TypeKind getKind() { return kind; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
