package com.example.contact_chetnasharma_c0776253;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contact_chetnasharma_c0776253.db.ContactInfo;
import com.google.android.material.snackbar.Snackbar;
import com.example.contact_chetnasharma_c0776253.db.ContactRoomDb;

import java.util.regex.Pattern;


public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {

    ContactRoomDb contactRoomDb;

    EditText firstNameEditText, lastNameEditText, emailEditText, contactEditText, addressEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        initials();
    }

    private void initials() {
        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        emailEditText = findViewById(R.id.email);
        contactEditText = findViewById(R.id.phoneNumber);
        addressEditText = findViewById(R.id.address);

        findViewById(R.id.addContactButton).setOnClickListener(this);
        findViewById(R.id.contactListButton).setOnClickListener(this);

        // Room database
        contactRoomDb = ContactRoomDb.getINSTANCE(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addContactButton:
                addContactToDatabase();
                break;
            case R.id.contactListButton:
                showContactList();
                break;
        }
    }

    private void showContactList() {

        startActivity(new Intent(this, ContactListActivity.class));
        finish();

    }

    private void addContactToDatabase() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String contact = contactEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        if(firstName.isEmpty()){
            firstNameEditText.setError("First Name is required");
            firstNameEditText.requestFocus();
            return;
        }
        if(email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }
        if(!isValidEmailId(email)){
            emailEditText.setError("Enter Valid email address");
            emailEditText.requestFocus();
            return;
        }
        if(contact.isEmpty()){
            contactEditText.setError("Contact is required");
            contactEditText.requestFocus();
            return;
        }
        if(contact.length()!= 10){
            contactEditText.setError("Invalid contact number");
            contactEditText.requestFocus();
            return;
        }

        // insert into room db
        ContactInfo contactInfo = new ContactInfo(firstName,lastName,email,contact,address);
        contactRoomDb.contactDao().insertContact(contactInfo);
        clearFields();
        Snackbar.make(findViewById(android.R.id.content),"Contact Inserted", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        firstNameEditText.setText("");
        lastNameEditText.setText("");
        emailEditText.setText("");
        contactEditText.setText("");
        addressEditText.setText("");

        firstNameEditText.requestFocus();
        lastNameEditText.clearFocus();
        emailEditText.clearFocus();
        contactEditText.clearFocus();
        addressEditText.clearFocus();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(AddContactActivity.this, ContactListActivity.class);
        startActivity(intent);
        finish();
    }

    private void clearFields(){
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        emailEditText.setText("");
        contactEditText.setText("");
        addressEditText.setText("");
        lastNameEditText.clearFocus();
        emailEditText.clearFocus();
        contactEditText.clearFocus();
        addressEditText.clearFocus();
        firstNameEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(firstNameEditText.getWindowToken(), 0);
    }

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}