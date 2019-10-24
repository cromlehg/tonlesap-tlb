package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class TLBTypeBodyPartCapArray extends TLBTypeBodyPart {

    public String array;

    public TLBTypeBodyPartCapArray(TLBSubtype subtype, String array) {
        super(subtype);
        this.array = array;
    }

    @Override
    public String toString(String identString, String identInc) {
        return identString + "^" + array;
    }

}
