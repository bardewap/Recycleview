package com.cbtech.learningpod.mydynamiccalendar;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cbtech.learningpod.R;

import java.util.ArrayList;

/**
 * Created by HCL on 01-10-2016.
 */
public class ShowWeekViewEventsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ShowEventsModel> showEventsModelList;
    //private ArrayList<EventModel> showEventsModelList;
    private OnWeekDayViewClickListener onWeekDayViewClickListener;

    public ShowWeekViewEventsListAdapter(Context context, ArrayList<ShowEventsModel> showEventsModelList) {
        this.context = context;
        this.showEventsModelList = showEventsModelList;
    }

    public void setOnWeekDayViewClickListener(OnWeekDayViewClickListener onWeekDayViewClickListener) {
        this.onWeekDayViewClickListener = onWeekDayViewClickListener;
    }

    class ShowEventsViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_parrent;

        public ShowEventsViewHolder(View itemView) {
            super(itemView);
            ll_parrent = (LinearLayout) itemView.findViewById(R.id.ll_parrent);

        }

        public void setShowEvents(final ShowEventsModel model) {

        //    ll_parrent.removeAllViews();

            final View row_event_layout = LayoutInflater.from(context).inflate(R.layout.calendar_day_week_event, null, true);

            TextView tv_event_simbol = (TextView) row_event_layout.findViewById(R.id.tv_event_simbol);
            TextView tv_event_name = (TextView) row_event_layout.findViewById(R.id.tv_event_name);
            TextView tv_event_date = (TextView) row_event_layout.findViewById(R.id.tv_event_date);
            TextView tv_event_time = (TextView) row_event_layout.findViewById(R.id.tv_event_time);
            TextView tv_event_count = (TextView) row_event_layout.findViewById(R.id.tv_event_count);
            LinearLayout ll_month_events = (LinearLayout) row_event_layout.findViewById(R.id.ll_month_events);
            View v_divider = (View) row_event_layout.findViewById(R.id.v_divider);

            v_divider.setVisibility(View.GONE);

            // set event color
            int count = 0;
            for (int i = 0; i < AppConstants.eventList.size(); i++) {

                if (AppConstants.eventList.get(i).getStrDate().equals(AppConstants.sdfDate.format(model.getDates()))
                        && GlobalMethods.convertDate(AppConstants.eventList.get(i).getStrStartTime(), AppConstants.sdfHourMinute, AppConstants.sdfHour).equals(GlobalMethods.convertDate(model.getHours(), AppConstants.sdfHourMinute, AppConstants.sdfHour))) {
                    count++;
                    final EventModel eventModel = new EventModel(AppConstants.eventList.get(i).getStrDate(), AppConstants.eventList.get(i).getStrStartTime(), AppConstants.eventList.get(i).getStrEndTime(), AppConstants.eventList.get(i).getStrName());
                    tv_event_name.setText(AppConstants.eventList.get(i).getStrName());
                    tv_event_name.setVisibility(View.GONE);
                    tv_event_date.setText(AppConstants.eventList.get(i).getStrDate());
                    tv_event_date.setVisibility(View.GONE);
                    tv_event_time.setText(String.format("%s to %s", AppConstants.eventList.get(i).getStrStartTime(), AppConstants.eventList.get(i).getStrEndTime()));
                    tv_event_time.setVisibility(View.GONE);
                    if (AppConstants.eventList.get(i).getImage() != -1) {
                        tv_event_simbol.setBackgroundResource(AppConstants.eventList.get(i).getImage());
                    } else {
                        tv_event_simbol.setBackgroundResource(R.drawable.event_view);
                    }
                    if (AppConstants.eventCellBackgroundColor != -1) {
                        ll_month_events.setBackgroundColor(AppConstants.eventCellBackgroundColor);
                    }

                    if (!AppConstants.strEventCellBackgroundColor.equals("null")) {
                        //  ll_month_events.setBackgroundColor(Color.parseColor(AppConstants.strEventCellBackgroundColor));
                        //  ll_month_events.setBackgroundColor(AppConstants.eventCellBackgroundColor);
                    }

                    if (AppConstants.eventCellTextColor != -1) {
                        tv_event_name.setTextColor(AppConstants.eventCellTextColor);
                        tv_event_date.setTextColor(AppConstants.eventCellTextColor);
                        tv_event_time.setTextColor(AppConstants.eventCellTextColor);
                    }

                    if (!AppConstants.strEventCellTextColor.equals("null")) {
                        tv_event_name.setTextColor(Color.parseColor(AppConstants.strEventCellTextColor));
                        tv_event_date.setTextColor(Color.parseColor(AppConstants.strEventCellTextColor));
                        tv_event_time.setTextColor(Color.parseColor(AppConstants.strEventCellTextColor));
                    }

                    ll_parrent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onWeekDayViewClickListener.onClick(eventModel);
                        }
                    });
                }
            }
            if (count > 1) {
                tv_event_count.setTextColor(Color.BLUE);
                tv_event_count.setText(String.valueOf(count));
            }

            ll_parrent.addView(row_event_layout);
        }

        public void setShowEvents(final ShowEventsModel model, final EventModel eventModel) {
         //   ll_parrent.removeAllViews();
            final View row_event_layout = LayoutInflater.from(context).inflate(R.layout.calendar_day_week_event, null, true);

            TextView tv_event_simbol = (TextView) row_event_layout.findViewById(R.id.tv_event_simbol);
            TextView tv_event_name = (TextView) row_event_layout.findViewById(R.id.tv_event_name);
            TextView tv_event_date = (TextView) row_event_layout.findViewById(R.id.tv_event_date);
            TextView tv_event_time = (TextView) row_event_layout.findViewById(R.id.tv_event_time);
            TextView tv_event_count = (TextView) row_event_layout.findViewById(R.id.tv_event_count);
            LinearLayout ll_month_events = (LinearLayout) row_event_layout.findViewById(R.id.ll_month_events);
            View v_divider = (View) row_event_layout.findViewById(R.id.v_divider);

            v_divider.setVisibility(View.GONE);

            // set event color
            int count = 0;
            for (int i = 0; i < AppConstants.eventList.size(); i++) {
                if (AppConstants.eventList.get(i).getStrDate().equals(AppConstants.sdfDate.format(model.getDates()))
                        && GlobalMethods.convertDate(AppConstants.eventList.get(i).getStrStartTime(), AppConstants.sdfHourMinute, AppConstants.sdfHour).equals(GlobalMethods.convertDate(model.getHours(), AppConstants.sdfHourMinute, AppConstants.sdfHour))) {

                    //final EventModel eventModel = new EventModel(AppConstants.eventList.get(i).getStrDate(), AppConstants.eventList.get(i).getStrStartTime(), AppConstants.eventList.get(i).getStrEndTime(), AppConstants.eventList.get(i).getStrName());
                    tv_event_name.setText(eventModel.getStrName());
                    tv_event_name.setVisibility(View.GONE);
                    tv_event_date.setText(eventModel.getStrDate());
                    tv_event_date.setVisibility(View.GONE);
                    tv_event_time.setText(String.format("%s to %s", eventModel.getStrStartTime(), eventModel.getStrEndTime()));
                    tv_event_time.setVisibility(View.GONE);
                    if (eventModel.getImage() != -1) {
                        tv_event_simbol.setBackgroundResource(AppConstants.eventList.get(i).getImage());
                    } else {
                        tv_event_simbol.setBackgroundResource(R.drawable.event_view);
                    }
                    if (AppConstants.eventCellBackgroundColor != -1) {
                        ll_month_events.setBackgroundColor(AppConstants.eventCellBackgroundColor);
                    }

                    if (!AppConstants.strEventCellBackgroundColor.equals("null")) {
                        //     ll_month_events.setBackgroundColor(Color.parseColor(AppConstants.strEventCellBackgroundColor));
//                        ll_month_events.setBackgroundColor(AppConstants.eventCellBackgroundColor);
                    }

                    if (AppConstants.eventCellTextColor != -1) {
                        tv_event_name.setTextColor(AppConstants.eventCellTextColor);
                        tv_event_date.setTextColor(AppConstants.eventCellTextColor);
                        tv_event_time.setTextColor(AppConstants.eventCellTextColor);
                    }

                    if (!AppConstants.strEventCellTextColor.equals("null")) {
                        tv_event_name.setTextColor(Color.parseColor(AppConstants.strEventCellTextColor));
                        tv_event_date.setTextColor(Color.parseColor(AppConstants.strEventCellTextColor));
                        tv_event_time.setTextColor(Color.parseColor(AppConstants.strEventCellTextColor));
                    }
                    row_event_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onWeekDayViewClickListener.onClick(eventModel);
                        }
                    });
                }
            }
            ll_parrent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onWeekDayViewClickListener.onClick(AppConstants.eventList);
                }
            });
            ll_parrent.addView(row_event_layout);

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.calendar_row_week_view_show_events, parent, false);
        return new ShowEventsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShowEventsModel showEventsModel = showEventsModelList.get(position);
        if(showEventsModel.getEventModelArrayList().size() >= 1){
            for(int i = 0; i < showEventsModel.getEventModelArrayList().size(); i++){
                ShowEventsViewHolder showEventsViewHolder = (ShowEventsViewHolder) holder;
                showEventsViewHolder.setShowEvents(showEventsModel, showEventsModel.getEventModelArrayList().get(i));
            }
        }else{
            ShowEventsViewHolder showEventsViewHolder = (ShowEventsViewHolder) holder;
            showEventsViewHolder.setShowEvents(showEventsModel);
        }

    }

    @Override
    public int getItemCount() {
        return showEventsModelList.size();
    }
}
