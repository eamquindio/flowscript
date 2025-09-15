package edu.eam.ingesoft.flowscript.functions;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class FlowScriptFunctionTest {

    @Test
    public void testSimpleSumFunction() {
        Parameter a = new Parameter("a", "integer");
        Parameter b = new Parameter("b", "integer");
        FlowScriptFunction sumFunction = new FlowScriptFunction(
            "add",
            Arrays.asList(a, b),
            "integer",
            Collections.singletonList(
                new ReturnStatement(
                    new BinaryExpression(
                        new VariableReferenceExpression("a"),
                        "+",
                        new VariableReferenceExpression("b")
                    )
                )
            )
        );
        FunctionInterpreter interpreter = new FunctionInterpreter();
        Object result = interpreter.execute(sumFunction, Arrays.asList(2, 3));
        assertEquals(5, result);
    }

    @Test
    public void testFactorialFunctionBaseCase() {
        Parameter n = new Parameter("n", "integer");
        FlowScriptFunction factorialFunction = new FlowScriptFunction(
            "factorial",
            Arrays.asList(n),
            "integer",
            Arrays.asList(
                new IfStatement(
                    new BinaryExpression(
                        new VariableReferenceExpression("n"),
                        "<=",
                        new LiteralExpression(1)
                    ),
                    Collections.singletonList(
                        new ReturnStatement(new LiteralExpression(1))
                    ),
                    Collections.emptyList()
                ),
                new ReturnStatement(
                    new BinaryExpression(
                        new VariableReferenceExpression("n"),
                        "*",
                        new FunctionCallExpression("factorial", Arrays.asList(
                            new BinaryExpression(
                                new VariableReferenceExpression("n"),
                                "-",
                                new LiteralExpression(1)
                            )
                        ))
                    )
                )
            )
        );
        FunctionInterpreter interpreter = new FunctionInterpreter();
        Object result = interpreter.execute(factorialFunction, Arrays.asList(1));
        assertEquals(1, result);
    }
}
