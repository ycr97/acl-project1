package com.ycr;

import cn.hutool.crypto.SecureUtil;

/**
 * @author ycr
 * @date 2020/11/27
 */
public class Test {

    @org.junit.Test
    public void test() {
        System.out.println(tableSizeFor(1000));
    }

    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n;
//        return (n < 0) ? 1 : (n >= 1 << 30) ? 1 << 30 : n + 1;
    }

    @org.junit.Test
    public void MD5() {
        System.out.println(SecureUtil.md5("123456"));
    }
}
