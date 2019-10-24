package com.blockwit.tonlesap.tlb.model;

import com.blockwit.tonlesap.tlb.Utils;

import java.util.BitSet;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class TLBTypeBodyPartFieldHex extends TLBTypeBodyPartField {

    public byte[] value;

    public TLBTypeBodyPartFieldHex(TLBSubtype subtype, String name, boolean withNotOperator, byte[] value) {
        super(subtype, name, withNotOperator);
        this.value = value;
    }

    @Override
    public String toString(String identString, String identInc) {
        return super.toString(identString, identInc) + "#" + Utils.byteArrayToHexString(value);
    }

}