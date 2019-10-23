package com.blockwit.tonlesap.tlb.parser;

import com.blockwit.tonlesap.tlb.model.TLB;

import java.util.Stack;

public class TLBParserContext {

    public Stack<TLBLex> stack = new Stack<TLBLex>();

    public int line = 0;

    public int index = 0;

    public String error;

    public TLB tlb = new TLB();

}

