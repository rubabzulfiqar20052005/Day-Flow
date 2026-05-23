package com.example.dayflow.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    long insert(UserEntity user);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    UserEntity findByEmail(String email);
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    UserEntity getByEmail(String email);
    @androidx.room.Query("UPDATE users SET passwordHash = :hash WHERE email = :email")
    int updatePasswordByEmail(String email, String hash);
    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :hash LIMIT 1")
    UserEntity login(String email, String hash);
}

