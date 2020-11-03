package com.cbtech.learningpod.views.MyCourseView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cbtech.learningpod.Constants.StringConstants;
import com.cbtech.learningpod.Constants.Utilities;
import com.cbtech.learningpod.R;
import com.cbtech.learningpod.SharedPreference.Credentials;
import com.cbtech.learningpod.VideoUploadProcess.Upload;
import com.cbtech.learningpod.adapters.mycourseAdapter.MyCourseAdapter;
import com.cbtech.learningpod.databinding.MyCourseViewModelBinding;
import com.cbtech.learningpod.interfaces.OnMyCourseItemClick;
import com.cbtech.learningpod.models.course_details.SingleCourseModel;
import com.cbtech.learningpod.models.exploremodels.CourseExploreModel;
import com.cbtech.learningpod.remote.APICall;
import com.cbtech.learningpod.remote.Data.JSONArrayResponseHandler;
import com.cbtech.learningpod.remote.Data.ResponseHandler;
import com.cbtech.learningpod.remote.VolleyMultipartRequest;
import com.cbtech.learningpod.remote.VolleySinglton;
import com.cbtech.learningpod.viewmodels.courseViewModels.MyCourseViewModel;
import com.cbtech.learningpod.views.ExploreView.ExploreDetailsFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class MyCourseFragment extends Fragment {
    private static final int SELECT_VIDEO = 1;

    private String selectedVideoPath;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private String courseIdForUploadImage;

    private MyCourseViewModelBinding courseViewModelBinding;
    private MyCourseViewModel myCourseViewModel;

    private MyCourseAdapter myCourseAdapter;

    private List<CourseExploreModel> courseExploreModels;

    private Gson gson;
    private ProgressDialog progressDialog;

    private Credentials credentials;
    private FragmentTransaction ft;
    private Fragment fragment;
    GsonBuilder gsonBuilder;
    public static MyCourseFragment newInstance() {
        return new MyCourseFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseExploreModels = new ArrayList<>();
        myCourseViewModel = new MyCourseViewModel(getContext(), getActivity().getSupportFragmentManager());
        progressDialog = new ProgressDialog(getContext());
        gsonBuilder = new GsonBuilder();
        progressDialog.show();
        progressDialog.setMessage("Loading..");
        credentials = new Credentials(getContext());
        loadCourseData();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        courseViewModelBinding = DataBindingUtil.inflate(inflater, R.layout.my_course_fragment, container, false);
        if(credentials.getUserRole().equalsIgnoreCase(StringConstants.STUDENT)){
            courseViewModelBinding.newCourseBtn.setVisibility(View.GONE);
        }

        //Log.d("User role : " ,credentials.getUserRole());

       // Log.d("User res val ", credentials.getIsRestrictedTeacher());

        if(credentials.getUserRole().equalsIgnoreCase(StringConstants.TEACHER) ){
            courseViewModelBinding.newCourseBtn.setVisibility(View.GONE);
        }
        courseViewModelBinding.setMyCourseFragment(myCourseViewModel);

        courseViewModelBinding.executePendingBindings();

        initiateCourseList();

        /*courseViewModelBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                courseExploreModels.clear();
                loadCourseData();
                courseViewModelBinding.swipeContainer.setRefreshing(false);
            }
        });
        courseViewModelBinding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);*/
        return courseViewModelBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(StringConstants.MY_COURSES);
    }

    private void loadCourseData(){
        myCourseViewModel.getMyCourses(new JSONArrayResponseHandler() {
            @Override
            public void handleSucessResponse(JSONArray response) {
                try {
                    Log.d("MyCourse is ", response.toString());
                    if(response.length() <= 0){
                        courseViewModelBinding.myCourseIdNoDataFoundMSG.setVisibility(View.VISIBLE);
                    }else{
                        courseViewModelBinding.myCourseIdNoDataFoundMSG.setVisibility(View.GONE);
                    }
                    gson = gsonBuilder.create();
                    courseExploreModels.addAll(Arrays.asList(gson.fromJson(response.toString(), CourseExploreModel[].class)));
                    myCourseAdapter.notifyDataSetChanged();
                    progressDialog.hide();
                    progressDialog.dismiss();

                }catch (Exception e){
                    e.printStackTrace();
                }
                progressDialog.hide();
                progressDialog.dismiss();
            }

            @Override
            public void handleErrorResponse(VolleyError error) {
                progressDialog.hide();
                progressDialog.dismiss();
                //Log.d(StringConstants.ERROR_RESPONSE, error.getMessage());
                Utilities.handleHttpError(getContext(), error);
            }
        });
    }

    private void initiateCourseList(){
        myCourseAdapter = new MyCourseAdapter(credentials.getUserRole(), courseExploreModels, new OnMyCourseItemClick() {
            @Override
            public void onItemClick(final CourseExploreModel courseExploreModel) {
                Log.d("CourseExploreModel", courseExploreModel.toString());

                myCourseViewModel.getCourseDetails(courseExploreModel.getId(), new ResponseHandler() {
                    @Override
                    public void handleSucessResponse(JSONObject response) {

                         String ccReview = courseExploreModel.getCcReview();
                        String ccRating = courseExploreModel.getCcRating();

                        Log.d("Coaching Review ====>",ccReview);
                        Log.d("Coaching Rating =====>",ccRating);

                        Log.d("SingleCourse Details", response.toString());
                        JsonObject jsonObject = new JsonObject();
                        gson = gsonBuilder.create();
                        SingleCourseModel singleCourseModel = gson.fromJson(response.toString(), SingleCourseModel.class);
                        //Log.d("AfterGsonSingleCM ", singleCourseModel.toString());
                        progressDialog.hide();
                        progressDialog.dismiss();
                        showExploreDetailsFragment(singleCourseModel, courseExploreModel);

                    }

                    @Override
                    public void handleErrorResponse(VolleyError error) {
                        Log.d(StringConstants.ERROR_RESPONSE, error.toString());
                    }
                });
            }

            @Override
            public void onDeleteCourseClick(CourseExploreModel courseExploreModel) {
                    progressDialog.show();
                    progressDialog.setMessage(StringConstants.LOADING_MESSEGE_PLEASE_WAIT);
                    myCourseViewModel.deleteCourse(Integer.parseInt(courseExploreModel.getId()), new ResponseHandler() {
                        @Override
                        public void handleSucessResponse(JSONObject response) {
                            Log.d("Response is ", response.toString());
                            Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void handleErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            progressDialog.dismiss();
                            Utilities.handleHttpError(getContext(), error);

                        }
                    });
            }

            @Override
            public void onUploadVideoBtnClick(String courseId) {
                courseIdForUploadImage = courseId;
                if(checkPermission()){
                    pickVideoFromLoaclStorage();
                }else{
                    requestPermission();
                }
            }
        });

        courseViewModelBinding.myCourcesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        courseViewModelBinding.myCourcesRecyclerView.setAdapter(myCourseAdapter);
    }

    public void pickVideoFromLoaclStorage(){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        startActivityForResult(intent, SELECT_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                selectedVideoPath = getPath(data.getData());
                if(selectedVideoPath != null) {
                    progressDialog.setMessage("Video uploading please wait..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    //Toast.makeText(getContext(), selectedVideoPath, Toast.LENGTH_LONG).show();
                    uploadVideo( courseIdForUploadImage, selectedVideoPath);
                 }
            }
        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if(cursor!=null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

        else return null;
       /* Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;*/
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE );
        int result1 = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        //Snackbar.make(getView(), "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    }else {

                        //Snackbar.make(getView(), "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



    public void showExploreDetailsFragment(SingleCourseModel singleCourseModel, CourseExploreModel courseExploreModel){
        fragment = ExploreDetailsFragment.newInstance(singleCourseModel, StringConstants.MY_COURSE_FRAGMENT, courseExploreModel);
        if(getActivity() != null ) {
            ft = getActivity().getSupportFragmentManager().beginTransaction();
        }
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(StringConstants.MY_COURSE_DETAILS);
        ft.commit();
    }

    private byte[] getFileBytesArray(String mVideoURI){
        try{
            File file = new File(mVideoURI);
            byte[] bytesArray = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
            return  bytesArray;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void uploadVideo(final String courseId, final String mVideoURI){

        final String filename= mVideoURI.substring( mVideoURI.lastIndexOf("/") + 1 );
        System.out.println("Video Upload URL ==========>"+APICall.MY_COURSE_UPLOAD_VIDEO + courseId);
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, APICall.MY_COURSE_UPLOAD_VIDEO + courseId, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString(StringConstants.STATUS);
                    Log.d("Response ==========>", status);
                    Log.d("Response FULL ======>", response.toString());
                    progressDialog.hide();
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), status, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utilities.handleHttpError(getContext(), error);
                progressDialog.hide();
                progressDialog.dismiss();
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", credentials.getToken());
                return headers;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
               Map<String, DataPart> params = new HashMap<>();
               params.put("video", new DataPart(filename, getFileBytesArray(mVideoURI)));
               return params;
            }
        };

        VolleySinglton.getInstance(getContext()).addTORequestQueue(multipartRequest);
       /* class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(getContext(), "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
               *//* textViewResponse.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());*//*
            }

            @Override
            protected String doInBackground(Void... params) {
                System.out.println("Token Id"+credentials.getToken());
                Upload u = new Upload();
                String msg = u.uploadVideo(mVideoURI,courseId,credentials.getToken());
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
*/
    }

}
