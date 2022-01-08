package com.nicanoritorma.qrattendance.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.nicanoritorma.qrattendance.BaseActivity;
import com.nicanoritorma.qrattendance.model.StudentModel;
import com.nicanoritorma.qrattendance.room.StudentDAO;
import com.nicanoritorma.qrattendance.room.StudentDB;

import java.util.List;

public class StudentRepo extends BaseActivity {
    private StudentDAO studentDAO;
    private LiveData<List<StudentModel>> allStudent;

    public StudentRepo(Application application) {
        StudentDB database = StudentDB.getInstance(application);
        studentDAO = database.studentDAO();
        allStudent = studentDAO.getStudentList();
    }

    public void insert(StudentModel student) {
        new InsertStudentAsyncTask(studentDAO).execute(student);
    }

    public void update(StudentModel student) {
        new UpdateStudentAsyncTask(studentDAO).execute(student);
    }

    public void delete(StudentModel student) {
        new DeleteStudentAsyncTask(studentDAO).execute(student);
    }

    public LiveData<List<StudentModel>> getAllStudent() {
        return allStudent;
    }

    private static class InsertStudentAsyncTask extends AsyncTask<StudentModel, Void, Void> {
        private StudentDAO studentDAO;

        private InsertStudentAsyncTask(StudentDAO studentDAO) {
            this.studentDAO = studentDAO;
        }

        @Override
        protected Void doInBackground(StudentModel... student) {
            studentDAO.insert(student[0]);
            return null;
        }
    }

    private static class UpdateStudentAsyncTask extends AsyncTask<StudentModel, Void, Void> {
        private StudentDAO studentDAO;

        private UpdateStudentAsyncTask(StudentDAO studentDAO) {
            this.studentDAO = studentDAO;
        }

        @Override
        protected Void doInBackground(StudentModel... student) {
            studentDAO.update(student[0]);
            return null;
        }
    }

    private static class DeleteStudentAsyncTask extends AsyncTask<StudentModel, Void, Void> {
        private StudentDAO studentDAO;

        private DeleteStudentAsyncTask(StudentDAO studentDAO) {
            this.studentDAO = studentDAO;
        }

        @Override
        protected Void doInBackground(StudentModel... student) {
            studentDAO.delete(student[0]);
            return null;
        }
    }

//    private class getStudentList extends AsyncTask<String, Void, Void>
//    {
//        @Override
//        protected Void doInBackground(String... strings) {
//            GetStudent getStudent = new GetStudent(application, getDbURl() + "GetStudentList.php");
//            getStudent.getStudentList(new GetStudent.ResponseListener() {
//                @Override
//                public void onResponse(List<StudentModel> data) {
//                    studentList = getStudent.getList();
//                    Log.d( "doInBackground: ", String.valueOf(studentList.size()));
//                }
//
//                @Override
//                public void onError(String message) {
//
//                }
//            });
//            Log.d( "doInBackground: ", String.valueOf(studentList.size()));
//            return null;
//        }
//    }
//
//    public List<StudentModel> getStudentList()
//    {
//        new getStudentList().execute();
//        return studentList;
//    }
//
//    //function to put studentData in online database
//    public void insert(Application application, String fullname, String idNum, String dept, String qrCode) {
//        //TODO: change URL on deployment, url found in BaseActivity
//
//        String URL = getDbURl() + "AddQrToDb.php";
//
//        Handler handler = new Handler();
//        handler.post(() -> {
//            String[] field = new String[4];
//            field[0] = "fullname";
//            field[1] = "idNumber";
//            field[2] = "department";
//            field[3] = "qrCode";
//
//            String[] data = new String[4];
//            data[0] = fullname;
//            data[1] = idNum;
//            data[2] = dept;
//            data[3] = qrCode;
//
//            PutData putData = new PutData(URL, "POST", field, data);
//            if (putData.startPut()) {
//                if (putData.onComplete()) {
//                    Toast.makeText(application.getApplicationContext(), putData.getResult(), Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(application.getApplicationContext(), "Try again later", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
}
