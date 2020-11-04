package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_type")
public class AccountType {
    @PrimaryKey
    public int typeID;
    public String typeName;
    public int status;
}
