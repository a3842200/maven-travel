package cn.itcast.travel.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class EncoderDEmo {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String  s = "男";
        String encode = URLEncoder.encode(s, "utf-8");
        System.out.println(encode);


        String decode = URLDecoder.decode(encode, "utf-8");
        System.out.println(decode);


    }
}
