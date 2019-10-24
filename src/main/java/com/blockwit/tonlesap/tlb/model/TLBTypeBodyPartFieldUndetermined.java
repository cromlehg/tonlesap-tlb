package com.blockwit.tonlesap.tlb.model;

import com.blockwit.tonlesap.tlb.Utils;

import java.util.BitSet;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class TLBTypeBodyPartFieldUndetermined extends TLBTypeBodyPartField {

    public TLBTypeBodyPartFieldUndetermined(TLBSubtype subtype, String name, boolean withNotOperator) {
        super(subtype, name, withNotOperator);
    }

    @Override
    public String toString(String identString, String identInc) {
        return super.toString(identString, identInc) + "$_";
    }

}
