package com.blockwit.tonlesap.tlb.model;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class CustomBitSet {

    public boolean[] value;

    public CustomBitSet(String bitSet) {
        value = new boolean[bitSet.length()];
        for (int i = 0; i < bitSet.length(); i++)
            value[0] = bitSet.charAt(i) == '1';
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < value.length; i++)
            sb.append(value[i] ? "1" : "0");
        return sb.toString();
    }
}
