package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;
import java.util.Objects;

/**
 * Acceso a propiedad: target.property
 */
public class PropertyAccessNode extends ExpressionNode {
    private final ExpressionNode target;
    private final Token propertyToken;   // IDENTIFIER de la propiedad
    private final String propertyName;

    /**
     * Constructor canónico: recibe el token del IDENTIFIER (no el '.').
     */
    public PropertyAccessNode(ExpressionNode target, Token propertyToken) {
        super(Objects.requireNonNull(propertyToken, "propertyToken"));
        this.target = Objects.requireNonNull(target, "target");
        this.propertyToken = propertyToken;
        this.propertyName = String.valueOf(propertyToken.getValue()); // ← inicializa SIEMPRE
    }

    /** Opcional: si ya traes el nombre calculado. */
    public PropertyAccessNode(ExpressionNode target, String propertyName, Token propertyToken) {
        super(Objects.requireNonNull(propertyToken, "propertyToken"));
        this.target = Objects.requireNonNull(target, "target");
        this.propertyToken = propertyToken;
        this.propertyName = Objects.requireNonNull(propertyName, "propertyName");
    }

    public ExpressionNode getObject() { return target; }        // compat con código existente
    public ExpressionNode getTarget() { return target; }
    public String getPropertyName() { return propertyName; }
    public Token getPropertyToken() { return propertyToken; }

    @Override public String getNodeType() { return "PropertyAccess"; }

    @Override
    public String getExpressionType() {
        // Se define en análisis semántico
        return "property";
    }

    @Override
    public String toString() {
        return "PropertyAccess(" + target.getNodeType() + "." + propertyName + ")";
    }
}
