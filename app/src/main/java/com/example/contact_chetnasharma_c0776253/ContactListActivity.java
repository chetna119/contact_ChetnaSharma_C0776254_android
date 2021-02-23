package com.example.contact_chetnasharma_c0776253;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contact_chetnasharma_c0776253.db.ContactInfo;
import com.example.contact_chetnasharma_c0776253.db.ContactRoomDb;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    private ContactRoomDb contactRoomDb;

    TextView contactsNumber;
    static TextView noOfContacts;
    List<ContactInfo> contactInfoList;
    ListView contactListView;

    ContactAdapter contactAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        initials();


    }

    private void initials() {
        contactListView = findViewById(R.id.contact_list_lv);
        noOfContacts = findViewById(R.id.tv_no_of_contacts);
        contactsNumber = findViewById(R.id.tv_no_of_contacts);
        contactInfoList = new ArrayList<>();
        contactRoomDb = ContactRoomDb.getINSTANCE(this);
        loadContact();
    }

    private void loadContact() {
        contactInfoList = contactRoomDb.contactDao().getAllContacts();
        contactAdapter = new ContactAdapter(this, R.layout.contact_list, contactInfoList);
        contactListView.setAdapter(contactAdapter);
        contactsNumber.setText("No of contacts: "+ contactInfoList.size());
    }

    public static void setNoOfContacts(int contactsNumber) {
        noOfContacts.setText("No of contacts: " + contactsNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_drawer, menu);

        MenuItem searchItem = menu.findItem(R.id.btnSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnAdd) {
            Intent intent = new Intent(ContactListActivity.this, AddContactActivity.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.btnSearch) {

        }
        return super.onOptionsItemSelected(item);
    }

}