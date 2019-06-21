/**
 * Created by Abdul on 18/06/19.
 * Main Activity class for showing the uploaded data to the user
 */
package com.abdul.sqliteassingment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter arrayAdapter;
    FloatingActionButton floatingButton;
    ArrayList<String > arrayList;
    DBHelper mydb;
    int id_To_Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mydb = new DBHelper(this);
        arrayList = mydb.getAllEmployees();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);





        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setEmptyView(findViewById(R.id.image));

        //for when you select a previously entered record
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Bundle dataBundle = new Bundle();

                //getting the id of the employee selected from the list
                String name=listView.getItemAtPosition(position).toString();
                Cursor rs = mydb.getId(name);
                rs.moveToFirst();
                id_To_Search=rs.getInt(0);

                //sending the fetched id to the next class to fetch the uploaded data
                dataBundle.putInt("id", id_To_Search);
                listView.setFocusableInTouchMode(true);
                Intent intent = new Intent(MainActivity.this, AddNew.class);
                intent.putExtras(dataBundle);

                if (!rs.isClosed())
                    rs.close();
                startActivity(intent);
            }
        });

        floatingButton = (FloatingActionButton) findViewById(R.id.floatingButton);

        //for when you want to add new data
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(MainActivity.this, AddNew.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
            }
        });
    }

    //exits app on pressing back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}