package com.blockwit.tonlesap.tlb.parser;

import com.blockwit.tonlesap.tlb.model.TLB;

import java.util.Collections;
import java.util.Iterator;

public class TLBParser {

    public static final String LEX_FIELD = "field";

    public static final String LEX_HEX_VALUE = "hex_value";

    public static final String LEX_DECIMAL_VALUE = "decimal_value";

    public static final String LEX_BIN_VALUE = "bin_value";

    public static final String LEX_HEX = "hex";

    public static final String LEX_BIN = "bin";

    public static final String LEX_DIGIT = "digit";

    public static final String LEX_COMMA = "comma";

    public static final String LEX_COLON = "colon";

    public static final String LEX_EQUALS = "equals";

    public static final String LEX_DOLLAR = "dollar";

    public static final String LEX_GRID = "grid";

    public static final String LEX_UNKNOWN = "unknown";

    public static final String LEX_COMMENT = "comment";

    public static final String LEX_TILDA = "tilda";

    public static final String LEX_EXPRESSION_START = "expr_start";

    public static final String LEX_EXPRESSION_END = "expr_end";

    public static final String LEX_EXPRESSION = "expr";

    public static final String LEX_TYPE_EXPRESSION_START = "type_expr_start";

    public static final String LEX_TYPE_EXPRESSION_END = "type_expr_end";

    public static final String LEX_TYPE_EXPRESSION = "type_expr";

    public static final String LEX_TYPE_CONSTRUCTOR_BODY = "type_constructor_body";

    public static final String LEX_TYPE_CONSTRUCTOR = "type_constructor";

    public static final String LEX_TILDA_IDENTIFIER = "tilda_id";

    public static final String LEX_TILDA_EXPR = "tilda_expr";

    public static final String LEX_TYPE_CONSTRUCTOR_HEADER = "type_constructor_header";

    public static final String LEX_IDENTIFIER = "identifier";

    public static final String LEX_SLASH = "slash";

    public static final String LEX_SPACE = "space";

    public static final String LEX_UNDERSCORE = "underscore";

    public static final String identifier_symbols_start = "abscdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String identifier_symbols = "abscdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789";

    public static final String bin = "01";

    public static final String digits = bin + "23456789";

    public static final String hex = digits + "abcdefABCDEF";

    public static final String spaces = "\t\n ";

    public static final void updateNewLine(TLBParserContext ctx) {
        ctx.line++;
        ctx.index = 0;
    }

    public static final void replaceTwo(TLBParserContext ctx, String name) {
        TLBLex top = ctx.stack.pop();
        TLBLex prev = ctx.stack.pop();
        TLBLex lex = new TLBLex(prev.startLine, top.endLine, prev.startIndex, prev.endIndex, name,
                prev.value + top.value);
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

    public static final TLBParserContext parse(String content) {
        TLBParserContext ctx = new TLBParserContext();
        parse(ctx, content);
        return ctx;
    }

    public static void checkLex(TLBParserContext ctx, char c) {
        if (ctx.stack.empty()) {
            checkLexNotInContext(ctx, c);
        } else {
            TLBLex prev = ctx.stack.lastElement();
            if (prev.name.equals(LEX_SPACE)) {
                if (spaces.indexOf(c) >= 0) {
                    appendToLast(ctx, c);
                } else {
                    checkLexNotInContext(ctx, c);
                }
            } else if (prev.name.equals(LEX_COMMENT)) {
                if (c == '\n') {
                    push(ctx, LEX_SPACE, c);
                } else {
                    appendToLast(ctx, c);
                }
            } else if (prev.name.equals(LEX_IDENTIFIER)) {
                if (identifier_symbols.indexOf(c) >= 0) {
                    appendToLast(ctx, c);
                } else {
                    checkLexNotInContext(ctx, c);
                }
            } else if (prev.name.equals(LEX_SLASH)) {
                if (c == '/') {
                    replace(ctx, LEX_COMMENT, c);
                } else {
                    checkLexNotInContext(ctx, c);
                }
            } else {
                checkLexNotInContext(ctx, c);
            }
        }
    }

    public static void checkLexNotInContext(TLBParserContext ctx, char c) {
        if (identifier_symbols_start.indexOf(c) >= 0) {
            push(ctx, LEX_IDENTIFIER, c);
        } else if (c == '{') {
            push(ctx, LEX_TYPE_EXPRESSION_START, c);
        } else if (c == '}') {
            push(ctx, LEX_TYPE_EXPRESSION_END, c);
        } else if (c == '/') {
            push(ctx, LEX_SLASH, c);
        } else if (spaces.indexOf(c) >= 0) {
            push(ctx, LEX_SPACE, c);
        } else if (c == '_') {
            push(ctx, LEX_UNDERSCORE, c);
        } else if (c == '$') {
            push(ctx, LEX_DOLLAR, c);
        } else if (c == ':') {
            push(ctx, LEX_COLON, c);
        } else if (c == '#') {
            push(ctx, LEX_GRID, c);
        } else if (c == ':') {
            push(ctx, LEX_COLON, c);
        } else if (c == '=') {
            push(ctx, LEX_EQUALS, c);
        } else if (c == '~') {
            push(ctx, LEX_TILDA, c);
        } else if (c == '(') {
            push(ctx, LEX_EXPRESSION_START, c);
        } else if (c == ')') {
            push(ctx, LEX_EXPRESSION_END, c);
        } else if (c == '{') {
            push(ctx, LEX_TYPE_EXPRESSION_START, c);
        } else if (c == '}') {
            push(ctx, LEX_TYPE_EXPRESSION_END, c);
        } else if (c == ';') {
            push(ctx, LEX_COMMA, c);
        } else if (bin.indexOf(c) >= 0) {
            push(ctx, LEX_BIN, c);
        } else if (digits.indexOf(c) >= 0) {
            push(ctx, LEX_DIGIT, c);
        } else if (hex.indexOf(c) >= 0) {
            push(ctx, LEX_HEX, c);
        } else {
            push(ctx, LEX_UNKNOWN, c);
            //ctx.error = "line: " + ctx.line + ", position: " + ctx.index + " - unexpected character: " + c;
        }
        checkPrev(ctx);
    }

    public static boolean checkPrev(TLBParserContext ctx) {
        if (ctx.stack.size() >= 2) {

            TLBLex top = ctx.stack.lastElement();
            TLBLex prev = ctx.stack.get(ctx.stack.size() - 2);

            if (prev.name.equals(LEX_IDENTIFIER)) {
                if (ctx.stack.size() >= 3) {
                    TLBLex prevPrev = ctx.stack.get(ctx.stack.size() - 3);
                    if (prevPrev.name.equals(LEX_TILDA)) {

                        TLBLex lex = new TLBLex(LEX_TILDA_IDENTIFIER);
                        lex.addChild(prevPrev);
                        lex.addChild(prev);
                        ctx.stack.pop();
                        ctx.stack.pop();
                        ctx.stack.pop();
                        ctx.stack.push(lex);
                        ctx.stack.push(top);
                        checkPrev(ctx);

                    }
                }
            }

            if (top.name.equals(LEX_IDENTIFIER)) {
                checkTypedField(ctx);
            }
            if (top.name.equals(LEX_EXPRESSION)) {
                if (prev.name.equals(LEX_TILDA)) {
                    TLBLex lex = new TLBLex(LEX_TILDA_EXPR);
                    lex.addChild(prev);
                    lex.addChild(top);
                    ctx.stack.pop();
                    ctx.stack.pop();
                    ctx.stack.push(lex);
                    checkPrev(ctx);
                } else {
                    checkTypedField(ctx);
                }
            } else if (top.name.equals(LEX_TYPE_EXPRESSION_END)) {

                TLBLex newLex = new TLBLex(LEX_TYPE_EXPRESSION);

                for (int i = 2; i <= ctx.stack.size(); i++) {
                    TLBLex lexNext = ctx.stack.get(ctx.stack.size() - i);
                    newLex.addChild(lexNext);
                    if (lexNext.name.equals(LEX_TYPE_EXPRESSION_START)) {

                        Collections.reverse(newLex.childs);
                        newLex.addChild(top);
                        for (int j = 0; j < i; j++) {
                            ctx.stack.pop();
                        }
                        ctx.stack.push(newLex);

                        checkPrev(ctx);
                        break;
                    }
                }


            } else if (top.name.equals(LEX_EXPRESSION_END)) {

                TLBLex newLex = new TLBLex(LEX_EXPRESSION);

                for (int i = 2; i <= ctx.stack.size(); i++) {
                    TLBLex lexNext = ctx.stack.get(ctx.stack.size() - i);
                    newLex.addChild(lexNext);
                    if (lexNext.name.equals(LEX_EXPRESSION_START)) {

                        Collections.reverse(newLex.childs);
                        newLex.addChild(top);
                        for (int j = 0; j < i; j++) {
                            ctx.stack.pop();
                        }
                        ctx.stack.push(newLex);

                        checkPrev(ctx);
                        break;
                    }
                }

            } else if (top.name.equals(LEX_BIN)) {
                if (prev.name.equals(LEX_BIN)) {
                    replaceTwo(ctx, LEX_BIN_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_BIN_VALUE)) {
                    replaceTwo(ctx, LEX_BIN_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_DIGIT)) {
                    replaceTwo(ctx, LEX_DECIMAL_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_DECIMAL_VALUE)) {
                    replaceTwo(ctx, LEX_DECIMAL_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_HEX)) {
                    replaceTwo(ctx, LEX_HEX_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_HEX_VALUE)) {
                    replaceTwo(ctx, LEX_HEX_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_DOLLAR)) {
                    replaceIfPrevPrevIdentifier(ctx);
                }
            } else if (top.name.equals(LEX_DIGIT)) {
                if (prev.name.equals(LEX_BIN)) {
                    replaceTwo(ctx, LEX_DECIMAL_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_BIN_VALUE)) {
                    replaceTwo(ctx, LEX_DECIMAL_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_DIGIT)) {
                    replaceTwo(ctx, LEX_DECIMAL_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_DECIMAL_VALUE)) {
                    replaceTwo(ctx, LEX_DECIMAL_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_HEX)) {
                    replaceTwo(ctx, LEX_HEX_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_HEX_VALUE)) {
                    replaceTwo(ctx, LEX_HEX_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_GRID)) {
                    replaceIfPrevPrevIdentifier(ctx);
                }
            } else if (top.name.equals(LEX_HEX)) {
                if (prev.name.equals(LEX_BIN)) {
                    replaceTwo(ctx, LEX_HEX_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_BIN_VALUE)) {
                    replaceTwo(ctx, LEX_HEX_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_DIGIT)) {
                    replaceTwo(ctx, LEX_HEX_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_DECIMAL_VALUE)) {
                    replaceTwo(ctx, LEX_HEX_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_HEX)) {
                    replaceTwo(ctx, LEX_HEX_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_HEX_VALUE)) {
                    replaceTwo(ctx, LEX_HEX_VALUE);
                    checkPrev(ctx);
                } else if (prev.name.equals(LEX_GRID)) {
                    replaceIfPrevPrevIdentifier(ctx);
                }
            } else if (top.name.equals(LEX_BIN_VALUE)) {
                if (prev.name.equals(LEX_DOLLAR)) {
                    replaceIfPrevPrevIdentifier(ctx);
                }
            } else if (top.name.equals(LEX_DECIMAL_VALUE) || top.equals(LEX_HEX_VALUE)) {
                if (prev.name.equals(LEX_GRID)) {
                    replaceIfPrevPrevIdentifier(ctx);
                }
            } else if (top.name.equals(LEX_UNDERSCORE)) {
                if (prev.name.equals(LEX_DOLLAR)) {
                    replaceIfPrevPrevIdentifier(ctx);
                }

            } else if (top.name.equals(LEX_TYPE_CONSTRUCTOR_HEADER)) {
                if (prev.name.equals(LEX_EQUALS)) {
                    TLBLex prevPrev = ctx.stack.get(ctx.stack.size() - 3);
                    if (prevPrev.name.equals(LEX_TYPE_CONSTRUCTOR_BODY)) {
                        TLBLex lex = new TLBLex(LEX_TYPE_CONSTRUCTOR);
                        lex.addChild(prevPrev);
                        lex.addChild(ctx.stack.get(ctx.stack.size() - 2));
                        lex.addChild(ctx.stack.get(ctx.stack.size() - 1));
                        ctx.stack.pop();
                        ctx.stack.pop();
                        ctx.stack.pop();
                        ctx.stack.push(lex);
                        checkPrev(ctx);
                    }
                }

            } else if (top.name.equals(LEX_COMMA)) {

                TLBLex newLex = new TLBLex(LEX_TYPE_CONSTRUCTOR_HEADER);

                for (int i = 2; i <= ctx.stack.size(); i++) {
                    TLBLex lexNext = ctx.stack.get(ctx.stack.size() - i);
                    if (lexNext.name.equals(LEX_SPACE) ||
                            lexNext.name.equals(LEX_COMMENT) ||
                            lexNext.name.equals(LEX_IDENTIFIER) ||
                            lexNext.name.equals(LEX_TILDA_EXPR) ||
                            lexNext.name.equals(LEX_EXPRESSION) ||
                            lexNext.name.equals(LEX_DECIMAL_VALUE) ||
                            lexNext.name.equals(LEX_DIGIT) ||
                            lexNext.name.equals(LEX_BIN) ||
                            lexNext.name.equals(LEX_BIN_VALUE) ||
                            lexNext.name.equals(LEX_HEX) ||
                            lexNext.name.equals(LEX_HEX_VALUE) ||
                            lexNext.name.equals(LEX_TILDA_IDENTIFIER)) {
                        newLex.addChild(lexNext);
                    } else if (lexNext.name.equals(LEX_EQUALS)) {
                        i = i + 1;
                        lexNext = ctx.stack.get(ctx.stack.size() - i);

                        if (lexNext.name.equals(LEX_TYPE_CONSTRUCTOR_BODY)) {

                            Collections.reverse(newLex.childs);
                            newLex.addChild(top);
                            for (int j = 0; j < i - 2; j++) {
                                ctx.stack.pop();
                            }
                            ctx.stack.push(newLex);
                        }
                        checkPrev(ctx);

                        break;
                    } else {
                        break;
                    }
                }


            } else if (top.name.equals(LEX_EQUALS)) {
                // try to find fields before: COMA OR COMMENT OR NOTHING : FIELDS+ SPACE*
                // pass spaces
                TLBLex newLex = new TLBLex(LEX_TYPE_CONSTRUCTOR_BODY);

                for (int i = 2; i <= ctx.stack.size(); i++) {
                    TLBLex lexNext = ctx.stack.get(ctx.stack.size() - i);
                    if (lexNext.name.equals(LEX_SPACE) || lexNext.name.equals(LEX_COMMENT) ||
                            lexNext.name.equals(LEX_FIELD) || lexNext.name.equals(LEX_UNDERSCORE) ||
                            lexNext.name.equals(LEX_TYPE_EXPRESSION)) {
                        newLex.addChild(lexNext);

                        if (i == ctx.stack.size()) {
                            Collections.reverse(newLex.childs);
                            for (int j = 0; j < i; j++) {
                                ctx.stack.pop();
                            }
                            ctx.stack.push(newLex);
                            ctx.stack.push(top);
                        }

                    } else if (lexNext.name.equals(LEX_TYPE_CONSTRUCTOR) || i == ctx.stack.size()) {

                        Collections.reverse(newLex.childs);
                        for (int j = 0; j < i - 1; j++) {
                            ctx.stack.pop();
                        }
                        ctx.stack.push(newLex);
                        ctx.stack.push(top);

                        break;
                    } else {
                        break;
                    }
                }


            }

            return true;
        } else
            return false;
    }

    public static void checkTypedField(TLBParserContext ctx) {
        TLBLex top = ctx.stack.lastElement();
        TLBLex newLex = new TLBLex(LEX_FIELD);

        boolean passColon = false;
        for (int i = 2; i <= ctx.stack.size(); i++) {
            TLBLex lexNext = ctx.stack.get(ctx.stack.size() - i);

            if (passColon) {
                if (lexNext.name.equals(LEX_SPACE) || lexNext.name.equals(LEX_COMMENT)) {
                    newLex.addChild(lexNext);
                } else if (lexNext.name.equals(LEX_IDENTIFIER)) {
                    newLex.addChild(lexNext);

                    Collections.reverse(newLex.childs);
                    newLex.addChild(top);
                    for (int j = 0; j < i; j++) {
                        ctx.stack.pop();
                    }
                    ctx.stack.push(newLex);
                    checkPrev(ctx);

                } else {
                    break;
                }
            } else {
                if (lexNext.name.equals(LEX_SPACE) || lexNext.name.equals(LEX_COMMENT)) {
                    newLex.addChild(lexNext);
                } else if (lexNext.name.equals(LEX_COLON)) {
                    passColon = true;
                    newLex.addChild(lexNext);
                } else {
                    break;
                }
            }

        }
    }

    public static void replaceIfPrevPrevIdentifier(TLBParserContext ctx) {
        TLBLex prevPrev = ctx.stack.get(ctx.stack.size() - 3);
        if (prevPrev.name.equals(LEX_IDENTIFIER)) {
            TLBLex lex = new TLBLex(LEX_FIELD);
            lex.addChild(prevPrev);
            lex.addChild(ctx.stack.get(ctx.stack.size() - 2));
            lex.addChild(ctx.stack.get(ctx.stack.size() - 1));
            ctx.stack.pop();
            ctx.stack.pop();
            ctx.stack.pop();
            ctx.stack.push(lex);
            checkPrev(ctx);
        }
    }

    public static final void parse(TLBParserContext ctx, String content) {
        char[] chars = content.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            checkLex(ctx, c);
            if (ctx.error != null)
                break;

            if (c == '\n') {
                ctx.line++;
                ctx.index = 0;
            } else {
                ctx.index++;
            }
        }

        removeSpacesAndComments(ctx);
        TLBLexesToModelConverter.convert(ctx);
    }

    public static final void removeSpacesAndComments(TLBParserContext ctx) {
        if (ctx.error == null) {
            Iterator<TLBLex> iter = ctx.stack.iterator();
            while (iter.hasNext()) {
                TLBLex lex = iter.next();
                if (lex.name.equals(LEX_COMMENT) || lex.name.equals(LEX_SPACE))
                    iter.remove();
                else
                    removeSpacesInner(lex);
            }
        }
    }

    public static final void removeSpacesInner(TLBLex curLex) {
        if (!curLex.childs.isEmpty()) {
            Iterator<TLBLex> iter = curLex.childs.iterator();
            while (iter.hasNext()) {
                TLBLex lex = iter.next();
                if (lex.name.equals(LEX_COMMENT) || lex.name.equals(LEX_SPACE))
                    iter.remove();
                else
                    removeSpacesInner(lex);
            }
        }
    }


}

