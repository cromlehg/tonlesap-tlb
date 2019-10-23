package com.blockwit.tonlesap.tlb.parser.tests;

import com.blockwit.tonlesap.tlb.model.TLBType;
import com.blockwit.tonlesap.tlb.parser.TLBLex;
import com.blockwit.tonlesap.tlb.parser.TLBParser;
import com.blockwit.tonlesap.tlb.parser.TLBParserContext;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class ParserTest {

    public static void test(String path) throws IOException {
        ClassLoader classLoader = ParseSmallTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        TLBParserContext ctx = TLBParser.parse(content);
        if (ctx.error != null) {
            System.out.println(ctx.error);
        } else {
            System.out.println("### Parser lex analyzer: ###");
            for (TLBLex lex : ctx.stack) {
                System.out.println(lex);
            }

            System.out.println("### Parser grammar analyzer: ###");
            System.out.println(ctx.tlb);
        }
    }

}
