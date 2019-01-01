package com.khahani.app.farhat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class PersonListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);

        Button nobat = findViewById(R.id.buttonNobat);
        nobat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nobatBegir();
            }
        });

    }

    public static ArrayList<Person> sAllPerson;

    private void nobatBegir(){

        sAllPerson = new ArrayList<>();

        int count = 5;

        for (int i = 0; i < count; i++) {

            EditText code = findViewById(getResources().getIdentifier("p"+i+"_code", "id", getPackageName()));
            EditText name = findViewById(getResources().getIdentifier("p"+i+"_name", "id", getPackageName()));
            EditText lastName = findViewById(getResources().getIdentifier("p"+i+"_lastname", "id", getPackageName()));
            EditText phone = findViewById(getResources().getIdentifier("p"+i+"_phone", "id", getPackageName()));

            if (code.getText().toString().equals(""))
                continue;

            Person person = new Person(
                    name.getText().toString(),
                    lastName.getText().toString(),
                    code.getText().toString(),
                    phone.getText().toString()
            );

            sAllPerson.add(person);

            Intent intent = new Intent(PersonListActivity.this, MutliTabActivity.class);
            startActivity(intent);

        }
    }
}
