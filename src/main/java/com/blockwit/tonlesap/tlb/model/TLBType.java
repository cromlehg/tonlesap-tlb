package com.blockwit.tonlesap.tlb.model;

import com.blockwit.tonlesap.tlb.parser.TLBLex;

import java.util.ArrayList;
import java.util.List;

public class TLBType extends TLBModel {

    public String name;

    public List<TLBSubtype> subtypes = new ArrayList<TLBSubtype>();

    public TLBType(String name) {
        this.name = name;
    }

    @Override
    public String toString(String identString, String identInc) {
        StringBuffer sb = new StringBuffer(identString + name + "[");
        if (!subtypes.isEmpty()) {
            sb.append("\n");
            String nextIdent = identString + identInc;
            for (int i = 0; i < subtypes.size(); i++) {
                sb.append(subtypes.get(i).toString(nextIdent, identInc) + "\n");
            }
        }
        sb.append(identString + "]");
        return sb.toString();
    }

}
