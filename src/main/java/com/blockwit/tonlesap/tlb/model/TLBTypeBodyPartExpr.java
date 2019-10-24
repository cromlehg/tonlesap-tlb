package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class TLBTypeBodyPartExpr extends TLBTypeBodyPart {

    public String expr;

    public TLBTypeBodyPartExpr(TLBSubtype subtype, String expr) {
        super(subtype);
        this.expr = expr;
    }

    @Override
    public String toString(String identString, String identInc) {
        return identString + expr;
    }

}
