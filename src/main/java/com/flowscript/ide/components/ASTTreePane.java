package com.flowscript.ide.components;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.*;
import com.flowscript.sintactic.ast.functions.statements_basicos.*;
import com.flowscript.sintactic.ast.functions.expresiones.*;
import com.flowscript.sintactic.ast.functions.control_ejecucion.*;
import com.flowscript.sintactic.ast.functions.control_flujo.*;
import com.flowscript.sintactic.ast.process.estructura_principal.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

/**
 * Component for visualizing the Abstract Syntax Tree (AST) of FlowScript code.
 * Displays a hierarchical tree view of the parsed program structure.
 */
public class ASTTreePane extends BorderPane {

    private final TreeView<ASTNodeWrapper> treeView;
    private final CheckBox autoUpdateCheckBox;
    private final Button refreshButton;
    private ProgramNode currentAST;

    public ASTTreePane() {
        // Create tree view
        treeView = new TreeView<>();
        treeView.setShowRoot(true);
        treeView.setCellFactory(tv -> new ASTTreeCell());

        // Create toolbar
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(5));

        Label titleLabel = new Label("AST Tree");
        titleLabel.setStyle("-fx-font-weight: bold;");

        refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> refreshTree());

        autoUpdateCheckBox = new CheckBox("Auto-update");
        autoUpdateCheckBox.setSelected(true);

        Button expandAllButton = new Button("Expand All");
        expandAllButton.setOnAction(e -> expandAll(treeView.getRoot()));

        Button collapseAllButton = new Button("Collapse All");
        collapseAllButton.setOnAction(e -> collapseAll(treeView.getRoot()));

        toolbar.getChildren().addAll(
            titleLabel,
            new Separator(javafx.geometry.Orientation.VERTICAL),
            refreshButton,
            autoUpdateCheckBox,
            new Separator(javafx.geometry.Orientation.VERTICAL),
            expandAllButton,
            collapseAllButton
        );

        // Set layout
        setTop(toolbar);
        setCenter(treeView);
        setPadding(new Insets(5));

        // Initialize with empty tree
        initializeEmptyTree();
    }

    private void initializeEmptyTree() {
        TreeItem<ASTNodeWrapper> root = new TreeItem<>(
            new ASTNodeWrapper(null, "No AST - Parse a file to see the tree")
        );
        treeView.setRoot(root);
    }

    /**
     * Updates the AST tree view with a new parsed program.
     */
    public void updateAST(ProgramNode ast) {
        if (ast == null) {
            initializeEmptyTree();
            return;
        }

        this.currentAST = ast;
        refreshTree();
    }

    private void refreshTree() {
        if (currentAST == null) {
            initializeEmptyTree();
            return;
        }

        // Create root node
        TreeItem<ASTNodeWrapper> root = new TreeItem<>(
            new ASTNodeWrapper(currentAST, "Program")
        );
        root.setExpanded(true);

        // Populate tree
        populateTree(root, currentAST);

        treeView.setRoot(root);
    }

    private void populateTree(TreeItem<ASTNodeWrapper> parentItem, ASTNode node) {
        if (node == null) return;

        // Get children based on node type
        List<ASTNodeWrapper> children = getNodeChildren(node);

        for (ASTNodeWrapper childWrapper : children) {
            TreeItem<ASTNodeWrapper> childItem = new TreeItem<>(childWrapper);
            parentItem.getChildren().add(childItem);

            // Recursively populate children
            if (childWrapper.node != null) {
                populateTree(childItem, childWrapper.node);
            }
        }
    }

    private List<ASTNodeWrapper> getNodeChildren(ASTNode node) {
        List<ASTNodeWrapper> children = new ArrayList<>();

        // Program
        if (node instanceof ProgramNode) {
            ProgramNode program = (ProgramNode) node;
            for (DeclarationNode decl : program.getDeclarations()) {
                children.add(new ASTNodeWrapper(decl, decl.getNodeType() + ": " + decl.getDeclaredName()));
            }
        }
        // Function Declaration
        else if (node instanceof FunctionDeclarationNode) {
            FunctionDeclarationNode func = (FunctionDeclarationNode) node;
            children.add(new ASTNodeWrapper(null, "Parameters: " + func.getParameters().size()));
            for (var param : func.getParameters()) {
                children.add(new ASTNodeWrapper(param, "Parameter: " + param.getName() + " : " + param.getTypeName()));
            }
            if (func.getReturnType() != null) {
                children.add(new ASTNodeWrapper(func.getReturnType(), "ReturnType: " + func.getReturnTypeName()));
            }
            if (func.getBody() != null) {
                children.add(new ASTNodeWrapper(func.getBody(), "Body"));
            }
        }
        // Process Declaration
        else if (node instanceof ProcessDeclarationNode) {
            ProcessDeclarationNode process = (ProcessDeclarationNode) node;
            for (ASTNode element : process.getProcessElements()) {
                String label = element.getNodeType();
                if (element instanceof StartElementNode) {
                    label += " → " + ((StartElementNode) element).getTargetNodeName();
                } else if (element instanceof EndElementNode) {
                    label += ": " + ((EndElementNode) element).getEndName();
                } else if (element instanceof TaskElementNode) {
                    label += ": " + ((TaskElementNode) element).getTaskName();
                }
                children.add(new ASTNodeWrapper(element, label));
            }
        }
        // Block
        else if (node instanceof BlockNode) {
            BlockNode block = (BlockNode) node;
            for (StatementNode stmt : block.getStatements()) {
                children.add(new ASTNodeWrapper(stmt, stmt.getNodeType()));
            }
        }
        // Task Element
        else if (node instanceof TaskElementNode) {
            TaskElementNode task = (TaskElementNode) node;
            children.add(new ASTNodeWrapper(null, "Statements: " + task.getActionStatements().size()));
            for (StatementNode stmt : task.getActionStatements()) {
                children.add(new ASTNodeWrapper(stmt, stmt.getNodeType()));
            }
        }
        // If Statement
        else if (node instanceof IfStatementNode) {
            IfStatementNode ifStmt = (IfStatementNode) node;
            children.add(new ASTNodeWrapper(ifStmt.getCondition(), "Condition"));
            children.add(new ASTNodeWrapper(ifStmt.getThenStatement(), "Then"));
            for (var elseIf : ifStmt.getElseIfClauses()) {
                children.add(new ASTNodeWrapper(elseIf, "ElseIf"));
            }
            if (ifStmt.getElseStatement() != null) {
                children.add(new ASTNodeWrapper(ifStmt.getElseStatement(), "Else"));
            }
        }
        // For Statement
        else if (node instanceof ForStatementNode) {
            ForStatementNode forStmt = (ForStatementNode) node;
            children.add(new ASTNodeWrapper(null, "Iterator: " + forStmt.getIteratorVariable()));
            children.add(new ASTNodeWrapper(forStmt.getIterable(), "Iterable"));
            children.add(new ASTNodeWrapper(forStmt.getBody(), "Body"));
        }
        // Try Statement
        else if (node instanceof TryStatementNode) {
            TryStatementNode tryStmt = (TryStatementNode) node;
            children.add(new ASTNodeWrapper(tryStmt.getTryBlock(), "TryBlock"));
            children.add(new ASTNodeWrapper(null, "CatchVar: " + tryStmt.getCatchVariable()));
            children.add(new ASTNodeWrapper(tryStmt.getCatchBlock(), "CatchBlock"));
        }
        // Return/Throw Statements
        else if (node instanceof ReturnStatementNode) {
            ReturnStatementNode ret = (ReturnStatementNode) node;
            if (ret.getExpression() != null) {
                children.add(new ASTNodeWrapper(ret.getExpression(), "Expression"));
            }
        }
        else if (node instanceof ThrowStatementNode) {
            ThrowStatementNode thr = (ThrowStatementNode) node;
            children.add(new ASTNodeWrapper(thr.getExpression(), "Expression"));
        }
        // Binary Expressions (check for simplified versions)
        else if (node instanceof MultiplicativeExpressionNode) {
            MultiplicativeExpressionNode mult = (MultiplicativeExpressionNode) node;
            if (!mult.isSingleOperand()) {
                for (int i = 0; i < mult.getOperands().size(); i++) {
                    String label = (i == 0) ? "Left" : "Right(" + mult.getOperators().get(i-1).getValue() + ")";
                    children.add(new ASTNodeWrapper(mult.getOperands().get(i), label));
                }
            } else {
                children.add(new ASTNodeWrapper(mult.getSingleOperand(), "Operand"));
            }
        }
        // Unary Expression
        else if (node instanceof UnaryExpressionNode) {
            UnaryExpressionNode unary = (UnaryExpressionNode) node;
            if (unary.getOperator() != null) {
                children.add(new ASTNodeWrapper(null, "Operator: " + unary.getOperator()));
            }
            children.add(new ASTNodeWrapper(unary.getOperand(), "Operand"));
        }
        // Ternary Expression
        else if (node instanceof TernaryExpressionNode) {
            TernaryExpressionNode tern = (TernaryExpressionNode) node;
            children.add(new ASTNodeWrapper(tern.getCondition(), "Condition"));
            if (tern.getTrueExpression() != null) {
                children.add(new ASTNodeWrapper(tern.getTrueExpression(), "True"));
                children.add(new ASTNodeWrapper(tern.getFalseExpression(), "False"));
            }
        }
        // Variable declarations
        else if (node instanceof VariableDeclarationStatementNode) {
            VariableDeclarationStatementNode varDecl = (VariableDeclarationStatementNode) node;
            children.add(new ASTNodeWrapper(null, "Name: " + varDecl.getVariableName()));
            children.add(new ASTNodeWrapper(varDecl.getInitializer(), "Initializer"));
        }
        // Import declarations
        else if (node instanceof ImportDeclarationNode) {
            ImportDeclarationNode imp = (ImportDeclarationNode) node;
            children.add(new ASTNodeWrapper(null, "Path: " + imp.getModulePath()));
            if (imp.getAlias() != null) {
                children.add(new ASTNodeWrapper(null, "Alias: " + imp.getAlias()));
            }
            children.add(new ASTNodeWrapper(null, "Type: " + (imp.isJarImport() ? "JAR" : "Module")));
        }

        return children;
    }

    private void expandAll(TreeItem<?> item) {
        if (item == null) return;
        item.setExpanded(true);
        for (TreeItem<?> child : item.getChildren()) {
            expandAll(child);
        }
    }

    private void collapseAll(TreeItem<?> item) {
        if (item == null) return;
        item.setExpanded(false);
        for (TreeItem<?> child : item.getChildren()) {
            collapseAll(child);
        }
    }

    public boolean isAutoUpdateEnabled() {
        return autoUpdateCheckBox.isSelected();
    }

    /**
     * Wrapper class for AST nodes in the tree view.
     */
    private static class ASTNodeWrapper {
        final ASTNode node;
        final String label;

        ASTNodeWrapper(ASTNode node, String label) {
            this.node = node;
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    /**
     * Custom tree cell for rendering AST nodes with icons and colors.
     */
    private static class ASTTreeCell extends TreeCell<ASTNodeWrapper> {
        @Override
        protected void updateItem(ASTNodeWrapper item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
                return;
            }

            setText(item.label);

            // Set icon based on node type
            Circle icon = new Circle(5);
            if (item.node == null) {
                icon.setFill(Color.GRAY);
            } else {
                String nodeType = item.node.getNodeType();
                if (nodeType.contains("Declaration")) {
                    icon.setFill(Color.BLUE);
                } else if (nodeType.contains("Statement")) {
                    icon.setFill(Color.GREEN);
                } else if (nodeType.contains("Expression")) {
                    icon.setFill(Color.ORANGE);
                } else if (nodeType.contains("Literal")) {
                    icon.setFill(Color.PURPLE);
                } else if (nodeType.contains("Process") || nodeType.contains("Task") || nodeType.contains("Gateway")) {
                    icon.setFill(Color.RED);
                } else {
                    icon.setFill(Color.DARKGRAY);
                }
            }

            setGraphic(icon);
        }
    }
}
