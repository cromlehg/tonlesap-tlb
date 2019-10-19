package com.blockwit.tonlesap.tlb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TLB {

    public String error;

    public Map<String, TLBType> tlbTypesMap = new HashMap<String, TLBType>();

    public List<TLBType> tlbTypes = new ArrayList<TLBType>();

    public void addType() {
        // TODO
    }

    public TLB withError(String msg) {
        this.error = msg;
        return this;
    }

}
