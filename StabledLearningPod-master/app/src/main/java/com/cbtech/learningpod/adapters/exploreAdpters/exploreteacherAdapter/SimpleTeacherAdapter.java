package com.cbtech.learningpod.adapters.exploreAdpters.exploreteacherAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbtech.learningpod.databinding.TeacherModelBinding;
import com.cbtech.learningpod.interfaces.OnTeacherNameClick;
import com.cbtech.learningpod.models.TeacherModel;
import com.cbtech.learningpod.models.exploremodels.CCTeacherInfo;
import com.cbtech.learningpod.viewmodels.exploreViewModels.TeacherViewModel;

import java.util.List;

public class SimpleTeacherAdapter extends RecyclerView.Adapter<SimpleTeacherAdapter.TeacherAdapter> {
    private List<CCTeacherInfo> ccTeacherInfoList;
    private LayoutInflater layoutInflater;
    public SimpleTeacherAdapter(List<CCTeacherInfo> ccTeacherInfoList) {
        this.ccTeacherInfoList = ccTeacherInfoList;
    }

    @Override
    public TeacherAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        TeacherModelBinding teacherBinding = TeacherModelBinding.inflate(layoutInflater, parent, false);
        return new TeacherAdapter(teacherBinding);
    }

    @Override
    public void onBindViewHolder(TeacherAdapter holder, int position) {
        //CCTeacherInfo ccTeacherInfo = ccTeacherInfoList.get(position);
        TeacherModel teacherModel = ccTeacherInfoList.get(position).getTeacherModel();
        if(teacherModel != null){
            if(teacherModel.getEmail() == null || teacherModel.getEmail().isEmpty()){
                holder.getTeacherBinding().email.setVisibility(View.GONE);
            }

            if(teacherModel.getQualifications() == null || teacherModel.getQualifications().isEmpty()){
                holder.getTeacherBinding().qualifiaction.setVisibility(View.GONE);
            }
        }
        holder.bind(teacherModel);
    }

    @Override
    public int getItemCount() {
        if(ccTeacherInfoList == null){
            return 0;
        }
        return ccTeacherInfoList.size();
    }

    class TeacherAdapter extends RecyclerView.ViewHolder{
        TeacherModelBinding teacherModelBinding;
        public TeacherAdapter(TeacherModelBinding teacherModelBinding) {
            super(teacherModelBinding.getRoot());
            this.teacherModelBinding = teacherModelBinding;
        }

        void bind(TeacherModel teacherModel){
            this.teacherModelBinding.setTeacherModel(teacherModel);
        }

        TeacherModelBinding getTeacherBinding(){
            return teacherModelBinding;
        }
    }
}
