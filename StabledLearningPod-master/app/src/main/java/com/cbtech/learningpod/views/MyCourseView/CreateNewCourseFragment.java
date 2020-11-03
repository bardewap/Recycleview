package com.cbtech.learningpod.views.MyCourseView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.cbtech.learningpod.Constants.StringConstants;
import com.cbtech.learningpod.Constants.Utilities;
import com.cbtech.learningpod.R;
import com.cbtech.learningpod.SharedPreference.Credentials;
import com.cbtech.learningpod.databinding.CreateNewCourseBinding;
import com.cbtech.learningpod.models.affiliationsModel.AffiliationRequestDetails;
import com.cbtech.learningpod.models.affiliationsModel.AffiliationRequestsModel;
import com.cbtech.learningpod.remote.Data.ResponseHandler;
import com.cbtech.learningpod.viewmodels.courseViewModels.CreateNewCourseViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateNewCourseFragment extends Fragment {
    private CreateNewCourseBinding createNewCourseBinding;
    private CreateNewCourseViewModel createNewCourseViewModel;
    private Gson gson;
    private GsonBuilder gsonBuilder;
    private ProgressDialog progressDialog;

    public CreateNewCourseFragment() {
        // Required empty public constructor
    }

    public static CreateNewCourseFragment newInstance() {
        CreateNewCourseFragment fragment = new CreateNewCourseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNewCourseViewModel = new CreateNewCourseViewModel(getContext(), getActivity().getSupportFragmentManager());
        progressDialog = new ProgressDialog(getContext());
        initMyaffiliationList();
        gsonBuilder = new GsonBuilder();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createNewCourseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_new_course, container, false);
        createNewCourseBinding.courseFrequencySpinner.setItems(Utilities.getWeekDays(), "Select Frequency");


        createNewCourseBinding.courseSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewCourseViewModel.setSelectedFrequences(createNewCourseBinding.courseFrequencySpinner.getSelectedItemsAsString());
                if (createNewCourseViewModel.validateFields().equalsIgnoreCase("true")) {
                    progressDialog.setMessage("Creating course...");
                    progressDialog.show();
                    //Log.d(createNewCourseBinding.courseFrequencySpinner.getSelectedItemsAsString(), createNewCourseViewModel.toString());
                    createNewCourseViewModel.createNewCourse(createNewCourseViewModel, new ResponseHandler() {
                        @Override
                        public void handleSucessResponse(JSONObject response) {
                            try {
                                Log.d("Response is ", response.toString());
                                if (response.getString(StringConstants.STATUS).equalsIgnoreCase(StringConstants.SUCCESS)) {
                                    Toast.makeText(getContext(), response.getString(StringConstants.MESSAGE), Toast.LENGTH_SHORT).show();
                                    createNewCourseViewModel.backToCoursePage();
                                } else if (response.getString(StringConstants.STATUS).equalsIgnoreCase(StringConstants.ERROR)) {
                                    Toast.makeText(getContext(), response.getString(StringConstants.MESSAGE), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.hide();
                                progressDialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void handleErrorResponse(VolleyError error) {
                            Utilities.handleHttpError(getContext(), error);
                            progressDialog.hide();
                            progressDialog.dismiss();
                        }
                    });
                }else {
                    Toast.makeText(getContext(), createNewCourseViewModel.validateFields(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return createNewCourseBinding.getRoot();
    }

    public void initMyaffiliationList(){
        progressDialog.setMessage(StringConstants.LOADING_MESSEGE);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        createNewCourseViewModel.getMyAffiliations(new ResponseHandler() {
            @Override
            public void handleSucessResponse(JSONObject response) {
                gson = gsonBuilder.create();
                AffiliationRequestDetails affiliationRequestDetails = gson.fromJson(response.toString(), AffiliationRequestDetails.class);
                //Log.d("affiliationReqDetails ", affiliationRequestDetails.toString());

                List<String> myAffiliationList = new ArrayList<>();
                myAffiliationList.add("None");

                if(affiliationRequestDetails.getAffiliationRequestsModels() != null) {

                    createNewCourseViewModel.setMyAffiliationsModelList(affiliationRequestDetails.getAffiliationRequestsModels());

                    if(createNewCourseViewModel.getCredentials().getUserRole().equalsIgnoreCase(StringConstants.TEACHER)){
                        for(int i = 0; i < affiliationRequestDetails.getAffiliationRequestsModels().size(); i++){
                            myAffiliationList.add(affiliationRequestDetails.getAffiliationRequestsModels().get(i).getCcModel().getCcName());
                        }
                    }else if(createNewCourseViewModel.getCredentials().getUserRole().equalsIgnoreCase(StringConstants.COACHING_CLASS)){
                        for(int i = 0; i < affiliationRequestDetails.getAffiliationRequestsModels().size(); i++){
                            myAffiliationList.add(affiliationRequestDetails.getAffiliationRequestsModels().get(i).getTeacherModel().getTeacherName());
                        }
                    }

                    //Log.d("ApprovedAffiliations", affiliationRequestDetails.toString());
                }

                createNewCourseViewModel.setMyAffiliationsList(myAffiliationList);
                createNewCourseBinding.setCreateNewCourse(createNewCourseViewModel);

                if(createNewCourseViewModel.getCredentials().getUserRole().equalsIgnoreCase(StringConstants.COACHING_CLASS) && createNewCourseViewModel.getMyAffiliationsList() != null && createNewCourseViewModel.getMyAffiliationsList().size() <= 1 ){
                    createNewCourseBinding.parentLayout.setVisibility(View.GONE);
                    createNewCourseBinding.showMesseageLayout.setVisibility(View.VISIBLE);
                }

                //createNewCourseBinding.courseMyAffiliationSpinner.setItems(createNewCourseViewModel.getMyAffiliationsList(), "Select Affiliations");
                progressDialog.hide();
                progressDialog.dismiss();
            }

            @Override
            public void handleErrorResponse(VolleyError error) {
                Utilities.handleHttpError(getContext(), error);
                progressDialog.hide();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Course");
    }
}
