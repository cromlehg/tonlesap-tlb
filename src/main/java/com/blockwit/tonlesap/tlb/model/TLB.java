package com.blockwit.tonlesap.tlb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Structure:
 * <p>
 * TLB:
 * TLBSubtype -> TLBType
 * <p>
 * TLBSubtype:
 * filed* templateexpr* expr* = type name template* expr*;
 */
public class TLB extends TLBModel {

    public Map<String, TLBType> tlbTypesMap = new HashMap<String, TLBType>();

    public List<TLBType> tlbTypes = new ArrayList<TLBType>();

    @Override
    public String toString(String identString, String identInc) {
        StringBuffer sb = new StringBuffer(identString + "TLB[");
        if (!tlbTypes.isEmpty()) {
            sb.append("\n");
            String nextIdent = identString + identInc;
            for (int i = 0; i < tlbTypes.size(); i++) {
                sb.append(tlbTypes.get(i).toString(nextIdent, identInc) + "\n");
            }
        }
        sb.append(identString + "]");
        return sb.toString();
    }

}
