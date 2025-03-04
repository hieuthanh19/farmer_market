package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.FeedbackShipping;

@Dao
public interface FeedbackShippingDAO {

    @Insert
    void insertFeedbackShipping(FeedbackShipping feedbackShipping);

    @Update
    void updateFeedbackShipping(FeedbackShipping feedbackShipping);

    @Delete
    void deleteFeedbackShipping(FeedbackShipping feedbackShipping);

    @Query("select * from feedback_shipping")
    FeedbackShipping[] getAllFeedbackShipping();

    @Query("select count(1) from feedback_shipping")
    int getFeedbackShippingCount();

    @Query("select * from feedback_shipping where accountID = :accountID and shippingID = :shippingID")
    FeedbackShipping getFeedbackShipping(int accountID, int shippingID);

    @Query("select * from feedback_shipping where accountID = :accountID")
    FeedbackShipping[] getFeedbackOfAccount(int accountID);

    @Query("select * from feedback_shipping where shippingID = :shippingID")
    FeedbackShipping[] getFeedbackOfShippingUnit(int shippingID);
}
