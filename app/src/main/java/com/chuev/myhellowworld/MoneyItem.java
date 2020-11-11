package com.chuev.myhellowworld;

import com.chuev.myhellowworld.remote.MoneyRemoteItem;

public class MoneyItem {
    private String id;
    private String title;
    private String value;
    public MoneyItem(String id, String title, String value) {
        this.id=id;
        this.title = title;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public static MoneyItem getInstance (MoneyRemoteItem moneyRemoteItem)
    {
        return new MoneyItem(moneyRemoteItem.getItemId(), moneyRemoteItem.getName(), moneyRemoteItem.getPrice()+"РУБ");
    }

}
