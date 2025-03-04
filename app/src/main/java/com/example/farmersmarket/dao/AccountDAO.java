package com.example.farmersmarket.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmersmarket.object.Account;

import java.util.List;

@Dao
public interface AccountDAO {
    @Insert
    void insertAccount(Account account);

    @Update
    void updateAccount(Account account);

    @Delete
    void deleteAccount(Account account);

    @Query("select * from account")
    List<Account> getAllAccounts();

    @Query("select count(1) from account")
    int getAccountsCount();

    @Query("select * from account where accountID = :accountID")
    Account getAccount(int accountID);

    @Query("select * from account where phone = :phoneNumber and password = :password")
    Account getAccountByPhoneAndPassword(String phoneNumber, String password);

    @Query("select * from account where phone = :phoneNumber")
    Account getAccountByPhone(String phoneNumber);


}
