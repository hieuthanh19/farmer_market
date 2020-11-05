package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.Notification;

import java.util.ArrayList;

@Dao
public interface NotificationDAO {

    @Insert
    void insertNotification(Notification notification);

    @Update
    void updateNotification(Notification notification);

    @Delete
    void deleteNotification(Notification notification);

    @Query("select * from notification")
    public Notification[] getAllNotification();

    @Query("select count(1) from notification")
    int getNotificationCount();

    @Query("select * from notification where accountID = :accountID")
    Notification getNotification(int accountID);
}
