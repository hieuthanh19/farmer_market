package com.example.farmersmarket.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.FeedbackProduct;

import java.util.ArrayList;

@Dao
public interface FeedbackProductDAO {
    @Insert
    void insertFeedbackProduct(FeedbackProduct feedbackProduct);

    @Update
    void updateFeedbackProduct(FeedbackProduct feedbackProduct);

    @Delete
    void deleteFeedbackProduct(FeedbackProduct feedbackProduct);

    @Query("select * from feedback_product")
    public FeedbackProduct[] getAllFeedbackProduct();

    @Query("select count(1) from feedback_product")
    int getFeedbackProductCount();

    @Query("select * from feedback_product where productID = :productID")
    FeedbackProduct getFeedbackProductByProductID(int productID);

    @Query("select * from feedback_product where accountID = :accountID")
    FeedbackProduct getFeedbackProductByAccountID(int accountID);
}
