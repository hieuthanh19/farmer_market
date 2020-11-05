package com.example.farmersmarket.object;

import androidx.room.TypeConverter;

import java.sql.Date;

public class ConvertDate {
    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
