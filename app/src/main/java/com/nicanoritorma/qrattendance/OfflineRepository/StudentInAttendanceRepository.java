package com.nicanoritorma.qrattendance.OfflineRepository;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.nicanoritorma.qrattendance.ClickedAttendanceRoom.StudentInAttendanceDAO;
import com.nicanoritorma.qrattendance.ClickedAttendanceRoom.StudentInAttendanceDB;
import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;

import java.util.List;

public class StudentInAttendanceRepository {

    private StudentInAttendanceDAO studentInAttendanceDAO;

    public StudentInAttendanceRepository(Application application) {

        StudentInAttendanceDB database = StudentInAttendanceDB.getInstance(application);
        studentInAttendanceDAO = database.studentInAttendanceDAO();
    }

    public void insert(StudentInAttendanceModel student)
    {
        new InsertStudentInAttendanceAsyncTask(studentInAttendanceDAO).execute(student);
    }

    public void update(StudentInAttendanceModel student)
    {
        new UpdateStudentInAttendanceAsyncTask(studentInAttendanceDAO).execute(student);
    }

    public void delete(StudentInAttendanceModel student)
    {
        new DeleteStudentInAttendanceAsyncTask(studentInAttendanceDAO).execute(student);
    }

    public void deleteAllStudentInAttendance()
    {
        new DeleteAllStudentInAttendanceAsyncTask(studentInAttendanceDAO).execute();
    }

    public LiveData<List<StudentInAttendanceModel>> getStudentsInAttendance(SimpleSQLiteQuery query)
    {
        return studentInAttendanceDAO.getStudents(query);
    }

    public Cursor getAttendanceContent(int id)
    {
        return studentInAttendanceDAO.getAttendanceContent(id);
    }

    private static class InsertStudentInAttendanceAsyncTask extends AsyncTask<StudentInAttendanceModel, Void, Void> {
        private StudentInAttendanceDAO student;

        private InsertStudentInAttendanceAsyncTask(StudentInAttendanceDAO student)
        {
            this.student = student;
        }

        @Override
        protected Void doInBackground(StudentInAttendanceModel... studentInAttendanceModels) {
            student.insert(studentInAttendanceModels[0]);
            return null;
        }
    }

    private static class UpdateStudentInAttendanceAsyncTask extends AsyncTask<StudentInAttendanceModel, Void, Void> {
        private StudentInAttendanceDAO student;

        private UpdateStudentInAttendanceAsyncTask(StudentInAttendanceDAO student)
        {
            this.student = student;
        }

        @Override
        protected Void doInBackground(StudentInAttendanceModel... studentInAttendanceModels) {
            student.update(studentInAttendanceModels[0]);
            return null;
        }
    }

    private static class DeleteStudentInAttendanceAsyncTask extends AsyncTask<StudentInAttendanceModel, Void, Void> {
        private StudentInAttendanceDAO student;

        private DeleteStudentInAttendanceAsyncTask(StudentInAttendanceDAO student)
        {
            this.student = student;
        }

        @Override
        protected Void doInBackground(StudentInAttendanceModel... studentInAttendanceModels) {
            student.delete(studentInAttendanceModels[0]);
            return null;
        }
    }

    private static class DeleteAllStudentInAttendanceAsyncTask extends AsyncTask<Void, Void, Void> {
        private StudentInAttendanceDAO student;

        private DeleteAllStudentInAttendanceAsyncTask(StudentInAttendanceDAO student)
        {
            this.student = student;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            student.deleteAllStudent();
            return null;
        }
    }
}
