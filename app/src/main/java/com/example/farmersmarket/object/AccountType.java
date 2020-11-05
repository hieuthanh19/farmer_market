package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_type")
public class AccountType {
    @PrimaryKey
    public int typeID;
    public String typeName;
    public int status;

    public AccountType(int typeID, String typeName, int status) {
        this.typeID = typeID;
        this.typeName = typeName;
        this.status = status;
    }

    public AccountType() {
    }
}
