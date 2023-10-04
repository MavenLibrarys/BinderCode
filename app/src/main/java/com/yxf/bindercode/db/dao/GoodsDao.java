package com.yxf.bindercode.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.yxf.bindercode.db.entry.Goods;

import java.util.List;

@Dao
public interface GoodsDao {
    @Insert
    void insertGoods(Goods goods);

    @Insert
    void insertGoods(List<Goods> goodsList);

    @Query("select * from goods")
    List<Goods> getAll();
}
