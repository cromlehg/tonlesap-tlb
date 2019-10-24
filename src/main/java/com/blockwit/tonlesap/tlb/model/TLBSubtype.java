package com.blockwit.tonlesap.tlb.model;

import java.util.ArrayList;
import java.util.List;

public class TLBSubtype extends TLBModel {

    public TLBType tlbType;

    public List<TLBTypeBodyPart> bodyParts = new ArrayList<TLBTypeBodyPart>();

    public TLBSubtype(TLBType tlbType) {
        this.tlbType = tlbType;
    }

    @Override
    public String toString(String identString, String identInc) {
        StringBuffer sb = new StringBuffer();
        if (!bodyParts.isEmpty()) {
            String nextIdent = identString + identInc;
            sb.append(nextIdent);
            for (int i = 0; i < bodyParts.size(); i++) {
                if (i > 0)
                    sb.append(" ");
                sb.append(bodyParts.get(i).toString("", identInc));
            }
        }
        sb.append(" = " + tlbType.name);
        return sb.toString();
    }

}
