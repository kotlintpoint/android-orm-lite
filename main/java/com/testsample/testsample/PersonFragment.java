package com.testsample.testsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by Dot on 1/18/2018.
 */

public class PersonFragment extends Fragment
{
    EditText edtName,edtmob,edtEmail,edtAddress;
    RadioButton rdbMAle,rdbFemale,radioSexButton;
    Button btnSave,btnEdit,btnDelete;
    RadioGroup radioSexGroup;


    PersonDetails pd;
    public AlertDialog alert;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final  View v = inflater.inflate(R.layout.person_fragment, null);

        edtName=(EditText)v.findViewById(R.id.edtName);
        edtmob=(EditText)v.findViewById(R.id.edtMobile);
        edtEmail=(EditText)v.findViewById(R.id.edtEmail);
        edtAddress=(EditText)v.findViewById(R.id.edtAddress);

        radioSexGroup=(RadioGroup)v.findViewById(R.id.radioGroup);
        rdbMAle=(RadioButton) v.findViewById(R.id.rdbMale);
        rdbFemale=(RadioButton) v.findViewById(R.id.rdbFemale);

        btnSave=(Button)v.findViewById(R.id.btnSave);
        btnEdit=(Button)v.findViewById(R.id.btnEdit);
        btnDelete=(Button)v.findViewById(R.id.btnDelete);

        if(AddEditPersonActivity.pdObject!=null)
        {
            btnEdit.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.INVISIBLE);
        }
        else
        {
            btnEdit.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
            btnSave.setVisibility(View.VISIBLE);
        }

        if(AddEditPersonActivity.pdObject!=null)
        {
            edtName.setText(AddEditPersonActivity.pdObject.getName());
            edtmob.setText(AddEditPersonActivity.pdObject.getMobile());
            edtEmail.setText(AddEditPersonActivity.pdObject.getEmail());
            edtAddress.setText(AddEditPersonActivity.pdObject.getAddress());

            if(AddEditPersonActivity.pdObject.getGender().equals("Male"))
                rdbMAle.setChecked(true);
            if(AddEditPersonActivity.pdObject.getGender().equals("Female"))
                rdbFemale.setChecked(true);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(checkForValidation())
                    createNewRecord(v);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(checkForValidation())
                    updateRecord(v);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                displayAlertContext("Are you sure? You want to delete record?");
            }
        });

        return v;
    }

    public boolean checkForValidation()
    {
        if(edtName.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "Person name is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidMobileNumber(edtmob.getText().toString()))
        {
            Toast.makeText(getActivity(), "Incorrect mobile number", Toast.LENGTH_SHORT).show();
            edtmob.requestFocus();
            return false;
        }
        if(!isValidEmail(edtEmail.getText().toString()))
        {
            Toast.makeText(getActivity(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
            edtEmail.requestFocus();
            return false;
        }

        return true;
    }

    public void createNewRecord(View v )
    {
        pd=new PersonDetails();

        int selectedId=radioSexGroup.getCheckedRadioButtonId();
        radioSexButton=(RadioButton)v.findViewById(selectedId);

        pd.setName(edtName.getText().toString());
        pd.setMobile(edtmob.getText().toString());
        pd.setEmail(edtEmail.getText().toString());
        pd.setAddress(edtAddress.getText().toString());
        pd.setGender(radioSexButton.getText().toString());

        try {
            AddEditPersonActivity.dbHelper.create(pd);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Toast.makeText(getActivity(), "Record saved successfully", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    public void updateRecord(View v )
    {

        PersonDetails p= AddEditPersonActivity.pdObject;
        if(p!=null)
        {
            int selectedId=radioSexGroup.getCheckedRadioButtonId();
            radioSexButton=(RadioButton)v.findViewById(selectedId);

            p.setName(edtName.getText().toString());
            p.setMobile(edtmob.getText().toString());
            p.setEmail(edtEmail.getText().toString());
            p.setAddress(edtAddress.getText().toString());
            p.setGender(radioSexButton.getText().toString());

            try {
                AddEditPersonActivity.dbHelper.update(p);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Toast.makeText(getActivity(), "Record edited successfully", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    public void displayAlertContext( String strMessage)
    {
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.app_name);
            builder.setMessage(strMessage).setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            PersonDetails p= AddEditPersonActivity.pdObject;
                            if(p!=null)
                            {
                                try {
                                    AddEditPersonActivity.dbHelper.deleteById(PersonDetails.class,  p.getId());
                                    Toast.makeText(getActivity(), "Record deleted successfully", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                } catch (SQLException e) {
                                    e.printStackTrace();

                                }
                            }
                            else
                                Toast.makeText(getActivity(), "Unable to DELETE record", Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id)
                {
                    dialog.dismiss();
                }
            });

            if (alert != null && alert.isShowing()) {
                alert = null;
            }
            alert = builder.create();
            alert.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static  boolean isValidMobileNumber(String phone2)
    {
        boolean check;
        if (phone2.length() >=10 && phone2.length()<=12) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }
}
