package com.example.dayflow.db;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    public interface Callback<T> {
        void onResult(T result);
    }

    private final UserDao userDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userDao = db.userDao();
    }

    // ---------------- SIGNUP ----------------
    public void signup(String name, String email, String password, Callback<Boolean> cb) {
        executor.execute(() -> {
            UserEntity existing = userDao.findByEmail(email);
            if (existing != null) {
                cb.onResult(false);
                return;
            }
            String hash = AuthUtil.sha256(password);
            userDao.insert(new UserEntity(name, email, hash));
            cb.onResult(true);
        });
    }

    // ---------------- LOGIN ----------------
    public void login(String email, String password, Callback<UserEntity> cb) {
        executor.execute(() -> {
            String hash = AuthUtil.sha256(password);
            UserEntity user = userDao.login(email, hash);
            cb.onResult(user);
        });
    }

    // ---------------- FIND USER (Forgot Password) ----------------
    public void findUserByEmail(String email, Callback<UserEntity> cb) {
        executor.execute(() -> {
            UserEntity user = userDao.findByEmail(email);
            cb.onResult(user);
        });
    }

    // ---------------- UPDATE PASSWORD (Forgot Password) ----------------
    public void updatePasswordByEmail(String email, String hash, Callback<Boolean> cb) {
        executor.execute(() -> {
            int rows = userDao.updatePasswordByEmail(email, hash);
            cb.onResult(rows > 0);
        });
    }
}

