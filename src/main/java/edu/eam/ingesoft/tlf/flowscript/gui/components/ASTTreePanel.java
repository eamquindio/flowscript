package edu.eam.ingesoft.tlf.flowscript.gui.components;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.literals.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.types.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

/**
 * Panel que muestra el árbol de derivación (AST) en un JTree.
 */
public class ASTTreePanel extends JPanel {
    private final JTree tree;
    private final DefaultTreeModel treeModel;
    private final JLabel statusLabel;

    public ASTTreePanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));

        // Nodo raíz inicial
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("AST vacío");
        treeModel = new DefaultTreeModel(root);

        // Árbol
        tree = new JTree(treeModel);
        tree.setFont(new Font("Monospaced", Font.PLAIN, 12));
        tree.setShowsRootHandles(true);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de estado
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("No hay AST para mostrar");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }

    /**
     * Actualiza el árbol con un AST.
     */
    public void updateAST(ProgramNode program) {
        if (program == null) {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("AST vacío");
            treeModel.setRoot(root);
            statusLabel.setText("No hay AST para mostrar");
            return;
        }

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Programa");
        buildTree(root, program);
        treeModel.setRoot(root);

        // Expandir primer nivel
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }

        statusLabel.setText(String.format("AST con %d declaraciones", program.getDeclarations().size()));
    }

    /**
     * Construye recursivamente el árbol a partir del AST.
     */
    private void buildTree(DefaultMutableTreeNode parent, ASTNode node) {
        if (node == null) return;

        if (node instanceof ProgramNode) {
            ProgramNode program = (ProgramNode) node;
            for (DeclarationNode decl : program.getDeclarations()) {
                DefaultMutableTreeNode declNode = new DefaultMutableTreeNode(getNodeLabel(decl));
                parent.add(declNode);
                buildTree(declNode, decl);
            }
        }
        else if (node instanceof FunctionDeclarationNode) {
            FunctionDeclarationNode func = (FunctionDeclarationNode) node;

            // Parámetros
            if (!func.getParameters().isEmpty()) {
                DefaultMutableTreeNode paramsNode = new DefaultMutableTreeNode("Parámetros");
                parent.add(paramsNode);
                for (ParameterNode param : func.getParameters()) {
                    DefaultMutableTreeNode paramNode = new DefaultMutableTreeNode(
                        param.getName() + ": " + param.getType().getKind()
                    );
                    paramsNode.add(paramNode);
                }
            }

            // Tipo de retorno
            DefaultMutableTreeNode returnNode = new DefaultMutableTreeNode(
                "Retorna: " + func.getReturnType().getKind()
            );
            parent.add(returnNode);

            // Cuerpo
            DefaultMutableTreeNode bodyNode = new DefaultMutableTreeNode("Cuerpo");
            parent.add(bodyNode);
            buildTree(bodyNode, func.getBody());
        }
        else if (node instanceof ProcessDeclarationNode) {
            ProcessDeclarationNode process = (ProcessDeclarationNode) node;
            for (ProcessElementNode element : process.getElements()) {
                DefaultMutableTreeNode elementNode = new DefaultMutableTreeNode(getNodeLabel(element));
                parent.add(elementNode);
                buildTree(elementNode, element);
            }
        }
        else if (node instanceof StartElementNode) {
            StartElementNode start = (StartElementNode) node;
            parent.add(new DefaultMutableTreeNode("→ " + start.getTargetTaskName()));
        }
        else if (node instanceof TaskElementNode) {
            TaskElementNode task = (TaskElementNode) node;
            DefaultMutableTreeNode actionsNode = new DefaultMutableTreeNode("Acciones");
            parent.add(actionsNode);
            for (StatementNode stmt : task.getActions()) {
                DefaultMutableTreeNode stmtNode = new DefaultMutableTreeNode(getNodeLabel(stmt));
                actionsNode.add(stmtNode);
                buildTree(stmtNode, stmt);
            }
        }
        else if (node instanceof ExclusiveGatewayNode) {
            ExclusiveGatewayNode gateway = (ExclusiveGatewayNode) node;
            for (ExclusiveGatewayNode.ConditionalBranch branch : gateway.getBranches()) {
                String label = branch.isElseBranch() ? "sino" : "si/sino_si";
                DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode(label + " → " + branch.getTargetTaskName());
                parent.add(branchNode);
                if (branch.getCondition() != null) {
                    DefaultMutableTreeNode condNode = new DefaultMutableTreeNode("Condición");
                    branchNode.add(condNode);
                    buildTree(condNode, branch.getCondition());
                }
            }
        }
        else if (node instanceof ParallelGatewayNode) {
            ParallelGatewayNode gateway = (ParallelGatewayNode) node;
            DefaultMutableTreeNode branchesNode = new DefaultMutableTreeNode("Ramas");
            parent.add(branchesNode);
            for (ParallelGatewayNode.ParallelBranch branch : gateway.getBranches()) {
                branchesNode.add(new DefaultMutableTreeNode("→ " + branch.getTargetTaskName()));
            }
            parent.add(new DefaultMutableTreeNode("Unir → " + gateway.getMergeTargetName()));
        }
        else if (node instanceof EndElementNode) {
            // No hay hijos
        }
        else if (node instanceof BlockNode) {
            BlockNode block = (BlockNode) node;
            for (StatementNode stmt : block.getStatements()) {
                DefaultMutableTreeNode stmtNode = new DefaultMutableTreeNode(getNodeLabel(stmt));
                parent.add(stmtNode);
                buildTree(stmtNode, stmt);
            }
        }
        else if (node instanceof IfStatementNode) {
            IfStatementNode ifStmt = (IfStatementNode) node;
            for (int i = 0; i < ifStmt.getBranches().size(); i++) {
                IfStatementNode.ConditionalBranch branch = ifStmt.getBranches().get(i);
                String label = branch.isElseBranch() ? "sino" :
                    (i == 0 ? "si" : "sino_si");
                DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode(label);
                parent.add(branchNode);

                if (branch.getCondition() != null) {
                    DefaultMutableTreeNode condNode = new DefaultMutableTreeNode("Condición");
                    branchNode.add(condNode);
                    buildTree(condNode, branch.getCondition());
                }

                DefaultMutableTreeNode bodyNode = new DefaultMutableTreeNode("Entonces");
                branchNode.add(bodyNode);
                buildTree(bodyNode, branch.getThenBlock());
            }
        }
        else if (node instanceof ForStatementNode) {
            ForStatementNode forStmt = (ForStatementNode) node;
            parent.add(new DefaultMutableTreeNode("Variable: " + forStmt.getIteratorVariable()));

            DefaultMutableTreeNode iterableNode = new DefaultMutableTreeNode("En");
            parent.add(iterableNode);
            buildTree(iterableNode, forStmt.getIterable());

            DefaultMutableTreeNode bodyNode = new DefaultMutableTreeNode("Cuerpo");
            parent.add(bodyNode);
            buildTree(bodyNode, forStmt.getBody());
        }
        else if (node instanceof VariableDeclarationStatementNode) {
            VariableDeclarationStatementNode varDecl = (VariableDeclarationStatementNode) node;
            DefaultMutableTreeNode valueNode = new DefaultMutableTreeNode("Valor");
            parent.add(valueNode);
            buildTree(valueNode, varDecl.getValue());
        }
        else if (node instanceof AssignmentStatementNode) {
            AssignmentStatementNode assignment = (AssignmentStatementNode) node;

            DefaultMutableTreeNode targetNode = new DefaultMutableTreeNode("Objetivo");
            parent.add(targetNode);
            buildTree(targetNode, assignment.getTarget());

            DefaultMutableTreeNode valueNode = new DefaultMutableTreeNode("Valor");
            parent.add(valueNode);
            buildTree(valueNode, assignment.getValue());
        }
        else if (node instanceof ReturnStatementNode) {
            ReturnStatementNode ret = (ReturnStatementNode) node;
            if (ret.hasValue()) {
                DefaultMutableTreeNode valueNode = new DefaultMutableTreeNode("Valor");
                parent.add(valueNode);
                buildTree(valueNode, ret.getValue());
            }
        }
        else if (node instanceof ExpressionStatementNode) {
            ExpressionStatementNode exprStmt = (ExpressionStatementNode) node;
            buildTree(parent, exprStmt.getExpression());
        }
        else if (node instanceof GotoStatementNode) {
            GotoStatementNode gotoStmt = (GotoStatementNode) node;
            parent.add(new DefaultMutableTreeNode("→ " + gotoStmt.getTargetName()));
        }
        else if (node instanceof BinaryExpressionNode) {
            BinaryExpressionNode binExpr = (BinaryExpressionNode) node;

            DefaultMutableTreeNode leftNode = new DefaultMutableTreeNode("Izquierda");
            parent.add(leftNode);
            buildTree(leftNode, binExpr.getLeft());

            DefaultMutableTreeNode rightNode = new DefaultMutableTreeNode("Derecha");
            parent.add(rightNode);
            buildTree(rightNode, binExpr.getRight());
        }
        else if (node instanceof UnaryExpressionNode) {
            UnaryExpressionNode unaryExpr = (UnaryExpressionNode) node;
            DefaultMutableTreeNode operandNode = new DefaultMutableTreeNode("Operando");
            parent.add(operandNode);
            buildTree(operandNode, unaryExpr.getOperand());
        }
        else if (node instanceof FunctionCallNode) {
            FunctionCallNode funcCall = (FunctionCallNode) node;

            // Mostrar la función llamada
            DefaultMutableTreeNode funcNode = new DefaultMutableTreeNode("Función");
            parent.add(funcNode);
            buildTree(funcNode, funcCall.getFunction());

            // Mostrar argumentos si los hay
            if (!funcCall.getArguments().isEmpty()) {
                DefaultMutableTreeNode argsNode = new DefaultMutableTreeNode("Argumentos");
                parent.add(argsNode);
                for (ExpressionNode arg : funcCall.getArguments()) {
                    DefaultMutableTreeNode argNode = new DefaultMutableTreeNode(getNodeLabel(arg));
                    argsNode.add(argNode);
                    buildTree(argNode, arg);
                }
            }
        }
        else if (node instanceof MemberAccessNode) {
            MemberAccessNode memberAccess = (MemberAccessNode) node;

            DefaultMutableTreeNode objectNode = new DefaultMutableTreeNode("Objeto");
            parent.add(objectNode);
            buildTree(objectNode, memberAccess.getObject());

            parent.add(new DefaultMutableTreeNode("Miembro: " + memberAccess.getMemberName()));
        }
        else if (node instanceof IndexAccessNode) {
            IndexAccessNode indexAccess = (IndexAccessNode) node;

            DefaultMutableTreeNode arrayNode = new DefaultMutableTreeNode("Array");
            parent.add(arrayNode);
            buildTree(arrayNode, indexAccess.getArray());

            DefaultMutableTreeNode indexNode = new DefaultMutableTreeNode("Índice");
            parent.add(indexNode);
            buildTree(indexNode, indexAccess.getIndex());
        }
        else if (node instanceof ListLiteralNode) {
            ListLiteralNode listLit = (ListLiteralNode) node;
            for (int i = 0; i < listLit.getElements().size(); i++) {
                DefaultMutableTreeNode elemNode = new DefaultMutableTreeNode("[" + i + "]");
                parent.add(elemNode);
                buildTree(elemNode, listLit.getElements().get(i));
            }
        }
        else if (node instanceof ObjectLiteralNode) {
            ObjectLiteralNode objLit = (ObjectLiteralNode) node;
            for (ObjectLiteralNode.ObjectMember member : objLit.getMembers()) {
                DefaultMutableTreeNode memberNode = new DefaultMutableTreeNode(member.getKey());
                parent.add(memberNode);
                buildTree(memberNode, member.getValue());
            }
        }
        // Literales simples y identificadores no tienen hijos
    }

    /**
     * Obtiene una etiqueta legible para un nodo AST.
     */
    private String getNodeLabel(ASTNode node) {
        if (node instanceof FunctionDeclarationNode) {
            return "Función: " + ((FunctionDeclarationNode) node).getName();
        }
        else if (node instanceof ProcessDeclarationNode) {
            return "Proceso: " + ((ProcessDeclarationNode) node).getName();
        }
        else if (node instanceof StartElementNode) {
            return "Inicio";
        }
        else if (node instanceof TaskElementNode) {
            return "Tarea: " + ((TaskElementNode) node).getName();
        }
        else if (node instanceof ExclusiveGatewayNode) {
            return "Gateway Exclusivo: " + ((ExclusiveGatewayNode) node).getName();
        }
        else if (node instanceof ParallelGatewayNode) {
            return "Gateway Paralelo: " + ((ParallelGatewayNode) node).getName();
        }
        else if (node instanceof EndElementNode) {
            return "Fin: " + ((EndElementNode) node).getName();
        }
        else if (node instanceof VariableDeclarationStatementNode) {
            return "Variable: " + ((VariableDeclarationStatementNode) node).getName();
        }
        else if (node instanceof AssignmentStatementNode) {
            return "Asignación";
        }
        else if (node instanceof ReturnStatementNode) {
            return "Retornar";
        }
        else if (node instanceof IfStatementNode) {
            return "Si/Sino";
        }
        else if (node instanceof ForStatementNode) {
            return "Para";
        }
        else if (node instanceof GotoStatementNode) {
            return "ir_a: " + ((GotoStatementNode) node).getTargetName();
        }
        else if (node instanceof BinaryExpressionNode) {
            return "Operador: " + ((BinaryExpressionNode) node).getOperator();
        }
        else if (node instanceof UnaryExpressionNode) {
            return "Operador: " + ((UnaryExpressionNode) node).getOperator();
        }
        else if (node instanceof FunctionCallNode) {
            FunctionCallNode funcCall = (FunctionCallNode) node;
            String funcName = "función";
            if (funcCall.getFunction() instanceof IdentifierNode) {
                funcName = ((IdentifierNode) funcCall.getFunction()).getName();
            }
            return "Llamada: " + funcName;
        }
        else if (node instanceof IdentifierNode) {
            return "Identificador: " + ((IdentifierNode) node).getName();
        }
        else if (node instanceof IntegerLiteralNode) {
            return "Entero: " + ((IntegerLiteralNode) node).getValue();
        }
        else if (node instanceof DecimalLiteralNode) {
            return "Decimal: " + ((DecimalLiteralNode) node).getValue();
        }
        else if (node instanceof BooleanLiteralNode) {
            return "Booleano: " + ((BooleanLiteralNode) node).getValue();
        }
        else if (node instanceof StringLiteralNode) {
            return "Texto: \"" + ((StringLiteralNode) node).getValue() + "\"";
        }
        else if (node instanceof NullLiteralNode) {
            return "nulo";
        }
        else if (node instanceof ListLiteralNode) {
            return "Lista[" + ((ListLiteralNode) node).getElements().size() + "]";
        }
        else if (node instanceof ObjectLiteralNode) {
            return "Objeto{" + ((ObjectLiteralNode) node).getMembers().size() + "}";
        }
        else if (node instanceof MemberAccessNode) {
            return "Acceso a miembro";
        }
        else if (node instanceof IndexAccessNode) {
            return "Acceso por índice";
        }
        else {
            return node.getClass().getSimpleName();
        }
    }

    /**
     * Limpia el árbol.
     */
    public void clear() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("AST vacío");
        treeModel.setRoot(root);
        statusLabel.setText("No hay AST para mostrar");
    }
}
