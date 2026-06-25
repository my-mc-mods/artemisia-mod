package dev.aika.artemisia.expression;

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.config.ExpressionConfiguration;
import com.ezylang.evalex.functions.FunctionIfc;
import com.ezylang.evalex.operators.OperatorIfc;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.aika.artemisia.expression.functions.LengthFunction;
import dev.aika.artemisia.expression.functions.RandomArbitraryFunction;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.Map;

@SuppressWarnings("unused")
@UtilityClass
public class ExpressionAPI {
    public ExpressionManager createManager(long maxSize, Duration duration) {
        return new ExpressionManager(maxSize, duration);
    }

    public static class ExpressionManager {
        @Getter
        private final ExpressionConfiguration config = ExpressionConfiguration.builder().build();
        private final Cache<String, Expression> cache;

        public ExpressionManager(long maxSize, Duration duration) {
            this.cache = CacheBuilder.newBuilder()
                    .maximumSize(maxSize).expireAfterAccess(duration)
                    .build();

            this.registerFunction("LENGTH", new LengthFunction())
                    .registerFunction("LEN", new LengthFunction());
            this.registerFunction("RANDOMA", new RandomArbitraryFunction());
        }

        public ExpressionManager registerFunction(String name, FunctionIfc function) {
            config.withAdditionalFunctions(Map.entry(name, function));
            return this;
        }

        public ExpressionManager registerOperator(String name, OperatorIfc operator) {
            config.withAdditionalOperators(Map.entry(name, operator));
            return this;
        }

        @SneakyThrows
        public Expression create(String expressionString) {
            return cache.get(expressionString, () -> {
                var expr = new Expression(expressionString, config);
                expr.validate();
                return expr;
            }).copy();
        }
    }
}