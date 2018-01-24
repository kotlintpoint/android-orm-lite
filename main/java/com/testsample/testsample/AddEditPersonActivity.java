package com.testsample.testsample;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Dot on 1/18/2018.
 */

public class AddEditPersonActivity extends Activity
{
   public static PersonDetails pdObject;
    public static DBHelper dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity);

        dbHelper=new DBHelper(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PersonFragment hello = new PersonFragment();
        fragmentTransaction.add(R.id.fragment_container, hello, "HELLO");
        fragmentTransaction.commit();

        pdObject = (PersonDetails) getIntent().getSerializableExtra("pdObject");


    }

}
