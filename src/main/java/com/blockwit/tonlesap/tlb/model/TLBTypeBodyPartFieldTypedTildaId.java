package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class TLBTypeBodyPartFieldTypedTildaId extends TLBTypeBodyPartFieldTyped {

    public TLBTypeBodyPartFieldTypedTildaId(TLBSubtype subtype, String name, boolean withNotOperator, String identifier) {
        super(subtype, name, withNotOperator, identifier);
    }

    @Override
    public String typeToString() {
        return "~" + typeDef.toString();
    }

}
