package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class TLBTypeBodyPartTypeExpr extends TLBTypeBodyPart {

    public String expr;

    public TLBTypeBodyPartTypeExpr(TLBSubtype subtype, String expr) {
        super(subtype);
        this.expr = expr;
    }

    @Override
    public String toString(String identString, String identInc) {
        return identString + expr;
    }

}
