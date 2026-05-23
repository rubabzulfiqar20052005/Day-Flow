package com.example.dayflow.db;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users",
        indices = {@Index(value = {"email"}, unique = true)})
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String email;

    // We'll store hashed password (better than plain)
    public String passwordHash;

    public UserEntity(String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }
}
