package com.example.contact_chetnasharma_c0776253;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contact_chetnasharma_c0776253.db.ContactInfo;
import com.example.contact_chetnasharma_c0776253.ContactListActivity;
import com.example.contact_chetnasharma_c0776253.db.ContactRoomDb;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter implements Filterable {

    Context context;
    int layoutRes;
    List<ContactInfo> contactInfoList;
    List<ContactInfo> contactInfoListFull;
    ContactRoomDb contactRoomDb;

    public ContactAdapter(@NonNull Context context, int resource, List<ContactInfo> contactInfoList) {
        super(context, resource, contactInfoList);
        this.contactInfoList = contactInfoList;
        this.context = context;
        this.layoutRes = resource;
        contactRoomDb = ContactRoomDb.getINSTANCE(context);

        contactInfoListFull = new ArrayList<>(contactInfoList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layoutRes, null);
        TextView firstNameTV = v.findViewById(R.id.tv_firstName);
        TextView lastNameTV = v.findViewById(R.id.tv_lastName);
        TextView emailTV = v.findViewById(R.id.tv_email);
        TextView contactTV = v.findViewById(R.id.tv_contact);
        TextView addressTV = v.findViewById(R.id.tv_address);

        final ContactInfo contactInfo = contactInfoList.get(position);
        firstNameTV.setText("First Name: " + contactInfo.getFirstName());
        lastNameTV.setText("Last Name: " + contactInfo.getLastName());
        emailTV.setText("Email: " + contactInfo.getEmail());
        contactTV.setText("Contact: " + contactInfo.getPhoneNumber());
        addressTV.setText("Address: " + contactInfo.getAddress());

        v.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact(contactInfo);
            }

            private void updateContact(final ContactInfo contactInfo) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.alert_dialog_contact, null);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final EditText firstNameET = view.findViewById(R.id.et_first_name);
                final EditText lastNameET = view.findViewById(R.id.et_last_name);
                final EditText emailET = view.findViewById(R.id.et_email);
                final EditText contactET = view.findViewById(R.id.et_contact);
                final EditText addressET = view.findViewById(R.id.et_address);

                firstNameET.setText(contactInfo.getFirstName());
                lastNameET.setText(contactInfo.getLastName());
                emailET.setText(contactInfo.getEmail());
                contactET.setText(contactInfo.getPhoneNumber());
                addressET.setText(contactInfo.getAddress());

                view.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String firstName = firstNameET.getText().toString().trim();
                        String lastName = lastNameET.getText().toString().trim();
                        String email = emailET.getText().toString().trim();
                        String contact = contactET.getText().toString().trim();
                        String address = addressET.getText().toString().trim();

                        if (firstName.isEmpty()) {
                            firstNameET.setError("First Name is required");
                            firstNameET.requestFocus();
                            return;
                        }
                        if (email.isEmpty()) {
                            emailET.setError("First Name is necessary");
                            emailET.requestFocus();
                            return;
                        }
                        if (contact.isEmpty()) {
                            contactET.setError("First Name is necessary");
                            contactET.requestFocus();
                            return;
                        }

                        contactRoomDb.contactDao().updateContact(contactInfo.getId(),
                                firstName,
                                lastName,
                                email,
                                contact,
                                address);
                        loadContacts();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact(contactInfo);
            }

            private void deleteContact(final ContactInfo contactInfo) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        contactRoomDb.contactDao().deleteContact(contactInfo.getId());
                        loadContacts();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Contact info of " + contactInfo.getFirstName() + " is still in database", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        return v;
    }

    @Override
    public int getCount() {
        return contactInfoList.size();
    }

    private void loadContacts() {
        contactInfoList = contactRoomDb.contactDao().getAllContacts();
        notifyDataSetChanged();
        realTimeContactsNumber();
    }

    private void realTimeContactsNumber(){
        com.example.contact_chetnasharma_c0776253.ContactListActivity.setNoOfContacts(contactInfoList.size());
    }

    public Filter getFilter() {
        return contactFilter;
    }
    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ContactInfo> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(contactInfoListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ContactInfo item : contactInfoListFull) {
                    if (item.getFirstName().toLowerCase().contains(filterPattern) || item.getLastName().toLowerCase().contains(filterPattern) || item.getPhoneNumber().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactInfoList.clear();
            contactInfoList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
