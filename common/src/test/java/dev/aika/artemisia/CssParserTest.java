package dev.aika.artemisia;

import com.osbcp.cssparser.CSSParser;
import org.junit.jupiter.api.Test;

public class CssParserTest {
    @Test
    public void styleTest() throws Exception {
        var rules = CSSParser.parse("""
                style {
                    size: 100 200;
                    padding: 20;
                    display: flex;
                    flex-direction: row;
                    justify-content: center;
                    align-items: end;
                    gap: 4;
                }
                """);
        rules.getFirst().getPropertyValues().forEach(value -> {
            System.out.printf("-> Property: %s\n", value.getProperty());
            System.out.printf("   Value: %s\n", value.getValue());
        });
    }
}