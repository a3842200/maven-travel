package cn.itcast.travel.utils;

public class GenericDemo {
    
    
//    public void  set(String s){
//        System.out.println(s);
//    }
//
//
//    public void  set(double s){
//        System.out.println(s);
//    }
    
    //  方法级别泛型  <T>  element  template
    
    
    public <T>  void  set(T t){
        System.out.println(t);
    }
    
    
    public <T> T get(T t){
        
        return t;
    }


    public  String  get(){
        return "aaa";
    }


    public static void main(String[] args) {
        new GenericDemo().set("111");
        Integer integer = new GenericDemo().get(11);
        String ss = new GenericDemo().get("123");

    }
}
