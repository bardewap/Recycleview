package com.cbtech.learningpod.mydynamiccalendar;

import java.util.Date;

/**
 * Created by HCL on 04-10-2016.
 */
public interface OnDateClickListener {

    void onClick(Date date, int position);

    void onLongClick(Date date);

}
