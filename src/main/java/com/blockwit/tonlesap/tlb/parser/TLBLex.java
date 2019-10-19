package com.blockwit.tonlesap.tlb.parser;

import java.util.ArrayList;
import java.util.List;

public class TLBLex {

    public int startLine;

    public int endLine;

    public int startIndex;

    public int endIndex;

    public String name;

    public String value;

    public List<TLBLex> childs = new ArrayList<TLBLex>();

    public TLBLex(int startLine, int endLine, int startIndex, int endIndex, String name, String value) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "LEX_" + name + "(" + startLine + ":" + startIndex + ", " + endLine + ":" + endIndex + ") = \"" + value
                + "\"";
    }

}
