package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public abstract class TLBTypeBodyPart extends TLBModel {

    public TLBSubtype subtype;

    public TLBTypeBodyPart(TLBSubtype subtype) {
        this.subtype = subtype;
    }

}
