package com.yy;

import com.yy.obServe.Watched;
import com.yy.obServe.Watcher;

public class Test2 {
    public static void main(String[] args) {
        Watched watched = new Watched();
        Watcher watcher = new Watcher();
        watched.addObserver(watcher);
        watched.setData("one");
        watched.setData("one");
        watched.setData("two");
    }
}
