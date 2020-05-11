package com.demo.DailyShoppingList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;

public class LoginActivity extends AppCompatActivity {

    private Button next;
    private TextView signup;
    private EditText Email,password;
    //DB user
    public static final String DATABASE_NAME = "ShoppingDatabases.db";
    public static final String TABLE_NAME="Users";

    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //button
        next=findViewById(R.id.btn_login);
        //textview
        signup=findViewById(R.id.signup);
        Email=findViewById(R.id.email_login);
        password=findViewById(R.id.password_login);
        //User DataBase Creation
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createUserTable();

        //next as btn_login
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString().trim();
                String pass=password.getText().toString().trim();
                //Cursor cursorData = mDatabase.rawQuery("SELECT * FROM  Users", null);
                Cursor cursorData = mDatabase.rawQuery("SELECT * FROM " + "Users" + " WHERE " + "Gmail" + "=? AND " + "Password" + "=?", new String[]{email, pass});
                if (cursorData != null) {
                    if (cursorData.getCount() > 0) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        Toast.makeText(getApplicationContext(), "Login Sucess", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
                    }
                }
                cursorData.close();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    private void createUserTable(){
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Gmail TEXT,Password TEXT)");
    }

}
