package com.blockwit.tonlesap.tlb.parser;

import java.util.ArrayList;
import java.util.List;

public class TLBLex {

    public static final String identConst = "  ";

    public int startLine;

    public int endLine;

    public int startIndex;

    public int endIndex;

    public String name;

    public String value;

    public List<TLBLex> childs = new ArrayList<TLBLex>();

    public TLBLex(String name) {
        this.name = name;
    }

    public TLBLex(int startLine, int endLine, int startIndex, int endIndex, String name, String value) {
        this(name);
        this.startLine = startLine;
        this.endLine = endLine;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.value = value;
    }

    public void addChild(TLBLex lex) {
        if (childs.isEmpty()) {
            startLine = lex.startLine;
            startIndex = lex.startIndex;
        }

        endLine = lex.endLine;
        endIndex = lex.endIndex;

        childs.add(lex);
    }

    public String getText() {
        if (value == null) {

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < childs.size(); i++) {
                if (i > 0)
                    sb.append(" ");
                sb.append(childs.get(i).getText());
            }
            return sb.toString();
        } else
            return value;
    }

    @Override
    public String toString() {
        return toString(identConst, identConst);
    }

    public String toString(String prevIdent, String ident) {
        return prevIdent + "LEX_" + name + "(" + startLine + ":" + startIndex + ", " + endLine + ":" + endIndex + ") = " + valueToString(prevIdent, ident);
    }

    public String valueToString(String prevIdent, String ident) {
        if (childs.isEmpty()) {
            if (value == null)
                return "null";
            else if (value.equals("\n"))
                return "\"\\n\"";
            else
                return "\"" + value + "\"";
        } else {
            StringBuffer sb = new StringBuffer("[\n");
            String nextIdent = prevIdent + ident;
            for (int i = 0; i < childs.size(); i++) {
                sb.append(childs.get(i).toString(nextIdent, ident) + "\n");
            }

            sb.append(prevIdent + "]");
            return sb.toString();
        }
    }

}
