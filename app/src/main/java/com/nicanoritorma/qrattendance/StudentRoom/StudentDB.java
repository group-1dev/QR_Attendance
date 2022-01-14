package com.nicanoritorma.qrattendance.StudentRoom;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nicanoritorma.qrattendance.model.StudentModel;

@Database(entities = {StudentModel.class}, version = 1)
public abstract class StudentDB extends RoomDatabase {

    private static StudentDB instance;

    public abstract StudentDAO studentDAO();

    public static synchronized StudentDB getInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    StudentDB.class, "student_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private StudentDAO studentDAO;

        private PopulateDbAsyncTask(StudentDB db)
        {
            studentDAO = db.studentDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}

