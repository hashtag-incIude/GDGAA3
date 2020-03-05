package com.gdg.gdgaa3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    EditText ename, eroll_no, emarks,email,dob,phone;
    Button add, view, viewall, Show1, delete, modify;
    SQLiteDatabase db;


    Spinner branchspn;
    public static final String finalGender = "gender";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ename = (EditText) findViewById(R.id.name);
        eroll_no = (EditText) findViewById(R.id.roll_no);
        emarks = (EditText) findViewById(R.id.marks);
        email=(EditText)findViewById(R.id.studEmail);
        dob=(EditText) findViewById(R.id.studDOB);
        phone=(EditText)findViewById(R.id.phone);
        add = (Button) findViewById(R.id.addbtn);
        view = (Button) findViewById(R.id.viewbtn);
        viewall = (Button) findViewById(R.id.viewallbtn);
        delete = (Button) findViewById(R.id.deletebtn);
        Show1 = (Button) findViewById(R.id.showbtn);
        modify = (Button) findViewById(R.id.modifybtn);

        branchspn = (Spinner)findViewById(R.id.branchSpn);



        db = openOrCreateDatabase("Student_manage", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno INTEGER,name VARCHAR,marks INTEGER,studEmail VARCHAR,branchspn VARCHAR,dob VARCHAR,phone VARCHAR);");



        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (eroll_no.getText().toString().trim().length() == 0 ||
                        ename.getText().toString().trim().length() == 0 ||
                        emarks.getText().toString().trim().length() == 0||
                        email.getText().toString().trim().length() == 0||
                        dob.getText().toString().trim().length()==0||
                        phone.getText().toString().trim().length()==0||
                        branchspn.getSelectedItem().toString().length()==0) {
                    showMessage("Error", "Please enter all values");
                    return;
                }
                db.execSQL("INSERT INTO student VALUES('" + eroll_no.getText() + "','" + ename.getText() + "','" + emarks.getText() +"','"+ email.getText() + "','"+ branchspn.getSelectedItem()+ "','"+dob.getText()+"','"+phone.getText()+"');");
                showMessage("Success", "Record added successfully");
                clearText();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (eroll_no.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + eroll_no.getText() + "'", null);
                if (c.moveToFirst()) {
                    db.execSQL("DELETE FROM student WHERE rollno='" + eroll_no.getText() + "'");
                    showMessage("Success", "Record Deleted");
                } else {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (eroll_no.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + eroll_no.getText() + "'", null);
                if (c.moveToFirst()) {
                    db.execSQL("UPDATE student SET name='" + ename.getText() + "',marks='" + emarks.getText() +"',email'" +email.getText()+"',branch" + branchspn.getSelectedItem()+
                            "' WHERE rollno='" + eroll_no.getText() + "',dob'"+dob.getText()+"',phone'"+phone.getText()+"'");
                    showMessage("Success", "Record Modified");
                } else {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (eroll_no.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + eroll_no.getText() + "'", null);
                if (c.moveToFirst()) {
                    ename.setText(c.getString(1));
                    dob.setText(c.getString(5));
                    emarks.setText(c.getString(2));
                    email.setText(c.getString(3));
                    phone.setText(c.getString(6));
                    branchspn.setSelection(4);

                } else {
                    showMessage("Error", "Invalid Rollno");
                    clearText();
                }
            }
        });
        viewall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Cursor c = db.rawQuery("SELECT * FROM student", null);
                if (c.getCount() == 0) {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (c.moveToNext()) {
                    buffer.append("Rollno: " + c.getString(0) + "\n");
                    buffer.append("Name: " + c.getString(1) + "\n");
                    buffer.append("DATE OF BIRTH: " + c.getString(5) + "\n");
                    buffer.append("Marks: " + c.getString(2) + "\n");
                    buffer.append("Email: " + c.getString(3) + "\n");
                    buffer.append("PHONE NO: " + c.getString(6) + "\n");
                    buffer.append("BRANCH: " + c.getString(4) + "\n\n");

                }
                showMessage("Student Details", buffer.toString());
            }
        });
        Show1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showMessage("GDG ADMISSION ", "Developed By ANUKUL KUMAR");
            }
        });

    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
        eroll_no.setText("");
        ename.setText("");
        dob.setText("");
        emarks.setText("");
        email.setText("");
        phone.setText("");
        branchspn.setSelected(Boolean.parseBoolean(""));
        eroll_no.requestFocus();
    }
}
