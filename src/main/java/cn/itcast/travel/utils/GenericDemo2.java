package cn.itcast.travel.utils;

public class GenericDemo2<T> {


    public  void  set(T t){
        System.out.println(t);
    }


    public  T get(T t){

        return t;
    }

    public static void main(String[] args) {
        new GenericDemo2<String>().get("aa");
    }
}
