package dev.aika.artemisia.expression.functions;

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.functions.AbstractFunction;
import com.ezylang.evalex.functions.FunctionParameter;
import com.ezylang.evalex.parser.Token;

@FunctionParameter(name = "value")
public class LengthFunction extends AbstractFunction {
    @Override
    public EvaluationValue evaluate(
            Expression expression, Token functionToken, EvaluationValue... parameterValues
    ) {
        final var param = parameterValues[0];
        if (param.isArrayValue()) return expression.convertValue(param.getArrayValue().size());
        else if (param.isStringValue()) return expression.convertValue(param.getStringValue().length());
        else return EvaluationValue.UNDEFINED;
    }
}