package com.demo.DailyShoppingList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText email,password;
    private Button signup;
    private TextView back;
    SQLiteDatabase mDatabase;

    public static final String DATABASE_NAME = "ShoppingDatabases.db";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.email_signup);
        password=findViewById(R.id.password_singup);
        signup=findViewById(R.id.btn_signup);
        back=findViewById(R.id.login);
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (gmail.isEmpty() || pass.isEmpty() ) {

                    Toast.makeText(RegisterActivity.this, "Fill the form", Toast.LENGTH_SHORT).show();

                } else {

                    String insertSQL = "INSERT INTO Users \n" +
                            "(Gmail, Password)\n" +
                            "VALUES \n" +
                            "(?, ?);";

                    //using the same method execsql for inserting values
                    //this time it has two parameters
                    //first is the sql string and second is the parameters that is to be binded with the query
                    mDatabase.execSQL(insertSQL, new String[]{gmail, pass});
                    //Clear the data After Entry
                    email.getText().clear();
                    password.getText().clear();
                    Toast.makeText(RegisterActivity.this, "Great! User Created", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



    }
}
