package com.retailstreet.mobilepos.View.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.ramijemli.percentagechartview.PercentageChartView;
import com.retailstreet.mobilepos.R;

/**
 * Created by Mayank Choudhary on 19-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class ProgressBarDialog extends Dialog {

        public Activity c;
        public static PercentageChartView progressBar;
        public  TextView textView;

        public ProgressBarDialog(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.progress_bar_dialog);
           getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressBar= findViewById(R.id.dload_progress);
            textView= findViewById(R.id.progress_status);
            textView.setText("Initializing Download...");
            progressBar.textShadow(Color.BLUE, 2f, 2f, 2f)
                    .apply();
            setCancelable(false);
        }


        public  void updateProgress(int states){
            try {
                progressBar.setProgress(states,true);
                textView.setText("Downloading Database...");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

