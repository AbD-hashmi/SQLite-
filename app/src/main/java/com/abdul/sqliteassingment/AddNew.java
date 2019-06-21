/**
 * Created by Abdul on 18/06/19.
 * Add New record class used for insertion, deletion,updation and viewing data
 */
package com.abdul.sqliteassingment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNew extends AppCompatActivity {

    EditText edname,edemail,edmobile,edposition;
    private DBHelper mydb ;
    int id_To_Update=0 ;
    String name,email,position,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        setTitle(getString(R.string.Addnewtitle));


        edname=(EditText)findViewById(R.id.name);
        edposition=(EditText)findViewById(R.id.position);
        edemail=(EditText)findViewById(R.id.email);
        edmobile=(EditText)findViewById(R.id.mobile);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                //fetching previously uploaded data from the sql table
                id_To_Update=Value;

                Cursor rs = mydb.getData(Value);
                rs.moveToFirst();

                for(rs.moveToFirst();!rs.isAfterLast();rs.moveToNext())
                {
                    edname.setText(rs.getString(rs.getColumnIndex(DBHelper.EMPLOYEE_COLUMN_NAME)));
                    edposition.setText(rs.getString(rs.getColumnIndex(DBHelper.EMPLOYEE_COLUMN_POSITION)));
                    edemail.setText(rs.getString(rs.getColumnIndex(DBHelper.EMPLOYEE_COLUMN_EMAIL)));
                    edmobile.setText(rs.getString(rs.getColumnIndex(DBHelper.EMPLOYEE_COLUMN_PHONE)));
                }

                if (!rs.isClosed())
                    rs.close();
              }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            //if previously uploaded data is fetched then delete option will be visible
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.delete_details_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Delete_Contact:

                //showing alert dialog before deleting the record
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                mydb.deleteEmployee(id_To_Update);
                                Toast.makeText(AddNew.this, "Data Deleted Successfully",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(AddNew.this,MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    //method to update and upload data in the sql table
    public void run(View view) {
        Bundle extras = getIntent().getExtras();

        //if record exists updates it
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                if(!editTextempty(edname)||!editTextempty(edposition)||!editTextempty(edemail)||!editTextempty(edmobile)){
                    return;
                }else {
                    name=getText(edname);
                    position=getText(edposition);

                    if (getText(edmobile).length()!=10){
                        edmobile.setError("Invalid Mobile number");
                        return;
                    }else mobile=getText(edmobile);


                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(getText(edemail)).matches()){
                        edemail.setError("Invalid email");
                        return;
                    }
                    else email=getText(edemail);

                   if(mydb.updateEmployee(id_To_Update,name,mobile,email,position)){
                       Toast.makeText(this, "Updated Sucessfully", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(AddNew.this,MainActivity.class));
                   }else Toast.makeText(this, "Couldn't update values", Toast.LENGTH_SHORT).show();
                }
            }
            //if record doesnt exists uploads new data
            else{
                if(!editTextempty(edname)||!editTextempty(edposition)||!editTextempty(edemail)||!editTextempty(edmobile)){
                    return;
                }else {
                    name=getText(edname);
                    position=getText(edposition);

                    if (getText(edmobile).length()!=10){
                        edmobile.setError("Invalid Mobile number");
                        return;
                    }else mobile=getText(edmobile);


                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(getText(edemail)).matches()){
                        edemail.setError("Invalid email");
                        return;
                    }
                    else email=getText(edemail);

                    if(mydb.insertEmployee(name,mobile,email,position)){
                        Toast.makeText(this, "Inserted Sucessfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddNew.this,MainActivity.class));
                    }else Toast.makeText(this, "Couldn't insert values", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private  boolean  editTextempty(EditText editText){
        if (TextUtils.isEmpty(editText.getText())){
            editText.setError("Field cannot stay empty");
            return false;
        }
        return true;
    }

    private String getText(EditText editText){
        String text;
        text=editText.getText().toString().trim();
        return text;
    }


}