package com.blockwit.tonlesap.tlb.model;

public class TLBSubtype extends TLBModel {

    public TLBType tlbType;

    public TLBSubtype(TLBType tlbType) {
        this.tlbType = tlbType;
    }

    @Override
    public String toString(String identString, String identInc) {
        return identString + "subtype";
//        StringBuffer sb = new StringBuffer(identString + name + "[");
//        if (!subtypes.isEmpty()) {
//            sb.append("\n");
//            String nextIdent = identString + identInc;
//            for (int i = 0; i < subtypes.size(); i++) {
//                sb.append(subtypes.get(i).toString(nextIdent, identInc) + "\n");
//            }
//        }
//        sb.append("]");
//        return sb.toString();
    }

}
