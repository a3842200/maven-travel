package cn.itcast.travel.utils;


import java.util.Random;

public class CodeUtils {


    public  static  String generateCode(){
        StringBuffer sb  = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            int num = random.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        System.out.println(generateCode());
    }

}
