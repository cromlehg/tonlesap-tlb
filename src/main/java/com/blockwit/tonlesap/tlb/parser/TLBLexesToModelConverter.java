package com.blockwit.tonlesap.tlb.parser;

import com.blockwit.tonlesap.tlb.Utils;
import com.blockwit.tonlesap.tlb.model.*;

public class TLBLexesToModelConverter {

    public static void convert(TLBParserContext ctx) {
        if (ctx.error == null) {
            for (TLBLex lex : ctx.stack) {
                if (ctx.error == null) {
                    if (lex.name.equals(TLBParser.LEX_TYPE_CONSTRUCTOR)) {
                        convertTLBType(ctx, lex);
                    } else {
                        ctx.error = "Grammar: Wrong lexem in stack : " + lex.name;
                        break;
                    }
                } else break;
            }
        }
    }

    public static void convertTLBType(TLBParserContext ctx, TLBLex lex) {
        if (lex.name.equals(TLBParser.LEX_TYPE_CONSTRUCTOR)) {
            if (lex.childs.size() != 3) {
                ctx.error = "Grammar: Wrong lexem size in stack for TLBType : " + lex;
            } else {
                TLBLex bodyLex = lex.childs.get(0);
                if (bodyLex.name.equals(TLBParser.LEX_TYPE_CONSTRUCTOR_BODY)) {

                    TLBLex headerLex = lex.childs.get(2);
                    if (headerLex.name.equals(TLBParser.LEX_TYPE_CONSTRUCTOR_HEADER)) {

                        if (headerLex.childs.size() >= 1) {
                            TLBLex lexName = headerLex.childs.get(0);

                            if (lexName.name.equals(TLBParser.LEX_IDENTIFIER)) {

                                TLBType tlbType = ctx.tlb.tlbTypesMap.get(lexName.value);
                                if (tlbType == null) {
                                    tlbType = new TLBType(lexName.value);
                                    tlbType.lexems.add(lex);
                                    ctx.tlb.tlbTypesMap.put(lexName.value, tlbType);
                                    ctx.tlb.tlbTypes.add(tlbType);
                                }


                                TLBSubtype subtype = new TLBSubtype(tlbType);
                                subtype.lexems.add(lex);
                                tlbType.subtypes.add(subtype);


                                for (TLBLex bodyInLex : bodyLex.childs) {

                                    boolean withNotOperator = false;

                                    if (bodyInLex.name.equals(TLBParser.LEX_NOT_FIELD)) {
                                        withNotOperator = true;
                                        bodyInLex = bodyInLex.childs.get(1);
                                    }

                                    if (bodyInLex.name.equals(TLBParser.LEX_FIELD)) {
                                        if (bodyInLex.childs.get(1).name.equals(TLBParser.LEX_DOLLAR)) {
                                            if (bodyInLex.childs.get(2).name.equals(TLBParser.LEX_UNDERSCORE)) {
                                                TLBTypeBodyPartFieldBinUndetermined field = new TLBTypeBodyPartFieldBinUndetermined(subtype, bodyInLex.childs.get(0).value, withNotOperator);
                                                subtype.bodyParts.add(field);
                                            } else if (bodyInLex.childs.get(2).name.equals(TLBParser.LEX_BIN) || bodyInLex.childs.get(2).name.equals(TLBParser.LEX_BIN_VALUE)) {
                                                TLBTypeBodyPartFieldBin field = new TLBTypeBodyPartFieldBin(subtype, bodyInLex.childs.get(0).value, withNotOperator, new CustomBitSet(bodyInLex.childs.get(2).value));
                                                subtype.bodyParts.add(field);
                                            } else {
                                                ctx.error = "Grammar: unknown expression in binary field : " + bodyInLex.childs.get(2);
                                                break;
                                            }
                                        } else if (bodyInLex.childs.get(1).name.equals(TLBParser.LEX_GRID)) {
                                            if (bodyInLex.childs.get(2).name.equals(TLBParser.LEX_UNDERSCORE)) {
                                                TLBTypeBodyPartFieldHexUndetermined field = new TLBTypeBodyPartFieldHexUndetermined(subtype, bodyInLex.childs.get(0).value, withNotOperator);
                                                subtype.bodyParts.add(field);
                                            } else if (bodyInLex.childs.get(2).name.equals(TLBParser.LEX_BIN) ||
                                                    bodyInLex.childs.get(2).name.equals(TLBParser.LEX_BIN_VALUE) ||
                                                    bodyInLex.childs.get(2).name.equals(TLBParser.LEX_DIGIT) ||
                                                    bodyInLex.childs.get(2).name.equals(TLBParser.LEX_DECIMAL_VALUE) ||
                                                    bodyInLex.childs.get(2).name.equals(TLBParser.LEX_HEX) ||
                                                    bodyInLex.childs.get(2).name.equals(TLBParser.LEX_HEX_VALUE)) {
                                                TLBTypeBodyPartFieldHex field = new TLBTypeBodyPartFieldHex(subtype, bodyInLex.childs.get(0).value, withNotOperator, Utils.hexStringToBytes(bodyInLex.childs.get(2).value));
                                                subtype.bodyParts.add(field);
                                            } else {
                                                ctx.error = "Grammar: unknown expression in hex field : " + bodyInLex.childs.get(2);
                                                break;
                                            }
                                        } else if (bodyInLex.childs.get(1).name.equals(TLBParser.LEX_COLON)) {
                                            if (bodyInLex.childs.get(2).name.equals(TLBParser.LEX_IDENTIFIER)) {
                                                TLBTypeBodyPartFieldTypedId field = new TLBTypeBodyPartFieldTypedId(subtype, bodyInLex.childs.get(0).value, withNotOperator, bodyInLex.childs.get(2).getText());
                                                subtype.bodyParts.add(field);
                                            } else if (bodyInLex.childs.get(2).name.equals(TLBParser.LEX_EXPRESSION)) {
                                                TLBTypeBodyPartFieldTypedExpr field = new TLBTypeBodyPartFieldTypedExpr(subtype, bodyInLex.childs.get(0).value, withNotOperator, bodyInLex.childs.get(2).getText());
                                                subtype.bodyParts.add(field);
                                            } else if (bodyInLex.childs.get(2).name.equals(TLBParser.LEX_CAP_IDENTIFIER)) {
                                                TLBTypeBodyPartFieldTypedCapId field = new TLBTypeBodyPartFieldTypedCapId(subtype, bodyInLex.childs.get(0).value, withNotOperator, bodyInLex.childs.get(2).childs.get(1).getText());
                                                subtype.bodyParts.add(field);
                                            } else if (bodyInLex.childs.get(2).name.equals(TLBParser.LEX_TILDA_IDENTIFIER)) {
                                                TLBTypeBodyPartFieldTypedTildaId field = new TLBTypeBodyPartFieldTypedTildaId(subtype, bodyInLex.childs.get(0).value, withNotOperator, bodyInLex.childs.get(2).childs.get(1).getText());
                                                subtype.bodyParts.add(field);
                                            } else if (bodyInLex.childs.get(2).name.equals(TLBParser.LEX_TILDA_EXPR)) {
                                                TLBTypeBodyPartFieldTypedTildaExpr field = new TLBTypeBodyPartFieldTypedTildaExpr(subtype, bodyInLex.childs.get(0).value, withNotOperator, bodyInLex.childs.get(2).childs.get(1).getText());
                                                subtype.bodyParts.add(field);
                                            } else if (bodyInLex.childs.get(2).name.equals(TLBParser.LEX_CAP_EXPR)) {
                                                TLBTypeBodyPartFieldTypedCapExpr field = new TLBTypeBodyPartFieldTypedCapExpr(subtype, bodyInLex.childs.get(0).value, withNotOperator, bodyInLex.childs.get(2).childs.get(1).getText());
                                                subtype.bodyParts.add(field);
                                            } else {
                                                ctx.error = "Grammar: unknown expression in binary field : " + bodyInLex.childs.get(2);
                                                break;
                                            }
                                        }

                                    } else if (bodyInLex.name.equals(TLBParser.LEX_UNDERSCORE)) {
                                        TLBTypeBodyPartUndetermined field = new TLBTypeBodyPartUndetermined(subtype);
                                        subtype.bodyParts.add(field);
                                    } else if (bodyInLex.name.equals(TLBParser.LEX_TYPE_EXPRESSION)) {
                                        TLBTypeBodyPartTypeExpr typeExpr = new TLBTypeBodyPartTypeExpr(subtype, bodyInLex.getText());
                                        subtype.bodyParts.add(typeExpr);
                                    } else if (bodyInLex.name.equals(TLBParser.LEX_ARRAY)) {
                                        TLBTypeBodyPartArray typeExpr = new TLBTypeBodyPartArray(subtype, bodyInLex.getText());
                                        subtype.bodyParts.add(typeExpr);
                                    } else if (bodyInLex.name.equals(TLBParser.LEX_CAP_ARRAY)) {
                                        TLBTypeBodyPartCapArray typeExpr = new TLBTypeBodyPartCapArray(subtype, bodyInLex.childs.get(1).getText());
                                        subtype.bodyParts.add(typeExpr);
                                    } else if (bodyInLex.name.equals(TLBParser.LEX_TILDA_ARRAY)) {
                                        TLBTypeBodyPartTildaArray typeExpr = new TLBTypeBodyPartTildaArray(subtype, bodyInLex.childs.get(1).getText());
                                        subtype.bodyParts.add(typeExpr);
                                    } else if (bodyInLex.name.equals(TLBParser.LEX_EXPRESSION)) {
                                        TLBTypeBodyPartExpr expr = new TLBTypeBodyPartExpr(subtype, bodyInLex.getText());
                                        subtype.bodyParts.add(expr);
                                    } else {
                                        ctx.error = "Grammar: unknown expression in body type : " + bodyInLex;
                                    }
                                }


                            } else {
                                ctx.error = "Grammar: first lexem in header should be indentifier : " + lexName;
                            }


                        } else {
                            ctx.error = "Grammar: Header should contains name : " + headerLex;
                        }

                    } else {
                        ctx.error = "Grammar: Wrong lexem size in stack for TLBHeader : " + headerLex;
                    }


                } else {
                    ctx.error = "Grammar: Wrong lexem size in stack for TLBBody : " + bodyLex;
                }
            }
        } else
            ctx.error = "Grammar: Wrong lexem in stack : " + lex.name;

        // should process link
    }


}

