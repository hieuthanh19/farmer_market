package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.AccountType;

import java.util.ArrayList;

@Dao
public interface AccountTypeDAO {

    @Insert
    void insertAccountType(AccountType accountType);

    @Update
    void updateAccountType(AccountType accountType);

    @Delete
    void deleteAccountType(AccountType accountType);

    @Query("select * from account_type")
    AccountType[] getAllAccountTypes();

    @Query("select count(1) from account_type")
    int getAccountTypeCount();

    @Query("select * from account_type where typeID = :typeID")
    AccountType getAccountType(int typeID);
}
