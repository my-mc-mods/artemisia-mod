package dev.aika.artemisia.expression.functions;

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.functions.AbstractFunction;
import com.ezylang.evalex.functions.FunctionParameter;
import com.ezylang.evalex.parser.Token;
import dev.aika.artemisia.api.PlatformAPI;

@SuppressWarnings("unused")
@FunctionParameter(name = "version")
@FunctionParameter(name = "versionRange")
public class ContainsVersionFunction extends AbstractFunction {
    @Override
    public EvaluationValue evaluate(
            Expression expression, Token functionToken, EvaluationValue... parameterValues
    ) {
        return EvaluationValue.booleanValue(
                PlatformAPI.isContainsVersion(
                        parameterValues[0].getStringValue(), parameterValues[1].getStringValue()
                )
        );
    }
}