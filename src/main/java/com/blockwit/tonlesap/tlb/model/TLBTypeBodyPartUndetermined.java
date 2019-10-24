package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class TLBTypeBodyPartUndetermined extends TLBTypeBodyPart {

    public TLBTypeBodyPartUndetermined(TLBSubtype subtype) {
        super(subtype);
    }

    @Override
    public String toString(String identString, String identInc) {
        return "_";
    }

}
