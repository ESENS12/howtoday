package com.esens.howtoday;

import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private TextView timeTextView;
    private TimePickerDialog tpd;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timepicker_layout, container, false);

        // Find our View instances
        timeTextView = view.findViewById(R.id.time_textview);
        Button timeButton = view.findViewById(R.id.time_button);

        // Show a timepicker when the timeButton is clicked
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                Log.d("ESENS","timeButton OnClick!");

                if (tpd == null) {
                    Log.d("ESENS","tpd is null called timepickerDialog");
                    tpd = TimePickerDialog.newInstance(
                            TimePickerFragment.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            0, //초기화 할때 0으로 줘도 됨.
                            //now.get(Calendar.MINUTE),
                            //mode24Hours.isChecked()
                            false
                    );
                    //tpd 시작시 보여지는 시간, 여기서 따로 제어 해도 되지만 deprecated 그러므로 init할 때 지정하는 방식이 권유됨
                    //tpd.setStartTime(now.get(Calendar.HOUR_OF_DAY),0,0);

                } else {
                    //Log.d("ESENS","tpd is not null");
                    //앱이 죽기 전까지는 살아 있으므로.. 단순히 그냥 인스턴스를 새로 만들어야 하냐 마냐 의 차이임
                    tpd.initialize(
                            TimePickerFragment.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            0,
                            //now.get(Calendar.MINUTE),
                            now.get(Calendar.SECOND),
                            false
                    );
                }
                //minute 은 안씀
                tpd.enableMinutes(false);
                //어두운 테마(색이 전부 바뀌지는 않음, 시계부분만)
                tpd.setThemeDark(false);
                tpd.vibrate(true);
                //tpd.dismissOnPause(false);
                tpd.enableSeconds(false);
                //TimePickerDialog.Version.VERSION_2 :
                tpd.setVersion(TimePickerDialog.Version.VERSION_1);


                //custom theme
                //tpd.setAccentColor(Color.parseColor("#9C27B0"));

                //popup title
                tpd.setTitle("TimePicker Title");


                /*if (limitSelectableTimes.isChecked()) {
                    if (enableSeconds.isChecked()) {
                        tpd.setTimeInterval(3, 5, 10);
                    } else {
                        tpd.setTimeInterval(3, 5, 60);
                    }
                }*/

                //여기서 설정한 시간은 unable
                /*if (disableSpecificTimes.isChecked()) {
                    Timepoint[] disabledTimes = {
                            new Timepoint(10),
                            new Timepoint(10, 30),
                            new Timepoint(11),
                            new Timepoint(12, 30)
                    };
                    tpd.setDisabledTimes(disabledTimes);
                }*/

                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("ESENS", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        if(tpd != null) tpd.setOnTimeSetListener(this);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        //String secondString = second < 10 ? "0"+second : ""+second;
        //second는 보여줄 필요가 없음.
        String time = "선택한 시간 : "+hourString+"h"+minuteString+"m";
        timeTextView.setText(time);
    }
}
