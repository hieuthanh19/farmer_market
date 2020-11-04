package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "notification", foreignKeys = @ForeignKey(entity = Account.class,
        parentColumns = "accountID", childColumns = "accountID"))
public class Notification {
    @PrimaryKey(autoGenerate = true)
    public int notificationID;
    public int accountID;
    public String title;
    public String content;
    public Date createdDate;
    public int status;
}
