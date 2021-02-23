package com.example.contact_chetnasharma_c0776253.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContactInfo.class}, version = 1, exportSchema = false)
public abstract class ContactRoomDb extends RoomDatabase {

    private static final String DB_NAME = "contact_database";

    public abstract ContactDao contactDao();

    private static volatile ContactRoomDb INSTANCE;

    public static ContactRoomDb getINSTANCE(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactRoomDb.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;

    }
}
