package com.example.homeworkplanner;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

public class FirstFragment extends Fragment {

    String assignmentName;
    String dueDate;
    String priority;
    String className;
    ImageView imgPhoto;
    Button btnOpenCamera;
    Button btnPhotoGallery;
    Button btnAddClasses;
    Button btnSaveClass;
    ImageView imgPhoto2;
    ImageView imgPhoto3;
    Button btnComplete;
    TextView txtYourColorIs;
    TextView txtActiveAssignments, txtCompletedAssignments;
    NotificationManager notificationManager;
    Switch switchNotis;

    public static final String class_name = "classname_key";
    boolean photo1;
    boolean photo2;
    boolean photo3;
    boolean disp1;
    boolean disp2;
    boolean disp3;
    Integer intCompleted=0;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String BUTTON1 = "button1";

    private String text;


    // text view variable to set the color for GFG text
    private TextView gfgTextView;

    // two buttons to open color picker dialog and one to
    // set the color for GFG text
    private Button mSetColorButton, mPickColorButton;

    // view box to preview the selected color
    private View mColorPreview;

    // this is the default color of the preview box
    private int mDefaultColor;

    Button btn1, btn2, btn3, btn4, btn5;
    boolean isNotis;
    boolean isBtn1, isBtn2, isBtn3, isBtn4, isBtn5;
    ListView lstViewAllAssignments, lstViewCompletedAssignments;
    EditText editTxtClassName;

    String[] subjects;

    public FirstFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_first, container, false);


        // register the GFG text with appropriate ID

        // register two of the buttons with their
        // appropriate IDs
        mPickColorButton = v.findViewById(R.id.pick_color_button);
        mSetColorButton = v.findViewById(R.id.set_color_button);

        // and also register the view which shows the
        // preview of the color chosen by the user
        mColorPreview = v.findViewById(R.id.preview_selected_color);

        // set the default color to 0 as it is black
        mDefaultColor = 0;

        // button open the AmbilWanra color picker dialog.



        // the dialog functionality is handled separately
        // using openColorPickerDialog this is triggered as
        // soon as the user clicks on the Pick Color button And
        // the AmbilWarnaDialog has 2 methods to be overridden
        // those are onCancel and onOk which handle the "Cancel"
        // and "OK" button of color picker dialog


        btn1 = v.findViewById(R.id.btn1);
        btn2 = v.findViewById(R.id.btn2);
        btn3 = v.findViewById(R.id.btn3);
        btn4 = v.findViewById(R.id.btn4);
        btn5 = v.findViewById(R.id.btn5);
        Button btnBack = v.findViewById(R.id.btnBack);
        Button btnAddClasses = v.findViewById(R.id.btnAddClasses);
        Button btnSaveClass = v.findViewById(R.id.btnSaveClass);
        EditText txtAssignmentName = v.findViewById(R.id.txtAssignmentName);
        EditText editTxtClassName = v.findViewById(R.id.editTxtClassName);
        TextView txtSubject = v.findViewById(R.id.txtSubject);
        txtYourColorIs=v.findViewById(R.id.txtYourColorIs);
        switchNotis=v.findViewById(R.id.switchNotis);
//        TextView txtDisplayAssignment = v.findViewById(R.id.txtDisplayAssignment);
//        TextView txtDisplayAssignment2 = v.findViewById(R.id.txtDisplayAssignment2);
//        TextView txtDisplayAssignment3 = v.findViewById(R.id.txtDisplayAssignment3);
        EditText txtDueDate = v.findViewById(R.id.txtDueDate);
        CheckBox checkPriorityHigh = v.findViewById(R.id.checkPriorityHigh);
        CheckBox checkPriorityMedium = v.findViewById(R.id.checkPriorityMedium);
        CheckBox checkPriorityLow = v.findViewById(R.id.checkPriorityLow);
        ImageButton btnAdd = v.findViewById(R.id.btnAdd);
        imgPhoto = v.findViewById(R.id.imgPhoto);
        imgPhoto2 = v.findViewById(R.id.imgPhoto2);
        imgPhoto3 = v.findViewById(R.id.imgPhoto3);
        btnPhotoGallery = v.findViewById(R.id.btnPhotoGallery);
        btnComplete = v.findViewById(R.id.btnComplete);

        txtActiveAssignments=v.findViewById(R.id.txtActiveAssignments);
        txtCompletedAssignments=v.findViewById(R.id.txtCompletedAssignments);

        btnOpenCamera = v.findViewById(R.id.btnOpeanCamera);

        lstViewAllAssignments = (ListView) v.findViewById(R.id.lstViewAllAssignments);
        lstViewCompletedAssignments = (ListView) v.findViewById(R.id.lstViewCompletedAssignments);

        ArrayList<String> lstAssignments = new ArrayList<String>();
        ArrayList<String> lstCompleted = new ArrayList<>();

//        System.out.println(lstAssignments);


        ArrayAdapter<String> lstViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lstAssignments) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the list view each item as text view
                TextView item = (TextView) super.getView(position, convertView, parent);


                // Set the typeface/font for the current item

                // Set the list view item's text color
                item.setTextColor(Color.parseColor("#17354f"));

                // Set the item text style to bold
                item.setTypeface(item.getTypeface(), Typeface.ITALIC);

                // Change the item text size
                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);

                // return the view
                return item;
            }
        };

        ArrayAdapter<String> lstViewAdapterCompleted = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lstCompleted) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the list view each item as text view
                TextView item = (TextView) super.getView(position, convertView, parent);


                // Set the typeface/font for the current item

                // Set the list view item's text color
                item.setTextColor(Color.parseColor("#17354f"));

                // Set the item text style to bold
                item.setTypeface(item.getTypeface(), Typeface.ITALIC);

                // Change the item text size
                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);

                // return the view
                return item;
            }
        };

//        TextView textView = new TextView(getActivity());
//        textView.setText("Active Assignments");
//
//        lstViewAllAssignments.addHeaderView(textView);
//
//        TextView textView2 = new TextView(getActivity());
//        textView2.setText("Completed Assignments");
//
//        lstViewCompletedAssignments.addHeaderView(textView2);

        switchNotis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNotis=switchNotis.isChecked();
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
                btn5.setVisibility(View.INVISIBLE);
                txtAssignmentName.setVisibility(View.VISIBLE);
                txtDueDate.setVisibility(View.VISIBLE);
                checkPriorityHigh.setVisibility(View.VISIBLE);
                checkPriorityLow.setVisibility(View.VISIBLE);
                checkPriorityMedium.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                btnOpenCamera.setVisibility(View.VISIBLE);
                btnPhotoGallery.setVisibility(View.INVISIBLE);
                imgPhoto.setVisibility(View.INVISIBLE);
                imgPhoto2.setVisibility(View.INVISIBLE);
                imgPhoto3.setVisibility(View.INVISIBLE);
                btnAddClasses.setVisibility(View.INVISIBLE);
                lstViewAllAssignments.setVisibility(View.INVISIBLE);
                lstViewCompletedAssignments.setVisibility(View.INVISIBLE);
                txtActiveAssignments.setVisibility(View.INVISIBLE);
                txtCompletedAssignments.setVisibility(View.INVISIBLE);
                btnComplete.setVisibility(View.INVISIBLE);

            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
                btn5.setVisibility(View.INVISIBLE);
                txtAssignmentName.setVisibility(View.VISIBLE);
                txtDueDate.setVisibility(View.VISIBLE);
                checkPriorityHigh.setVisibility(View.VISIBLE);
                checkPriorityLow.setVisibility(View.VISIBLE);
                checkPriorityMedium.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                btnOpenCamera.setVisibility(View.VISIBLE);
                btnPhotoGallery.setVisibility(View.INVISIBLE);
                imgPhoto.setVisibility(View.INVISIBLE);
                imgPhoto2.setVisibility(View.INVISIBLE);
                imgPhoto3.setVisibility(View.INVISIBLE);
                btnAddClasses.setVisibility(View.INVISIBLE);
                lstViewAllAssignments.setVisibility(View.INVISIBLE);
                lstViewCompletedAssignments.setVisibility(View.INVISIBLE);
                txtActiveAssignments.setVisibility(View.INVISIBLE);
                txtCompletedAssignments.setVisibility(View.INVISIBLE);
                btnComplete.setVisibility(View.INVISIBLE);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
                btn5.setVisibility(View.INVISIBLE);
                txtAssignmentName.setVisibility(View.VISIBLE);
                txtDueDate.setVisibility(View.VISIBLE);
                checkPriorityHigh.setVisibility(View.VISIBLE);
                checkPriorityLow.setVisibility(View.VISIBLE);
                checkPriorityMedium.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                btnOpenCamera.setVisibility(View.VISIBLE);
                btnPhotoGallery.setVisibility(View.INVISIBLE);
                imgPhoto.setVisibility(View.INVISIBLE);
                imgPhoto2.setVisibility(View.INVISIBLE);
                imgPhoto3.setVisibility(View.INVISIBLE);
                btnAddClasses.setVisibility(View.INVISIBLE);
                lstViewAllAssignments.setVisibility(View.INVISIBLE);
                lstViewCompletedAssignments.setVisibility(View.INVISIBLE);
                txtActiveAssignments.setVisibility(View.INVISIBLE);
                txtCompletedAssignments.setVisibility(View.INVISIBLE);
                btnComplete.setVisibility(View.INVISIBLE);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
                btn5.setVisibility(View.INVISIBLE);
                txtAssignmentName.setVisibility(View.VISIBLE);
                txtDueDate.setVisibility(View.VISIBLE);
                checkPriorityHigh.setVisibility(View.VISIBLE);
                checkPriorityLow.setVisibility(View.VISIBLE);
                checkPriorityMedium.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                btnOpenCamera.setVisibility(View.VISIBLE);
                btnPhotoGallery.setVisibility(View.INVISIBLE);
                imgPhoto.setVisibility(View.INVISIBLE);
                imgPhoto2.setVisibility(View.INVISIBLE);
                imgPhoto3.setVisibility(View.INVISIBLE);
                btnAddClasses.setVisibility(View.INVISIBLE);
                lstViewAllAssignments.setVisibility(View.INVISIBLE);
                lstViewCompletedAssignments.setVisibility(View.INVISIBLE);
                txtActiveAssignments.setVisibility(View.INVISIBLE);
                txtCompletedAssignments.setVisibility(View.INVISIBLE);
                btnComplete.setVisibility(View.INVISIBLE);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
                btn5.setVisibility(View.INVISIBLE);
                txtAssignmentName.setVisibility(View.VISIBLE);
                txtDueDate.setVisibility(View.VISIBLE);
                checkPriorityHigh.setVisibility(View.VISIBLE);
                checkPriorityLow.setVisibility(View.VISIBLE);
                checkPriorityMedium.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                btnOpenCamera.setVisibility(View.VISIBLE);
                btnPhotoGallery.setVisibility(View.INVISIBLE);
                imgPhoto.setVisibility(View.INVISIBLE);
                imgPhoto2.setVisibility(View.INVISIBLE);
                imgPhoto3.setVisibility(View.INVISIBLE);
                btnAddClasses.setVisibility(View.INVISIBLE);
                lstViewAllAssignments.setVisibility(View.INVISIBLE);
                lstViewCompletedAssignments.setVisibility(View.INVISIBLE);
                txtActiveAssignments.setVisibility(View.INVISIBLE);
                txtCompletedAssignments.setVisibility(View.INVISIBLE);
                btnComplete.setVisibility(View.INVISIBLE);
            }
        });

        btnAddClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                btnEnglish.setVisibility(View.INVISIBLE);
//                btnScience.setVisibility(View.INVISIBLE);
//                btnHistory.setVisibility(View.INVISIBLE);
//                btnCS.setVisibility(View.INVISIBLE);
//                txtAssignmentName.setVisibility(View.VISIBLE);
//                txtDueDate.setVisibility(View.VISIBLE);
//                checkPriorityHigh.setVisibility(View.VISIBLE);
//                checkPriorityLow.setVisibility(View.VISIBLE);
//                checkPriorityMedium.setVisibility(View.VISIBLE);
//                btnAdd.setVisibility(View.VISIBLE);
//                btnBack.setVisibility(View.VISIBLE);
//                btnMath.setVisibility(View.INVISIBLE);
//                btnOpenCamera.setVisibility(View.VISIBLE);
//                btnPhotoGallery.setVisibility(View.INVISIBLE);
//                imgPhoto.setVisibility(View.INVISIBLE);
//                imgPhoto2.setVisibility(View.INVISIBLE);
//                imgPhoto3.setVisibility(View.INVISIBLE);
                editTxtClassName.setVisibility(View.VISIBLE);
                btnSaveClass.setVisibility(View.VISIBLE);
                btnAddClasses.setVisibility(View.INVISIBLE);
                btnPhotoGallery.setVisibility(View.INVISIBLE);
                lstViewAllAssignments.setVisibility(View.INVISIBLE);
                lstViewCompletedAssignments.setVisibility(View.INVISIBLE);
                txtActiveAssignments.setVisibility(View.INVISIBLE);
                txtCompletedAssignments.setVisibility(View.INVISIBLE);

                mPickColorButton.setVisibility(View.VISIBLE);




            }
        });

        mPickColorButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // to make code look cleaner the color
                        // picker dialog functionality are
                        // handled in openColorPickerDialogue()
                        // function
                        mSetColorButton.setVisibility(View.VISIBLE);
                        txtYourColorIs.setVisibility(View.VISIBLE);
                        mColorPreview.setVisibility(View.VISIBLE);
                        openColorPickerDialogue();
                    }
                });

        // button to set the color GFG text
        mSetColorButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // as the mDefaultColor is the global
                        // variable its value will be changed as
                        // soon as ok button is clicked from the
                        // color picker dialog.
                        if (isBtn4) {
                            btn5.setBackgroundColor(mDefaultColor);
                        } else if (isBtn3) {
                            btn4.setBackgroundColor(mDefaultColor);
                        } else if (isBtn2) {
                            btn3.setBackgroundColor(mDefaultColor);
                        } else if (isBtn1) {
                            btn2.setBackgroundColor(mDefaultColor);
                        } else {
                            btn1.setBackgroundColor(mDefaultColor);
                        }
                    }
                });

        btnSaveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                btnEnglish.setVisibility(View.INVISIBLE);
//                btnScience.setVisibility(View.INVISIBLE);
//                btnHistory.setVisibility(View.INVISIBLE);
//                btnCS.setVisibility(View.INVISIBLE);
//                txtAssignmentName.setVisibility(View.VISIBLE);
//                txtDueDate.setVisibility(View.VISIBLE);
//                checkPriorityHigh.setVisibility(View.VISIBLE);
//                checkPriorityLow.setVisibility(View.VISIBLE);
//                checkPriorityMedium.setVisibility(View.VISIBLE);
//                btnAdd.setVisibility(View.VISIBLE);
//                btnBack.setVisibility(View.VISIBLE);
//                btnMath.setVisibility(View.INVISIBLE);
//                btnOpenCamera.setVisibility(View.VISIBLE);
//                btnPhotoGallery.setVisibility(View.INVISIBLE);
//                imgPhoto.setVisibility(View.INVISIBLE);
//                imgPhoto2.setVisibility(View.INVISIBLE);
//                imgPhoto3.setVisibility(View.INVISIBLE);
                className = String.valueOf(editTxtClassName.getText());
                editTxtClassName.setVisibility(View.INVISIBLE);
                btnSaveClass.setVisibility(View.INVISIBLE);
                btnAddClasses.setVisibility(View.VISIBLE);
                lstViewAllAssignments.setVisibility(View.VISIBLE);
                lstViewCompletedAssignments.setVisibility(View.VISIBLE);
                txtActiveAssignments.setVisibility(View.VISIBLE);
                txtCompletedAssignments.setVisibility(View.VISIBLE);

                mSetColorButton.setVisibility(View.INVISIBLE);
                txtYourColorIs.setVisibility(View.INVISIBLE);
                mColorPreview.setVisibility(View.INVISIBLE);
                mPickColorButton.setVisibility(View.INVISIBLE);

                if (isBtn4) {
                    btn5.setVisibility(View.VISIBLE);
                    btn5.setText(className);
                    isBtn5 = true;
                    editTxtClassName.setText("");
                } else if (isBtn3) {
                    btn4.setVisibility(View.VISIBLE);
                    btn4.setText(className);
                    isBtn4 = true;
                    editTxtClassName.setText("");
                } else if (isBtn2) {
                    btn3.setVisibility(View.VISIBLE);
                    btn3.setText(className);
                    isBtn3 = true;
                    editTxtClassName.setText("");
                } else if (isBtn1) {
                    btn2.setVisibility(View.VISIBLE);
                    btn2.setText(className);
                    isBtn2 = true;
                    editTxtClassName.setText("");
                } else {
                    btn1.setVisibility(View.VISIBLE);
                    btn1.setText(className);
                    isBtn1 = true;
                    editTxtClassName.setText("");


                }

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {


                assignmentName = txtAssignmentName.getText().toString();
                dueDate = txtDueDate.getText().toString();
                System.out.println(dueDate);
                System.out.println(assignmentName);
                if (checkPriorityHigh.isChecked()) {
                    priority = "High";
                }
                if (checkPriorityLow.isChecked()) {
                    priority = "Low";
                }
                if (checkPriorityMedium.isChecked()) {
                    priority = "Medium";
                }

                lstAssignments.add("Assignment: " + assignmentName + " || Due: " + dueDate + " || Priority: " + priority);

//                if(disp2) {
//                    txtDisplayAssignment3.setText(String.valueOf("Assignment: "+ assignmentName + " || Due: " + dueDate+ " || Priority: " + priority));
//                    disp3=true;
//                }
//                else if(disp1){
//                    txtDisplayAssignment2.setText(String.valueOf("Assignment: "+ assignmentName + " || Due: " + dueDate+ " || Priority: " + priority));
//                    disp2=true;
//                }
//                else{
//                    txtDisplayAssignment.setText(String.valueOf("Assignment: "+ assignmentName + " || Due: " + dueDate+ " || Priority: " + priority));
//                    disp1=true;
//                }
                txtDueDate.setText("");
                txtAssignmentName.setText("");
                checkPriorityHigh.setChecked(false);
                checkPriorityLow.setChecked(false);
                checkPriorityMedium.setChecked(false);

                lstViewAllAssignments.setAdapter(lstViewAdapter);
                lstViewCompletedAssignments.setAdapter(lstViewAdapterCompleted);
                lstViewAllAssignments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedAssignment = (String) parent.getItemAtPosition(position);
                        btnComplete.setVisibility(View.VISIBLE);
                        btnComplete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                intCompleted++;
                                if(isNotis){
                                    sendNotification("Congrats!", "You have completed " + intCompleted+" assignments!");
                                }
                                lstAssignments.remove(position);
                                lstCompleted.add(selectedAssignment);

                                lstViewAdapter.notifyDataSetChanged();
                                btnComplete.setVisibility(View.INVISIBLE);
                                lstViewAdapterCompleted.notifyDataSetChanged();

                            }
                        });
                    }
                });

                lstViewAdapter.notifyDataSetChanged();
                lstViewAdapterCompleted.notifyDataSetChanged();

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBtn1) {
                    btn1.setVisibility(View.VISIBLE);
                }
                if (isBtn2) {
                    btn2.setVisibility(View.VISIBLE);
                }
                if (isBtn3) {
                    btn3.setVisibility(View.VISIBLE);
                }
                if (isBtn4) {
                    btn4.setVisibility(View.VISIBLE);
                }
                if (isBtn5) {
                    btn5.setVisibility(View.VISIBLE);
                }
                txtAssignmentName.setVisibility(View.INVISIBLE);
                txtDueDate.setVisibility(View.INVISIBLE);
                checkPriorityHigh.setVisibility(View.INVISIBLE);
                checkPriorityLow.setVisibility(View.INVISIBLE);
                checkPriorityMedium.setVisibility(View.INVISIBLE);
                btnAdd.setVisibility(View.INVISIBLE);
                btnBack.setVisibility(View.INVISIBLE);
                btnOpenCamera.setVisibility(View.INVISIBLE);
                imgPhoto.setVisibility(View.INVISIBLE);
                imgPhoto2.setVisibility(View.INVISIBLE);
                imgPhoto3.setVisibility(View.INVISIBLE);
                btnPhotoGallery.setVisibility(View.VISIBLE);
                btnAddClasses.setVisibility(View.VISIBLE);
                lstViewAllAssignments.setVisibility(View.VISIBLE);
                lstViewCompletedAssignments.setVisibility(View.VISIBLE);
                txtActiveAssignments.setVisibility(View.VISIBLE);
                txtCompletedAssignments.setVisibility(View.VISIBLE);

            }
        });

        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPhoto.setVisibility(View.INVISIBLE);
                imgPhoto2.setVisibility(View.INVISIBLE);
                imgPhoto3.setVisibility(View.INVISIBLE);
                attachPhoto();
                imgPhoto.setVisibility(View.INVISIBLE);
                imgPhoto2.setVisibility(View.INVISIBLE);
                imgPhoto3.setVisibility(View.INVISIBLE);
            }
        });

        btnPhotoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
                btn5.setVisibility(View.INVISIBLE);
                txtAssignmentName.setVisibility(View.INVISIBLE);
                txtDueDate.setVisibility(View.INVISIBLE);
                checkPriorityHigh.setVisibility(View.INVISIBLE);
                checkPriorityLow.setVisibility(View.INVISIBLE);
                checkPriorityMedium.setVisibility(View.INVISIBLE);
                btnAdd.setVisibility(View.INVISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                btnOpenCamera.setVisibility(View.INVISIBLE);
                imgPhoto.setVisibility(View.VISIBLE);
                imgPhoto2.setVisibility(View.VISIBLE);
                imgPhoto3.setVisibility(View.VISIBLE);
                btnPhotoGallery.setVisibility(View.INVISIBLE);
                btnAddClasses.setVisibility(View.INVISIBLE);
                lstViewAllAssignments.setVisibility(View.INVISIBLE);
                lstViewCompletedAssignments.setVisibility(View.INVISIBLE);
                txtActiveAssignments.setVisibility(View.INVISIBLE);
                txtCompletedAssignments.setVisibility(View.INVISIBLE);

            }
        });


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


// Inflate the layout for this fragment
        return v;


    }


    public void openColorPickerDialogue() {

        // the AmbilWarnaDialog callback needs 3 parameters
        // one is the context, second is default color,
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(getActivity(), mDefaultColor,
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




    public void attachPhoto(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        imgPhoto.setVisibility(View.INVISIBLE);
        imgPhoto2.setVisibility(View.INVISIBLE);
        imgPhoto3.setVisibility(View.INVISIBLE);
        if(resultCode== Activity.RESULT_OK){
            Bitmap b = (Bitmap) data.getExtras().get("data");
            if(photo2){
                imgPhoto3.setImageBitmap(b);
                imgPhoto3.setVisibility(View.INVISIBLE);
                photo3=true;

            }
            else if(photo1){
                imgPhoto2.setImageBitmap(b);
                imgPhoto2.setVisibility(View.INVISIBLE);
                photo2=true;

            }
            else{
                imgPhoto.setImageBitmap(b);
                imgPhoto.setVisibility(View.INVISIBLE);
                photo1=true;
            }

        }
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



//    Button s = findViewById(R.id.s);
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

}
