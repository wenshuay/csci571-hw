package com.example.yuanw.myapplication;

/**
 * Created by yuanw on 2017/11/26.
 */

public class FavItem {
    String symbol;
    double lastprice;
    double change;
    String per;

    public FavItem(String symbol,double lastprice, double change, String per ) {
        this.symbol = symbol;
        this.lastprice = lastprice;
        this.change = change;
        this.per = per;
    }

    @Override
    public boolean equals(Object obj) {
        //FavItem cur =  (FavItem)obj;
        return this.symbol.equals(((FavItem)obj).symbol) ;
    }
}
