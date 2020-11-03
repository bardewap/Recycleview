package com.cbtech.learningpod.adapters.recordingsAdapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cbtech.learningpod.Constants.StringConstants;
import com.cbtech.learningpod.R;
import com.cbtech.learningpod.adapters.myStudentAdapter.StudentViewAdapter;
import com.cbtech.learningpod.databinding.RecordingViewBindingItem;
import com.cbtech.learningpod.databinding.StudentViewBindingItem;
import com.cbtech.learningpod.models.exploremodels.TeacherExploreModel;
import com.cbtech.learningpod.models.mystudentModel.StudentExploreModel;
import com.cbtech.learningpod.models.recordingsModel.RecordingExploreModel;
import com.cbtech.learningpod.remote.APICall;
import com.cbtech.learningpod.remote.VolleySinglton;
import com.cbtech.learningpod.viewmodels.myStudentViewModel.StudentsViewModel;
import com.cbtech.learningpod.viewmodels.recordingViewModel.RecordingViewModel;
import com.cbtech.learningpod.views.AddTeacherView.UpdateTeacherFragment;
import com.cbtech.learningpod.views.RecordingView.RecordingDetailsFragments;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordingAdapter extends RecyclerView.Adapter<RecordingAdapter.RecordingViewHolder> {


    private Context context;
    private LayoutInflater layoutInflater;
    private List<RecordingViewModel> exploreViewModels;
    private RecordingViewModel recordingViewModel;

    public RecordingAdapter(Context context, List<RecordingViewModel> exploreViewModels, RecordingViewModel recordingViewModel) {
        this.exploreViewModels = exploreViewModels;
        this.context = context;
        this.recordingViewModel = recordingViewModel;


        Log.d("RECORDING_ADAPTER", "StudentViewAdapter: " + recordingViewModel);


    }


    @Override
    public RecordingViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        RecordingViewBindingItem recordingBinding = RecordingViewBindingItem.inflate(layoutInflater, viewGroup, false);
        return new RecordingViewHolder(recordingBinding);


    }

    @Override
    public void onBindViewHolder(RecordingViewHolder recordingViewHolder, int i) {

        final RecordingViewModel recordingViewModel = exploreViewModels.get(i);

        recordingViewHolder.bind(recordingViewModel.getRecordingExploreModel());

        Log.d("POSITIONS", "onBindViewHolder: "+exploreViewModels.get(i));



        recordingViewHolder.recordingViewBindingItem.textView84.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recordingDetails(recordingViewModel.getRecordingExploreModel());

            }
        });


    }

    @Override
    public int getItemCount() {

        Log.d("SIZE", "getItemCount: "+exploreViewModels.size());

        return exploreViewModels.size();
    }

    class RecordingViewHolder extends RecyclerView.ViewHolder {

        private RecordingViewBindingItem recordingViewBindingItem;

        RecordingViewHolder(@NonNull final RecordingViewBindingItem recordingBinding) {
            super(recordingBinding.getRoot());
            this.recordingViewBindingItem = recordingBinding;
        }

        void bind(RecordingExploreModel recordingViewBindingItem) {
            this.recordingViewBindingItem.setRecordingView(recordingViewBindingItem);
        }

        RecordingViewBindingItem getRecordingBinding() {
            return recordingViewBindingItem;
        }
    }




    private void recordingDetails(RecordingExploreModel recordingExploreModel) {

        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
        Fragment fragment= RecordingDetailsFragments.newInstance(recordingExploreModel);
        // UpdateTeacherFragment tab1 = new UpdateTeacherFragment();
        manager.beginTransaction().replace(R.id.content_frame,fragment)
                .commit();



    }


}
