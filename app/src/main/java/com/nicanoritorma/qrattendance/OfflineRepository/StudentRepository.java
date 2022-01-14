package com.nicanoritorma.qrattendance.OfflineRepository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.nicanoritorma.qrattendance.model.StudentModel;
import com.nicanoritorma.qrattendance.StudentRoom.StudentDAO;
import com.nicanoritorma.qrattendance.StudentRoom.StudentDB;

import java.util.List;

public class StudentRepository {

    private StudentDAO studentDAO;
    private LiveData<List<StudentModel>> allStudent;

    public StudentRepository(Application application)
    {
        StudentDB database = StudentDB.getInstance(application);
        studentDAO = database.studentDAO();
        allStudent = studentDAO.getAllStudent();
    }

    public void insert(StudentModel student)
    {
        new InsertStudentAsyncTask(studentDAO).execute(student);
    }

    public void update(StudentModel student)
    {
        new InsertStudentAsyncTask(studentDAO).execute(student);
    }

    public void delete(StudentModel student)
    {
        new InsertStudentAsyncTask(studentDAO).execute(student);
    }

    public void deleteAllStudent()
    {
        new DeleteAllStudentAsyncTask(studentDAO).execute();
    }

    public LiveData<List<StudentModel>> getAllStudent() {
        return allStudent;
    }

    private static class InsertStudentAsyncTask extends AsyncTask<StudentModel, Void, Void> {
        private StudentDAO studentDAO;

        private InsertStudentAsyncTask(StudentDAO studentDAO)
        {
            this.studentDAO = studentDAO;
        }

        @Override
        protected Void doInBackground(StudentModel... studentModels) {
            studentDAO.insert(studentModels[0]);
            return null;
        }
    }

    private static class UpdateStudentAsyncTask extends AsyncTask<StudentModel, Void, Void> {
        private StudentDAO studentDAO;

        private UpdateStudentAsyncTask(StudentDAO studentDAO)
        {
            this.studentDAO = studentDAO;
        }

        @Override
        protected Void doInBackground(StudentModel... studentModels) {
            studentDAO.update(studentModels[0]);
            return null;
        }
    }

    private static class DeleteStudentAsyncTask extends AsyncTask<StudentModel, Void, Void> {
        private StudentDAO studentDAO;

        private DeleteStudentAsyncTask(StudentDAO studentDAO)
        {
            this.studentDAO = studentDAO;
        }

        @Override
        protected Void doInBackground(StudentModel... studentModels) {
            studentDAO.delete(studentModels[0]);
            return null;
        }
    }

    private static class DeleteAllStudentAsyncTask extends AsyncTask<Void, Void, Void> {
        private StudentDAO studentDAO;

        private DeleteAllStudentAsyncTask(StudentDAO studentDAO)
        {
            this.studentDAO = studentDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentDAO.deleteAllStudent();
            return null;
        }
    }
}
