package dev.aika.artemisia.expression.functions;

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.functions.AbstractFunction;
import com.ezylang.evalex.functions.FunctionParameter;
import com.ezylang.evalex.parser.Token;

import java.math.BigDecimal;
import java.security.SecureRandom;

// 两数之间的随机数 result >= min && result < max
@FunctionParameter(name = "min")
@FunctionParameter(name = "max")
public class RandomArbitraryFunction extends AbstractFunction {
    @Override
    public EvaluationValue evaluate(
            Expression expression, Token functionToken, EvaluationValue... parameterValues
    ) {
        final SecureRandom secureRandom = new SecureRandom();
        final BigDecimal min = parameterValues[0].getNumberValue();
        final BigDecimal max = parameterValues[1].getNumberValue();
        return expression.convertValue(new BigDecimal(secureRandom.nextDouble())
                .multiply(max.subtract(min))
                .add(min));
    }
}