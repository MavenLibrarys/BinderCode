package com.yxf.bindercode.db.entry;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Goods {
    @PrimaryKey(autoGenerate = true)
    private int goodsId;
    @ColumnInfo(name = "goods_name")
    private String goodsName;

    @ColumnInfo(name = "goods_price")
    private String goodsPrice;

    public Goods(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                '}';
    }
}
