package dev.aika.artemisia.expression;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionAPITest {
    ExpressionAPI.ExpressionManager manager = ExpressionAPI.createManager(200, Duration.ofMinutes(30));

    @Test
    void testLengthFunction() throws EvaluationException, ParseException {
        final var tests = List.of("hello", "hello world", "ExpressionFactory");

        for (String str : tests) {
            assertEquals(
                    str.length(),
                    manager.create("LENGTH(str)").with("str", str)
                            .evaluate().getNumberValue().intValue()
            );
            assertEquals(
                    str.length(),
                    manager.create("LEN(str)").with("str", str)
                            .evaluate().getNumberValue().intValue()
            );
        }
    }

    @Test
    void testRandomArbitraryFunction() throws EvaluationException, ParseException {
        final List<Map.Entry<Double, Double>> tests = List.of(
                Map.entry(1.0, 100.0), Map.entry(3.14, 2345.67)
        );

        for (Map.Entry<Double, Double> entry : tests) {
            final var result = manager.create("RANDOMA(min,max)")
                    .with("min", entry.getKey()).with("max", entry.getValue())
                    .evaluate().getNumberValue();
            assertTrue(result.compareTo(new BigDecimal(entry.getKey())) > 0);
            assertTrue(result.compareTo(new BigDecimal(entry.getValue())) < 0);
        }
    }
}