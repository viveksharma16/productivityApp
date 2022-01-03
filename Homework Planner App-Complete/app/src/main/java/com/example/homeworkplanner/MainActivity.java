package com.example.homeworkplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import yuku.ambilwarna.AmbilWarnaDialog;


public class MainActivity extends AppCompatActivity {

    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    private Button mSetColorButton, mPickColorButton;
    TextView txtYourColorIs;
    TextView txtWelcome;
    // view box to preview the selected color
    private View mColorPreview;

    // this is the default color of the preview box
    private int mDefaultColor;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// get the reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        mSetColorButton = (Button)findViewById(R.id.set_color_button);
        mPickColorButton=(Button)findViewById(R.id.pick_color_button);
        mColorPreview = (View)findViewById(R.id.preview_selected_color);
        constraintLayout = (ConstraintLayout)findViewById(R.id.constraintLayout);
        txtYourColorIs=(TextView)findViewById(R.id.txtYourColorIs);
        txtWelcome=(TextView)findViewById(R.id.txtWelcome);
// Create a new Tab named "First"
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Home"); // set the Text for the first Tab
        firstTab.setIcon(R.drawable.home); // set an icon for the
// first tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
// Create a new Tab named "Second"
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Calendar"); // set the Text for the second Tab
        secondTab.setIcon(R.drawable.calendar); // set an icon for the second tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
// Create a new Tab named "Third"
        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Productivity"); // set the Text for the first Tab
        thirdTab.setIcon(R.drawable.productivity); // set an icon for the first tab
        tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout


// perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new FirstFragment();
                        firstTab.setIcon(R.drawable.home);
                        break;
                    case 1:
                        fragment = new SecondFragment();
                        secondTab.setIcon(R.drawable.calendar);
                        break;
                    case 2:
                        fragment = new ThirdFragment();
                        thirdTab.setIcon(R.drawable.productivity);
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
                firstTab.setIcon(R.drawable.home);
                secondTab.setIcon(R.drawable.calendar);
                thirdTab.setIcon(R.drawable.productivity);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                firstTab.setIcon(R.drawable.home);
                secondTab.setIcon(R.drawable.calendar);
                thirdTab.setIcon(R.drawable.productivity);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                firstTab.setIcon(R.drawable.home);
                secondTab.setIcon(R.drawable.calendar);
                thirdTab.setIcon(R.drawable.productivity);
            }
        });

        mPickColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPickerDialogue();
            }
        });

        mSetColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintLayout.setBackgroundColor(mDefaultColor);
                mPickColorButton.setVisibility(View.INVISIBLE);
                mSetColorButton.setVisibility(View.INVISIBLE);
                mColorPreview.setVisibility(View.INVISIBLE);
                txtYourColorIs.setVisibility(View.INVISIBLE);
                txtWelcome.setVisibility(View.INVISIBLE);
            }
        });


    }

    public void openColorPickerDialogue() {

        // the AmbilWarnaDialog callback needs 3 parameters
        // one is the context, second is default color,
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, mDefaultColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // leave this function body as
                        // blank, as the dialog
                        // automatically closes when
                        // clicked on cancel button
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // change the mDefaultColor to
                        // change the GFG text color as
                        // it is returned when the OK
                        // button is clicked from the
                        // color picker dialog
                        mDefaultColor = color;

                        // now change the picked color
                        // preview box to mDefaultColor
                        mColorPreview.setBackgroundColor(mDefaultColor);
                    }
                });
        colorPickerDialogue.show();
    }


}


//    FrameLayout simpleFrameLayout;
//    TabLayout tabLayout;
//
//    Button s, s2, s3, s4, s5, btnSaveSubjects;
//
//    TabItem tabHome, tabHomework, tabCalendar;
//
//    TextView txtSubjectName, txtSubject;
//
//    ImageButton imgButtonAddHW;
//
//    ListView lstSubjects;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Button s = findViewById(R.id.s);
//        Button s2 = findViewById(R.id.s2);
//        Button s3 = findViewById(R.id.s3);
//        Button s4 = findViewById(R.id.s4);
//        Button s5 = findViewById(R.id.s5);
//        Button btnSaveSubjects = findViewById(R.id.btnSaveSubjects);
//
//        EditText txtS = findViewById(R.id.txtS);
//        EditText txtS2 = findViewById(R.id.txtS2);
//        EditText txtS3 = findViewById(R.id.txtS3);
//        EditText txtS4 = findViewById(R.id.txtS4);
//        EditText txtS5 = findViewById(R.id.txtS5);
//
//        txtSubjectName = findViewById(R.id.txtSubjectName);
//        txtSubject = findViewById(R.id.txtSubject);
//
//        tabHomework = findViewById(R.id.tabHomework);
//        tabHome = findViewById(R.id.tabHome);
//        tabCalendar = findViewById(R.id.tabCalendar);
//
//        TextView txtSubject = findViewById(R.id.txtSubject);
//
////        btnSaveSubjects.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                String sText = String.valueOf(txtS.getText());
////                s.setText(sText);
////
////                String sText2 = String.valueOf(txtS2.getText());
////                s2.setText(sText2);
////
////                String sText3 = String.valueOf(txtS3.getText());
////                s3.setText(sText3);
////
////                String sText4 = String.valueOf(txtS4.getText());
////                s4.setText(sText4);
////
////                String sText5 = String.valueOf(txtS5.getText());
////                s5.setText(sText5);
////            }
////        });
//
//        s.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                txtSubject.setText(s.getText());
//            }
//        });
//
//
//        s2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                txtSubject.setText(s2.getText());
//            }
//        });
//        s3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                txtSubject.setText(s3.getText());
//            }
//        });
//        s4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                txtSubject.setText(s4.getText());
//            }
//        });
//        s5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                txtSubject.setText(s5.getText());
//            }
//        });

//
//    }
//}