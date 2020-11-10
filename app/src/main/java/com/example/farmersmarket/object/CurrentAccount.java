package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "current_account")
public class CurrentAccount {
    @PrimaryKey
    public int accountID;

    @Ignore
    public CurrentAccount(int accountID) {
        this.accountID = accountID;
    }

    public CurrentAccount() {
    }

    ;
}
