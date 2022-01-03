package com.example.homeworkplanner;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ThirdFragment extends Fragment {

    public int counter;
    Button btnStartTime;
    TextView textView;
    EditText editTxtTime;
    Integer timeInp;
    Button btnPause;
    ImageButton imgButtonStudyMusic;

    CountDownTimer timer;
    ProgressBar progressBar;
    private SQLiteDatabase sqLiteDatabase;

    NotificationManager notificationManager;

    VideoView mVideoView;
    MediaController mediaController;

    public ThirdFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_third, container, false);


        btnStartTime= v.findViewById(R.id.btnStartTime);
        btnPause=v.findViewById(R.id.btnPause);
        textView= v.findViewById(R.id.textView);
        editTxtTime = v.findViewById(R.id.editTxtTime);

        mediaController = new MediaController(getActivity());
        mVideoView = v.findViewById(R.id.simpleVideoView);
        mediaController.setAnchorView(mVideoView);
        Uri localUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.studymusic);
        mVideoView.setVideoURI(localUri);
        mVideoView.setMediaController(mediaController);
        mVideoView.start();



        btnStartTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                editTxtTime.setVisibility(View.INVISIBLE);
                btnStartTime.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.VISIBLE);
                timeInp=Integer.parseInt(String.valueOf(editTxtTime.getText()));
                textView.setText("");


                timer = new CountDownTimer (timeInp*60000, 1000) {


                    public void onTick(long millisUntilFinished){
                        String text = String.format(Locale.getDefault(), "%02d min: %02d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                        textView.setText(text);
                        counter++;

                    }
                    public  void onFinish(){
                        textView.setText("");
                        timer.cancel();
                        editTxtTime.setText("");
                        editTxtTime.setVisibility(View.VISIBLE);
                        btnStartTime.setVisibility(View.VISIBLE);
                        btnPause.setVisibility(View.INVISIBLE);
                        sendNotification("Timer Up!", "You worked for " + timeInp + " minutes!");
                    }
                }.start();

                btnPause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timer.cancel();
                        textView.setText("");
                        editTxtTime.setVisibility(View.VISIBLE);
                        btnStartTime.setVisibility(View.VISIBLE);
                        btnPause.setVisibility(View.INVISIBLE);

                    }
                });


            }
        });

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



}