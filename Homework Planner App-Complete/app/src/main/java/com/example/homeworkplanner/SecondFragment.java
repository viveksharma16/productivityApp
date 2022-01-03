package com.example.homeworkplanner;


import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class SecondFragment extends Fragment {
    CalendarView hwCalendar;
    Button btnAddSchedule;
    Button btnSaveSchedule;
    EditText editTextSchedule;
    ListView lstViewSchedule;
    String txtScheduleInp;
    int dayScheduledInp;
    int dayInCalendar;
    Button btnRemoveEvent;
    Button btnCancelRemove;
    ArrayList<String> lstAssignments = new ArrayList<>();
    ArrayList<Integer> lstDates = new ArrayList<>();
    NotificationManager notificationManager;
    mySQLiteDBHandler dbHandler;
    private SQLiteDatabase sqLiteDatabase;

    public SecondFragment() {

// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second, container, false);

        lstViewSchedule = (ListView) v.findViewById(R.id.lstViewSchedule);

        ArrayList<String> lstSchedule = new ArrayList<String>();

//        System.out.println(lstAssignments);

        ArrayAdapter<String> lstViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lstSchedule){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Cast the list view each item as text view
                TextView item = (TextView) super.getView(position,convertView,parent);

                // Set the typeface/font for the current item

                // Set the list view item's text color
                item.setTextColor(Color.parseColor("#17354f"));

                // Set the item text style to bold
                item.setTypeface(item.getTypeface(), Typeface.ITALIC);

                // Change the item text size
                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);

                // return the view
                return item;
            }
        };
        lstViewAdapter.notifyDataSetChanged();

        btnRemoveEvent=v.findViewById(R.id.btnRemoveEvent);
        btnCancelRemove=v.findViewById(R.id.btnCancelRemove);
        btnAddSchedule=v.findViewById(R.id.btnAddSchedule);
        btnSaveSchedule=v.findViewById(R.id.btnSaveSchedule);
        editTextSchedule=v.findViewById(R.id.editTextSchedule);
        hwCalendar = (CalendarView)v.findViewById(R.id.hwCalendar); // get the reference of CalendarView
        hwCalendar.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
        hwCalendar.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
        hwCalendar.setSelectedWeekBackgroundColor(Color.RED); // red color for the selected week's background
        hwCalendar.setWeekSeparatorLineColor(Color.GREEN); // green color for the week separator line
        // perform setOnDateChangeListener event on CalendarView
        hwCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                lstSchedule.clear();
                for(int i = 0; i < lstDates.size(); i++){
                    if(lstDates.get(i)==dayOfMonth){
                        lstSchedule.add(lstAssignments.get(i));
                    }
                }
                dayInCalendar=dayOfMonth;
                lstViewSchedule.setAdapter(lstViewAdapter);
                lstViewAdapter.notifyDataSetChanged();
            }
        });

        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddSchedule.setVisibility(View.INVISIBLE);
                editTextSchedule.setVisibility(View.VISIBLE);
                btnSaveSchedule.setVisibility(View.VISIBLE);
            }
        });

        btnSaveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtScheduleInp = String.valueOf(editTextSchedule.getText());
                lstSchedule.add(txtScheduleInp);
                lstViewSchedule.setAdapter(lstViewAdapter);
                lstViewAdapter.notifyDataSetChanged();
                editTextSchedule.setVisibility(View.INVISIBLE);
                editTextSchedule.setText("");
                btnSaveSchedule.setVisibility(View.INVISIBLE);
                btnAddSchedule.setVisibility(View.VISIBLE);
                lstAssignments.add(txtScheduleInp);
                lstDates.add(dayInCalendar);
                System.out.println(dayInCalendar);
                System.out.println(lstSchedule);
                System.out.println(lstAssignments);
                sendNotification("New Event", "You added '" + txtScheduleInp + "' to your schedule!");

            }
        });


        lstViewSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAssignment = (String) parent.getItemAtPosition(position);
                btnAddSchedule.setVisibility(View.INVISIBLE);
                btnRemoveEvent.setVisibility(View.VISIBLE);
                System.out.println("Whfikef");
                btnCancelRemove.setVisibility(View.VISIBLE);
                btnRemoveEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = lstSchedule.indexOf(lstSchedule.get(position));
                        lstDates.remove(lstAssignments.indexOf(lstSchedule.get(position)));
                        lstAssignments.remove(lstSchedule.get(position));

                        lstSchedule.remove(position);

                        System.out.println(lstDates);

                        System.out.println(lstSchedule);
                        System.out.println(lstAssignments);
                        btnAddSchedule.setVisibility(View.VISIBLE);
                        btnRemoveEvent.setVisibility(View.INVISIBLE);
                        btnCancelRemove.setVisibility(View.INVISIBLE);
                        lstViewAdapter.notifyDataSetChanged();
                    }
                });

                btnCancelRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnAddSchedule.setVisibility(View.VISIBLE);
                        btnRemoveEvent.setVisibility(View.INVISIBLE);
                        btnCancelRemove.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });

        lstViewAdapter.notifyDataSetChanged();
// Inflate the layout for this fragment
        return v;
    }

    private void sendNotification(String title, String content) {
        String NOTIFICATION_CHANNEL_ID = "edmt_multiple_location";
        notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[] {0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        Notification notification = builder.build();
        notificationManager.notify(new Random().nextInt(), notification);
    }

    public void buttonsText(){

    }

}