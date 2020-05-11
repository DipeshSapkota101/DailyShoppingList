package com.demo.DailyShoppingList;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {

    List<Data> dataList;
    SQLiteDatabase mDatabase;
    ListView listViewData;
    DataAdapter adapter;
    public TextView Total;
    Button deleteALl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        listViewData = (ListView) findViewById(R.id.listViewEmployees);
        deleteALl=findViewById(R.id.deleteAll);
        Total=findViewById(R.id.total_Amount);
        dataList = new ArrayList<>();

        //opening the database
        mDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);

        //this method will display the employees in the list
        showDataFromDatabase();
        totalCalculate();
        final String insertSQL="DELETE  FROM List";
        deleteALl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataList.isEmpty()){
                    Toast.makeText(getApplicationContext(), "No Data Left", Toast.LENGTH_SHORT).show();
                }else{
                    mDatabase.execSQL(insertSQL);
                    Intent intent= new Intent(DataActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "List Updated,Click to see", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void showDataFromDatabase() {
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorData  = mDatabase.rawQuery("SELECT * FROM List", null);
        //if the cursor has some data
        if (cursorData.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                dataList.add(new Data(
                        cursorData.getInt(0),
                        cursorData.getString(1),
                        cursorData.getString(2),
                        cursorData.getString(3),
                        cursorData.getString(4),
                        cursorData.getString(5)
                ));

            } while (cursorData.moveToNext());
        }
        //closing the cursor
        cursorData.close();

        //creating the adapter object
        adapter = new DataAdapter(this, R.layout.list_layout_data, dataList, mDatabase);

        //adding the adapter to listview
        listViewData.setAdapter(adapter);
    }

    private void totalCalculate(){
        float qty,tamt;
        float iamount;
        float total=0;
        for (int i = 0; i < dataList.size(); i++) {
            //System.out.println(dataList.get(i));
            iamount=Float.parseFloat(dataList.get(i).getAmount());
            qty=Float.parseFloat(dataList.get(i).getQuantity());
            tamt=iamount*qty;
            total=total+tamt;
        }
        Total.setText(String.valueOf(total));
    }



}
