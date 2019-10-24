package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class TLBTypeBodyPartFieldTypedTildaExpr extends TLBTypeBodyPartFieldTyped {

    public TLBTypeBodyPartFieldTypedTildaExpr(TLBSubtype subtype, String name, boolean withNotOperator, String expr) {
        super(subtype, name, withNotOperator, expr);
    }

    @Override
    public String typeToString() {
        return "~" + typeDef.toString();
    }

}
