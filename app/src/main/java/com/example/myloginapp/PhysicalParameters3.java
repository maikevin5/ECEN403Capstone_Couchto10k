package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PhysicalParameters3 extends AppCompatActivity{

    private Button button;

    ListView listViewData;
    ArrayAdapter<String> adapter;
    String [] med_conditions = {"Respiratory Disease", "Cardiovascular Disease", "Pregnancy"};

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://couch-to-10k-testing-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_parameters3);

        button = findViewById(R.id.Submit_PP); //PP3 to welcome

        listViewData = findViewById(R.id.listView_data);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, med_conditions);
        listViewData.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                //Toast.makeText(PhysicalParameters3.this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                String txt_UID = currentFirebaseUser.getUid();

                SparseBooleanArray checked = listViewData.getCheckedItemPositions();
                for (int j = 0; j < listViewData.getCount(); j++) {
                    if (checked.get(j)) {
                        String item = med_conditions[j];
                        if (j == 0) {
                            databaseReference.child("User ID").child(txt_UID).child("medical conditions").child("Respiratory Disease").setValue(1);
                        }
                        else if (j == 1) {
                            databaseReference.child("User ID").child(txt_UID).child("medical conditions").child("Cardiovascular Disease").setValue(1);
                        }
                        else if (j == 2) {
                            databaseReference.child("User ID").child(txt_UID).child("medical conditions").child("Pregnancy").setValue(1);
                        }
                    }
                }


                Intent intent = new Intent(PhysicalParameters3.this, Cto10k_Context.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_done){
            String itemSelected = "Selected items: \n";
            for (int i=0; i < listViewData.getCount();i++) {
                if (listViewData.isItemChecked(i)) {
                    itemSelected += listViewData.getItemAtPosition(i) + "\n";
                }
            }
            Toast.makeText(this, itemSelected, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}