package com.yy.obServe;

import java.util.Observable;

public class Watched extends Observable {
    private String data = "";

    public String getData() {
        return data;
    }

    public void setData(String data) {


            this.data = data;

            this.setChanged();

            this.notifyObservers();
    }

}
