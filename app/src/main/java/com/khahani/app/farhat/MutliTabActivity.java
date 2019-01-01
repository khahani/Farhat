package com.khahani.app.farhat;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MutliTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutli_tab);

        int count;
        ArrayList<Person> persons = PersonListActivity.sAllPerson;
        count = persons.size();

//        for (int i = 0; i < count; i++) {
//            persons.add(new Person("محمممم", "خوووو", "123"+i+"59989" + i, "1111"+i+"11911" + i));
//        }

        for (int i = 0; i < count; i++) {
            WebViewFragment fragment = WebViewFragment.newInstance(persons.get(i));

            getSupportFragmentManager().beginTransaction()
                    .add(getResources().getIdentifier("f" + i, "id", getPackageName()), fragment).commit();
        }

    }
}
