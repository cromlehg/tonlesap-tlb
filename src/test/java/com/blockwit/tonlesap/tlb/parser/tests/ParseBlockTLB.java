package com.blockwit.tonlesap.tlb.parser.tests;

import com.blockwit.tonlesap.tlb.model.TLBType;
import com.blockwit.tonlesap.tlb.parser.TLBParser;
import com.blockwit.tonlesap.tlb.parser.TLBParserContext;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ParseBlockTLB {

    public static void main(String[] args) throws IOException {
        String path = "Block.tlb";
        ClassLoader classLoader = ParseBlockTLB.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        TLBParserContext ctx = TLBParser.parse(content);
        for (TLBType tlbType : ctx.tlb.tlbTypes) {
            System.out.println(tlbType + "\n");
        }
    }

}
