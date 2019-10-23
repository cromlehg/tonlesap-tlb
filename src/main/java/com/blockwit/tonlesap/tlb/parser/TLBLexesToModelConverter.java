package com.blockwit.tonlesap.tlb.parser;

import com.blockwit.tonlesap.tlb.model.TLBSubtype;
import com.blockwit.tonlesap.tlb.model.TLBType;

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
        if(lex.name.equals(TLBParser.LEX_TYPE_CONSTRUCTOR)) {
            if(lex.childs.size() != 3) {
                ctx.error = "Grammar: Wrong lexem size in stack for TLBType : " + lex;
            } else {
                TLBLex bodyLex = lex.childs.get(0);
                if(bodyLex.name.equals(TLBParser.LEX_TYPE_CONSTRUCTOR_BODY)) {

                    // TODO: Get all fields

                    TLBLex headerLex = lex.childs.get(2);
                    if(headerLex.name.equals(TLBParser.LEX_TYPE_CONSTRUCTOR_HEADER)) {

                        if(headerLex.childs.size() >= 1) {
                            TLBLex lexName =  headerLex.childs.get(0);

                            if(lexName.name.equals(TLBParser.LEX_IDENTIFIER)) {

                                TLBType tlbType = ctx.tlb.tlbTypesMap.get(lexName.value);
                                if(tlbType == null) {
                                    tlbType =  new TLBType(lexName.value);
                                    tlbType.lexems.add(lex);
                                    ctx.tlb.tlbTypesMap.put(lexName.value, tlbType);
                                    ctx.tlb.tlbTypes.add(tlbType);
                                }


                                TLBSubtype subtype = new TLBSubtype(tlbType);
                                subtype.lexems.add(lex);
                                tlbType.subtypes.add(subtype);

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
    }


}

