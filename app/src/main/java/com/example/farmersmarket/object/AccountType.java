package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_type")
public class AccountType {
    @PrimaryKey(autoGenerate = true)
    public int typeID;
    public String typeName;
    public int status;

    @Ignore
    public AccountType(String typeName, int status) {
        this.typeName = typeName;
        this.status = status;
    }

    public AccountType() {
    }
}
