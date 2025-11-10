package com.flowscript.semantic.errors;

import java.util.*;

public class SemanticAnalysisResult {
    private final List<SemanticError> errors;
    private final List<SemanticWarning> warnings;

    public SemanticAnalysisResult() {
        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();
    }

    public SemanticAnalysisResult(List<SemanticError> errors, List<SemanticWarning> warnings) {
        this.errors = new ArrayList<>(errors);
        this.warnings = new ArrayList<>(warnings);
    }

    public void addError(SemanticError error) {
        errors.add(error);
    }

    public void addWarning(SemanticWarning warning) {
        warnings.add(warning);
    }

    public List<SemanticError> getErrors() {
        return new ArrayList<>(errors);
    }

    public List<SemanticWarning> getWarnings() {
        return new ArrayList<>(warnings);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }

    public int getErrorCount() {
        return errors.size();
    }

    public int getWarningCount() {
        return warnings.size();
    }

    public boolean isSuccess() {
        return errors.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Semantic Analysis Result:\n");
        sb.append("  Errors: ").append(errors.size()).append("\n");
        sb.append("  Warnings: ").append(warnings.size()).append("\n");

        if (!errors.isEmpty()) {
            sb.append("\nErrors:\n");
            for (SemanticError error : errors) {
                sb.append("  ").append(error).append("\n");
            }
        }

        if (!warnings.isEmpty()) {
            sb.append("\nWarnings:\n");
            for (SemanticWarning warning : warnings) {
                sb.append("  ").append(warning).append("\n");
            }
        }

        return sb.toString();
    }
}
