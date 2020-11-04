package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "account", foreignKeys = @ForeignKey(entity = AccountType.class, parentColumns = "typeID", childColumns = "typeID"))
public class Account {
    @PrimaryKey
    public int accountID;
    public int typeID;
    public String phone;
    public String password;
    public String name;
    public int gender;
    public String address;
    public String email;
    public String image;
    public int status;
}
