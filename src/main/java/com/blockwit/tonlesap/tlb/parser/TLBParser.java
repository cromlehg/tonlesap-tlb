package com.blockwit.tonlesap.tlb.parser;

import com.blockwit.tonlesap.tlb.model.TLB;
import com.blockwit.tonlesap.tlb.model.TLBType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TLBParser {

    public static final String LEX_UNKNOWN = "unknown";

    public static final String LEX_COMMENT = "comment";

    public static final String LEX_EXPR = "expression";

    public static final String identifiers = "abscdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789";

    public static final void updateNewLine(TLBParserContext ctx, char c) {
        if (c == '\n') {
            updateNewLine(ctx);
        }
    }

    public static final void updateNewLine(TLBParserContext ctx) {
        ctx.line++;
        ctx.index = 0;
    }

    public static final void replaceWhileNotUnknown(TLBParserContext ctx, String name, char value) {
        TLBLex lex = new TLBLex(-1, ctx.line, -1, ctx.index + 1, name, "" + value);

        TLBLex le = ctx.stack.lastElement();
        while (le.name.equals(LEX_UNKNOWN)) {
            lex.startLine = le.startLine;
            lex.startIndex = le.startIndex;
            lex.value = le.value + lex.value;
            ctx.stack.pop();
            if(ctx.stack.isEmpty())
                break;
            le = ctx.stack.lastElement();
        }

        ctx.stack.push(lex);
    }

    public static final void replace(TLBParserContext ctx, String name, char value) {
        TLBLex lexprev = ctx.stack.pop();
        TLBLex lex = new TLBLex(lexprev.startLine, ctx.line, lexprev.startIndex, ctx.index + 1, name,
                lexprev.value + value);
        ctx.stack.push(lex);
    }

    public static final void push(TLBParserContext ctx, String name, char value) {
        TLBLex lex = new TLBLex(ctx.line, ctx.line, ctx.index, ctx.index + 1, name, value + "");
        ctx.stack.push(lex);
    }

    public static final void appendToLast(TLBParserContext ctx, char value) {
        TLBLex lex = ctx.stack.lastElement();
        lex.value += value;
        lex.endLine = ctx.line;
        lex.endIndex = ctx.index + 1;
    }

    public static final void pushUnknown(TLBParserContext ctx, char c) {
        TLBLex lex = new TLBLex(ctx.line, ctx.line, ctx.index, ctx.index + 1, LEX_UNKNOWN, "" + c);
        ctx.stack.push(lex);
    }



    public static final TLBParserContext parse(String content) {
        TLBParserContext ctx = new TLBParserContext();
        parse(ctx, content);
        return ctx;
    }
    public static final void parse(TLBParserContext ctx, String content) {
        char[] chars = content.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (ctx.stack.isEmpty()) {
                pushUnknown(ctx, c);
                updateNewLine(ctx, c);
            } else {
                TLBLex le = ctx.stack.lastElement();
                if (le.name.equals(LEX_COMMENT)) {
                    if (c == '\n') {
                        pushUnknown(ctx, c);
                        updateNewLine(ctx);
                    } else {
                        appendToLast(ctx, c);
                    }
                } else if(c == ';') {
                    replaceWhileNotUnknown(ctx, LEX_EXPR, ';');
                } else {
                    if (c == '/') {
                        if (le.name.equals(LEX_UNKNOWN) && le.value.equals("/")) {
                            replace(ctx, LEX_COMMENT, '/');
                        } else {
                            pushUnknown(ctx, c);
                        }
                    } else if (c == '\n') {
                        pushUnknown(ctx, c);
                        updateNewLine(ctx);
                    } else {
                        pushUnknown(ctx, c);
                    }
                }
            }

            ctx.index++;

        }

        Iterator<TLBLex> iter = ctx.stack.iterator();
        List<TLBLex> exprs = new ArrayList<TLBLex>();
        while (iter.hasNext()) {
            TLBLex lex = iter.next();
            if (lex.name.equals(LEX_EXPR)) {
                exprs.add(lex);
            }
        }

        ctx.tlb = new TLB();

        for(TLBLex expr : exprs) {
            int idx = expr.value.lastIndexOf('=');
            String name = expr.value.substring(idx + 1, expr.value.length() - 1).trim();
            TLBType tlbType = ctx.tlb.tlbTypesMap.get(name);
            if(tlbType == null) {
                tlbType = new TLBType(name);
                ctx.tlb.tlbTypes.add(tlbType);
                ctx.tlb.tlbTypesMap.put(name, tlbType);
            }
            tlbType.lexes.add(expr);
        }

    }

}

