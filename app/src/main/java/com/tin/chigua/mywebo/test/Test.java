package com.tin.chigua.mywebo.test;

import javax.security.auth.callback.Callback;

/**
 * Created by hasee on 6/22/2017.
 */

public class Test {


    Callback mCallback = new Callback() {

        @Override
        public int hashCode() {
            return super.hashCode();


        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    };

}
