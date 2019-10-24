package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public abstract class TLBTypeBodyPartField extends TLBTypeBodyPart {

    public String name;

    public boolean withNotOperator = false;

    public TLBTypeBodyPartField(TLBSubtype subtype, String name, boolean withNotOperator) {
        super(subtype);
        this.name = name;
        this.withNotOperator = withNotOperator;
    }

    @Override
    public String toString(String identString, String identInc) {
        return identString + (withNotOperator ? "!" : "") + name;
    }

}
