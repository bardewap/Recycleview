package com.cbtech.learningpod.viewmodels.myStudentViewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.cbtech.learningpod.R;
import com.cbtech.learningpod.models.exploremodels.TeacherExploreModel;
import com.cbtech.learningpod.models.mystudentModel.StudentExploreModel;
import com.cbtech.learningpod.remote.Data.DataManager;
import com.cbtech.learningpod.remote.Data.ResponseHandler;
import com.cbtech.learningpod.remote.Data.ResponseProvider;
import com.cbtech.learningpod.views.ExploreView.ExploreTeacherFragment;
import com.cbtech.learningpod.views.MyCourseView.MyCourseFragment;
import com.cbtech.learningpod.views.MyStudentsView.MyStudentsFragment;

import org.json.JSONObject;

public class StudentsViewModel extends BaseObservable {
    private DataManager dataManager;
    private Context context;
    private FragmentManager fragmentManager;
    private StudentExploreModel studentExploreModel;

    public StudentsViewModel(Context context) {
        this.context = context;
        dataManager = new DataManager(context);
    }


    public StudentsViewModel(StudentExploreModel studentExploreModel) {
        this.studentExploreModel = studentExploreModel;
    }


    @Bindable
    public StudentExploreModel getStudentExploreModel() {
        return studentExploreModel;
    }

    public void setTeacherExploreModel(StudentExploreModel studentExploreModel) {
        this.studentExploreModel = studentExploreModel;
        notifyPropertyChanged(com.cbtech.learningpod.BR.studentExploreModel);
    }

    public void getStudentData(final ResponseHandler responseHandler) {
        dataManager.getStudentExploreRequest(new ResponseProvider() {
            @Override
            public void onSuccessResponse(JSONObject response) {
                responseHandler.handleSucessResponse(response);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                responseHandler.handleErrorResponse(error);
            }
        });
    }


//    public void deleteStudentData(String StudentId, final ResponseHandler responseHandler) {
//        dataManager.deleteStudentRequest(StudentId, new ResponseProvider() {
//            @Override
//            public void onSuccessResponse(JSONObject response) {
//                responseHandler.handleSucessResponse(response);
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                responseHandler.handleErrorResponse(error);
//            }
//        });
//    }

    public void backToStudentPage() {

        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
        MyStudentsFragment tab1 = new MyStudentsFragment();
        manager.beginTransaction().replace(R.id.content_frame,tab1)
                .commit();

    }


}
