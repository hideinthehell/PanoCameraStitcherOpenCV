package com.fx.android.watercamerademo;

/**
 * Created by sunbo on 2019/1/14.
 */

public class Mthread extends Thread implements Runnable{
        private static Mthread instance;
        private Mthread(){
        }
        public static Mthread getInstance(){
            if(instance==null){
                instance=new Mthread();
            }
            return instance;
        }

}
