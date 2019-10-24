package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class TLBTypeBodyPartFieldBin extends TLBTypeBodyPartField {

    public CustomBitSet value;

    public TLBTypeBodyPartFieldBin(TLBSubtype subtype, String name, boolean withNotOperator, CustomBitSet value) {
        super(subtype, name, withNotOperator);
        this.value = value;
    }

    @Override
    public String toString(String identString, String identInc) {
        return super.toString(identString, identInc) + "$" + value.toString();
    }

}
