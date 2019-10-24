package com.blockwit.tonlesap.tlb;

/**
 * @author Alexander Strakh, cromlehg@gmail.com
 **/
public class Utils {

    public static String byteArrayToHexString(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static byte[] hexStringToBytes(String s) {
        if(s.length() % 2 == 1)
            s = "0" + s;
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}
