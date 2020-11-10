package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.CurrentAccount;

import java.util.List;

@Dao
public interface CurrentAccountDAO {
    @Insert
    void insertCurrentAccount(CurrentAccount account);

    @Update
    void updateCurrentAccount(CurrentAccount account);

    @Delete
    void deleteCurrentAccount(CurrentAccount account);

    @Query("select * from current_account")
    List<CurrentAccount> getAllCurrentAccounts();

    @Query("select count(1) from current_account")
    int getCurrentAccountsCount();

    @Query("select * from current_account where accountID = :accountID")
    CurrentAccount getCurrentAccount(int accountID);
}
