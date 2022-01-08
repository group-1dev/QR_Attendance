package com.nicanoritorma.qrattendance.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.nicanoritorma.qrattendance.BaseActivity;
import com.nicanoritorma.qrattendance.api.GetStudentOnline;
import com.nicanoritorma.qrattendance.api.PutData;
import com.nicanoritorma.qrattendance.model.StudentModel;

import java.util.List;

public class OnlineStudentRepo extends BaseActivity{

    private static OnlineStudentRepo instance;
    private Application application;
    private GetStudentOnline studentList;

    public OnlineStudentRepo(Application application)
    {
        this.application = application;
        studentList = new GetStudentOnline(application);
    }

    public boolean insert(StudentModel student)
    {
        InsertStudentToDb insertStudentToDb = (InsertStudentToDb) new InsertStudentToDb(student.getName(), student.getIdNum(), student.getCollege(), student.getQrCode()).execute();
        return insertStudentToDb.doInBackground();
    }

    static class InsertStudentToDb extends AsyncTask<Void, Void, Boolean>
    {
        String url = getDbUrl() + "AddQrToDb.php";
        String fullname, idNumber, dept, qrCode;

        public InsertStudentToDb(String fullname, String idNumber, String dept, String qrCode) {
            this.fullname = fullname;
            this.idNumber = idNumber;
            this.dept = dept;
            this.qrCode = qrCode;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String[] field = new String[4];
            field[0] = "fullname";
            field[1] = "idNumber";
            field[2] = "department";
            field[3] = "qrCode";

            String[] data = new String[4];
            data[0] = fullname;
            data[1] = idNumber;
            data[2] = dept;
            data[3] = qrCode;

            //TODO: change URL address
            PutData putData = new PutData(url, "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    if (result.equals("Success"))
                    {
                        return true;
                    }
                }
            }
            return null;
        }
    }

    public LiveData<List<StudentModel>> getStudentList()
    {
        return studentList.getStudentList();
    }
}
