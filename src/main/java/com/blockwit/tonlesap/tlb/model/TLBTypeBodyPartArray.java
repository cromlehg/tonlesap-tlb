package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class TLBTypeBodyPartArray extends TLBTypeBodyPart {

    public String array;

    public TLBTypeBodyPartArray(TLBSubtype subtype, String array) {
        super(subtype);
        this.array = array;
    }

    @Override
    public String toString(String identString, String identInc) {
        return identString + array;
    }

}
