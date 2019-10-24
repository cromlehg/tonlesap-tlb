package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class TLBTypeBodyPartFieldBinUndetermined extends TLBTypeBodyPartField {

    public TLBTypeBodyPartFieldBinUndetermined(TLBSubtype subtype, String name, boolean withNotOperator) {
        super(subtype, name, withNotOperator);
    }

    @Override
    public String toString(String identString, String identInc) {
        return super.toString(identString, identInc) + "$_";
    }

}
