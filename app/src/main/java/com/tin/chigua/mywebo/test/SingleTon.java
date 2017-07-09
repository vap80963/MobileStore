package com.tin.chigua.mywebo.test;

/**
 * Created by hasee on 6/21/2017.
 */
public class SingleTon {

    private SingleTon() {}

    private static class SigleTonHolder{
        private static final SingleTon INSTANCE = new SingleTon();
    }

    public static SingleTon getInstance(){
        return SigleTonHolder.INSTANCE;
    }



}






