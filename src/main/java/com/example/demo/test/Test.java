package com.example.demo.test;/*
 问题：把字符串转化为整数 ,若输入无效，则返回-1。
*/

/*
 问题：有三个线程，每个线程分别从1打印到100，现在要求10个一批10个一批的打印，
 也就是说必须三个线程都打印完前十个，每个线程才能继续打印下一个，如果有某个线程已经打印完了前十个，
 其他线程没打印完，则必须等待
*/

public class Test {

    private static String max = "2147483647";
    private static String min = "2147483648";

    private static int maxLength = 9;

    public static void main(String[] args) {
        System.out.println(test1("-997783"));
    }

    public static boolean judge(String s){

        String a = new String(s);
        if(a.startsWith("-")){
            a = a.replaceFirst("-","");
            //长度判断
            if(a.length()>maxLength){
                return false;
            }
            //大小判端
            if(a.length()==maxLength && a.compareTo(min)>0){
                return false;
            }
            return true;
        }

        if(a.length()>maxLength){
            return false;
        }
        if(a.length() == maxLength&&a.compareTo(max)>0){
            return false;
        }
        return true;
    }

    public static int test1(String s) {
        boolean pass = judge(s);
        if(!pass){
            return -1;
        }
        boolean isNeg = false;
        if (s.startsWith("-")) {
            isNeg = true;
        }
        int i = isNeg ? 1 : 0;
        int intValue = 0;
        for (; i < s.length(); i++) {
            int anInt = findInt(s.charAt(i));
            if (-1 == anInt) {
                return -1;
            }
            intValue = 10 * intValue + anInt;
        }
        return isNeg ? 0 - intValue : intValue;
    }

    public static int findInt(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                return -1;
        }
    }


}