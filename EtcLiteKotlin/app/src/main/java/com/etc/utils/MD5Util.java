package com.etc.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author :creat by Xia燚
 * 时间：2020/12/8
 * 邮箱：XiahaotianV@163.com
 **/
public class MD5Util {
    public final static String getMD5String(String sSecret) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sSecret.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

}
