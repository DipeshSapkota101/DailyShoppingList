package com.demo.DailyShoppingList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText e_type, e_amount, e_note,quantity;
    Button bt_save,viewdatall;
    public static final String DATABASE_NAME = "ShoppingDB.db";
//    public static final String DATABASE_NAME = "ShoppingDatabases.db";

    SQLiteDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createDataTable();


        //FindById (Button and Edittxt)
        e_type = (EditText) findViewById(R.id.e_type);
        e_amount = (EditText) findViewById(R.id.e_amount);
        e_note = (EditText) findViewById(R.id.e_note);
        quantity = (EditText) findViewById(R.id.e_quantity);

        bt_save = (Button) findViewById(R.id.btn_save);
        viewdatall=(Button)findViewById(R.id.viewdataLL);
        viewdatall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });




        //Onclick Btn
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get the Enter data
                String type = e_type.getText().toString().trim();
                String amount = e_amount.getText().toString().trim();
                String note = e_note.getText().toString();
                String quant = quantity.getText().toString();
                String date = DateFormat.getDateInstance().format(new Date());


                if (type.isEmpty() || amount.isEmpty() || note.isEmpty() || quant.isEmpty()  ) {
                    Toast.makeText(MainActivity.this, "Fil the form", Toast.LENGTH_SHORT).show();

                } else {

                    String insertSQL = "INSERT INTO List \n" +
                            "( Type, Amount , Quantity , Note , Date )\n" +
                            "VALUES \n" +
                            "(?,?,?,?,?);";

                    //using the same method execsql for inserting values
                    //this time it has two parameters
                    //first is the sql string and second is the parameters that is to be binded with the query
                    mDatabase.execSQL(insertSQL, new String[]{type, amount,quant, note, date});
                    Toast.makeText(MainActivity.this, "Great! Data Saved", Toast.LENGTH_SHORT).show();
                    //Clear the data After Entry
                    e_type.getText().clear();
                    e_amount.getText().clear();
                    e_note.getText().clear();
                    quantity.getText().clear();
                }


            }
        });


    }

    private void createDataTable() {
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS List " +
                "(\n" +
                "    id INTEGER NOT NULL CONSTRAINT list_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    Type varchar(200) NOT NULL,\n" +
                "    Amount varchar(200) NOT NULL,\n" +
                "    Quantity varchar(200) NOT NULL,\n" +
                "    Note varchar(200) NOT NULL,\n" +
                "    Date Varchar(200) NOT NULL\n" +
                ");"

        );
    }
}
