package com.example.leaderboard;

import com.google.gson.annotations.SerializedName;

public class StoreDataModel {


    private  String name;

    private String currency;

    private String money_format;

    public StoreDataModel(String name, String currency, String money_format) {
        this.name = name;
        this.currency = currency;
        this.money_format = money_format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMoney_format() {
        return money_format;
    }

    public void setMoney_format(String money_format) {
        this.money_format = money_format;
    }
}
