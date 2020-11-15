package com.example.farmersmarket.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "current_account",
        foreignKeys = @ForeignKey(entity = Account.class, parentColumns = "accountID", childColumns = "accountID"),
        indices = @Index("accountID"))
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
