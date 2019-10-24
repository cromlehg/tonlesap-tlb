package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public abstract class TLBTypeBodyPartFieldTyped extends TLBTypeBodyPartField {

    public Object typeDef;

    public TLBTypeBodyPartFieldTyped(TLBSubtype subtype, String name, boolean withNotOperator, Object typeDef) {
        super(subtype, name, withNotOperator);
        this.typeDef = typeDef;
    }

    @Override
    public String toString(String identString, String identInc) {
        return super.toString(identString, identInc) + ":" + typeToString();
    }

    public abstract String typeToString();

}
