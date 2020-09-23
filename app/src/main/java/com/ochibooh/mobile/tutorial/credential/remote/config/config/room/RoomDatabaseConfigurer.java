package com.ochibooh.mobile.tutorial.credential.remote.config.config.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.logging.Level;

import lombok.Synchronized;
import lombok.extern.java.Log;

@Log
public class RoomDatabaseConfigurer {
    private static RoomRepositoryConfigurer repository = null;

    @Synchronized
    public static void init(@NonNull Context context) {
        if (repository == null) {
            synchronized (RoomRepositoryConfigurer.class) {
                if (repository == null) {
                    repository = Room.databaseBuilder(context, RoomRepositoryConfigurer.class, "credentials_remote_config")
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    log.log(Level.INFO, String.format("Database created [ version=%s ]",  db.getVersion()));
                                }
                            }).addMigrations(new Migration(1, 2) {
                                @Override
                                public void migrate(@NonNull SupportSQLiteDatabase db) {
                                    if (db.needUpgrade(2)) {
                                        log.log(Level.WARNING, "Do database migration from 1 to 2");
                                    }
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
    }

    public static RoomRepositoryConfigurer getRepository(@NonNull Context context) {
        if (repository == null) {
            init(context);
        }
        return repository;
    }
}
