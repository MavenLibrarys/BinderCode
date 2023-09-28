package com.yxf.bindercode.hicar;

public enum CardType {
    MAP(1),
    MEDIA(2),
    WEATHER(3),
    EDIT(4);
    int value;

    CardType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CardType getCardTypeByValue(int value) {
        for (CardType cardType : values()) {
            if (cardType.getValue() == value) {
                return cardType;
            }
        }
        return MAP;
    }
}
