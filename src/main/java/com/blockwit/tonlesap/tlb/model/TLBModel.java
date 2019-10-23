package com.blockwit.tonlesap.tlb.model;

import com.blockwit.tonlesap.tlb.parser.TLBLex;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public abstract class TLBModel {

    public static final String identConst = "  ";

    public static final String startIdentConst = "";

    public List<TLBLex> lexems =  new ArrayList<TLBLex>();

    @Override
    public String toString() {
        return toString(startIdentConst, identConst);
    }

    public abstract String toString(String identString, String identInc);

}
