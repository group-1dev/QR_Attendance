package com.nicanoritorma.qrattendance.OfflineRepository;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.nicanoritorma.qrattendance.AttendanceRoom.AttendanceDAO;
import com.nicanoritorma.qrattendance.AttendanceRoom.AttendanceDB;
import com.nicanoritorma.qrattendance.model.AttendanceModel;

import java.util.List;

public class AttendanceRepository {

    private AttendanceDAO attendanceDAO;
    private LiveData<List<AttendanceModel>> attendanceList;

    public AttendanceRepository(Application application)
    {
        AttendanceDB database = AttendanceDB.getInstance(application);
        attendanceDAO = database.attendanceDAO();
        attendanceList = attendanceDAO.getAllAttendance();
    }

    public void insert(AttendanceModel attendance)
    {
        new InsertAttendanceAsyncTask(attendanceDAO).execute(attendance);
    }

    public void update(AttendanceModel attendance)
    {
        new UpdateAttendanceAsyncTask(attendanceDAO).execute(attendance);
    }

    public void updateTime(int id, String date, String time)
    {
        new UpdateTimeAsyncTask(attendanceDAO, id, date, time).execute();
    }

    public LiveData<AttendanceModel> getAttendanceDT(int id)
    {
        return attendanceDAO.getAttendanceDT(id);
    }

    public void delete(AttendanceModel attendance)
    {
        new DeleteAttendanceAsyncTask(attendanceDAO).execute(attendance);
    }

    public void deleteAllAttendance()
    {
        new DeleteAllAttendanceAsyncTask(attendanceDAO).execute();
    }

    public LiveData<List<AttendanceModel>> getAttendanceList() {
        return attendanceList;
    }

    private static class UpdateTimeAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private AttendanceDAO attendanceDAO;
        private int itemId;
        private String date, time;

        private UpdateTimeAsyncTask(AttendanceDAO attendanceDAO, int itemId, String date, String time)
        {
            this.attendanceDAO = attendanceDAO;
            this.itemId = itemId;
            this.date = date;
            this.time = time;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            attendanceDAO.updateTime(itemId, date, time);
            return null;
        }
    }

    private static class InsertAttendanceAsyncTask extends AsyncTask<AttendanceModel, Void, Void> {
        private AttendanceDAO attendanceDAO;

        private InsertAttendanceAsyncTask(AttendanceDAO attendanceDAO)
        {
            this.attendanceDAO = attendanceDAO;
        }

        @Override
        protected Void doInBackground(AttendanceModel... attendanceModels) {
            attendanceDAO.insert(attendanceModels[0]);
            return null;
        }
    }

    private static class UpdateAttendanceAsyncTask extends AsyncTask<AttendanceModel, Void, Void> {
        private AttendanceDAO attendanceDAO;

        private UpdateAttendanceAsyncTask(AttendanceDAO attendanceDAO)
        {
            this.attendanceDAO = attendanceDAO;
        }

        @Override
        protected Void doInBackground(AttendanceModel... attendanceModels) {
            attendanceDAO.update(attendanceModels[0]);
            return null;
        }
    }

    private static class DeleteAttendanceAsyncTask extends AsyncTask<AttendanceModel, Void, Void> {
        private AttendanceDAO attendanceDAO;

        private DeleteAttendanceAsyncTask(AttendanceDAO attendanceDAO)
        {
            this.attendanceDAO = attendanceDAO;
        }

        @Override
        protected Void doInBackground(AttendanceModel... attendanceModels) {
            attendanceDAO.delete(attendanceModels[0]);
            return null;
        }
    }

    private static class DeleteAllAttendanceAsyncTask extends AsyncTask<Void, Void, Void> {
        private AttendanceDAO attendanceDAO;

        private DeleteAllAttendanceAsyncTask(AttendanceDAO attendanceDAO)
        {
            this.attendanceDAO = attendanceDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            attendanceDAO.deleteAllStudent();
            return null;
        }
    }
}
