package com.demo.DailyShoppingList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.List;


public class DataAdapter extends ArrayAdapter<Data> {

    Context mCtx;
    int listLayoutRes;
    List<Data> dataList;
    SQLiteDatabase mDatabase;


    public DataAdapter(Context mCtx, int listLayoutRes, List<Data> dataList, SQLiteDatabase mDatabase) {
        super(mCtx, listLayoutRes, dataList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.dataList = dataList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        final Data data = dataList.get(position);


        TextView textViewType = view.findViewById(R.id.textViewType);
        TextView textViewAmount = view.findViewById(R.id.textViewAmount);
        TextView textViewNote = view.findViewById(R.id.textViewNote);
        TextView textViewQty = view.findViewById(R.id.quantity);
        TextView textViewDate = view.findViewById(R.id.textViewDate);



        textViewType.setText(data.getType());
        textViewAmount.setText(data.getAmount());
        textViewNote.setText(data.getNote());
        textViewQty.setText(data.getQuantity());
        textViewDate.setText(data.getDate());


        ImageView buttonDelete = view.findViewById(R.id.buttonDeleteEmployee);
        ImageView buttonEdit = view.findViewById(R.id.buttonEditEmployee);

        //adding a clicklistener to button
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(data);
            }
        });

        //the delete operation
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM List WHERE id = ?";
                        mDatabase.execSQL(sql, new Integer[]{data.getId()});
                        mCtx.startActivity(new Intent(mCtx.getApplicationContext(), MainActivity.class));
                        Toast.makeText(mCtx.getApplicationContext(), "Data Deleted,Click to See the updated List", Toast.LENGTH_SHORT).show();

                        //reloadEmployeesFromDatabase();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    private void updateData(final Data data) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_data, null);
        builder.setView(view);


        final EditText editTextType = view.findViewById(R.id.editTextType);
        final EditText editamount = view.findViewById(R.id.editAmount);
        final EditText editqnt = view.findViewById(R.id.editQty);
        final EditText editnote = view.findViewById(R.id.editNote);
        final EditText editdate = view.findViewById(R.id.editDate);

        editTextType.setText(data.getType());
        //int amount=Integer.parseInt();
        editamount.setText(data.getAmount());
        editqnt.setText(data.getQuantity());
        editnote.setText(data.getNote());
        editdate.setText(data.getDate());


        final AlertDialog dialog = builder.create();
        dialog.show();

        // CREATE METHOD FOR EDIT THE FORM
        view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = editTextType.getText().toString().trim();
                String amount = editamount.getText().toString().trim();
                String qty = editqnt.getText().toString().trim();
                String note = editnote.getText().toString().trim();
                String date = editdate.getText().toString().trim();

                if (type.isEmpty()) {
                    editTextType.setError("Type can't be blank");
                    editTextType.requestFocus();
                    return;
                }

                if (amount.isEmpty()) {
                    editamount.setError("Amount can't be blank");
                    editamount.requestFocus();
                    return;
                }

                if (qty.isEmpty()) {
                    editqnt.setError("Quantity can't be blank");
                    editqnt.requestFocus();
                    return;
                }

                if (note.isEmpty()) {
                    editnote.setError("Note can't be blank");
                    editnote.requestFocus();
                    return;
                }
                if (date.isEmpty()) {
                    editdate.setError("Date can't be blank");
                    editdate.requestFocus();
                    return;
                }

                String sql = "UPDATE List \n" +
                        "SET Type = ?, \n" +
                        "Amount = ?,\n"+
                        "Quantity = ?,\n"+
                        "Note = ?,\n"+
                        "Date= ? \n" +
                        "WHERE id = ?;\n";

                mDatabase.execSQL(sql, new String[]{type,amount,qty,note,date, String.valueOf(data.getId())});
                mCtx.startActivity(new Intent(mCtx.getApplicationContext(), MainActivity.class));
                Toast.makeText(mCtx, "List Updated,Click to see", Toast.LENGTH_SHORT).show();
                //reloadEmployeesFromDatabase();
                dialog.dismiss();
            }
        });
    }

    private void reloadDataFromDatabase() {
        Cursor cursorData = mDatabase.rawQuery("SELECT * FROM List", null);
        if (cursorData.moveToFirst()) {
            dataList.clear();
            do {
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
        cursorData.close();
        notifyDataSetChanged();
    }

}
