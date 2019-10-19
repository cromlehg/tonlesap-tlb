package com.blockwit.tonlesap.tlb.model;

import com.blockwit.tonlesap.tlb.parser.TLBLex;

import java.util.ArrayList;
import java.util.List;

public class TLBType {

    public String name;

    public List<TLBLex> lexes = new ArrayList<TLBLex>();

    public TLBType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(name + " {");

        if (!lexes.isEmpty())
            sb.append("\n");

        for (TLBLex lex : lexes) {
            int idx = lex.value.lastIndexOf('=');
            String expr = lex.value.substring(0, idx).replace('\n', ' ').replace('\t', ' ').replaceAll(" +", " ").trim();
            sb.append("\t");
            sb.append(expr);
            sb.append("\n");
        }

        sb.append("}");
        return sb.toString();
    }

}
