package com.example.contact_chetnasharma_c0776253;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contact_chetnasharma_c0776253.db.ContactInfo;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder> implements Filterable {

    private List<ContactInfo> contactInfoList;
    private List<ContactInfo> contactInfoListFull;

    class ContactListViewHolder extends RecyclerView.ViewHolder {
        TextView firstNameTV;
        TextView lastNameTV;
        TextView emailTV;
        TextView contactTV;
        TextView addressTV;

        public ContactListViewHolder(@NonNull View itemView) {
            super(itemView);
            firstNameTV = itemView.findViewById(R.id.tv_firstName);
            lastNameTV = itemView.findViewById(R.id.tv_lastName);
            emailTV = itemView.findViewById(R.id.tv_email);
            contactTV = itemView.findViewById(R.id.tv_contact);
            addressTV = itemView.findViewById(R.id.tv_address);
        }
    }

    ContactListAdapter(List<ContactInfo> contactInfoList) {
        this.contactInfoList = contactInfoList;
        contactInfoListFull = new ArrayList<>(contactInfoList);
    }

    @NonNull
    @Override
    public ContactListAdapter.ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list,
                parent, false);
        return new ContactListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ContactListViewHolder holder, int position) {

        ContactInfo contactInfo = contactInfoList.get(position);

        holder.firstNameTV.setText(contactInfo.getFirstName());
        holder.lastNameTV.setText(contactInfo.getLastName());
        holder.emailTV.setText(contactInfo.getEmail());
        holder.contactTV.setText(contactInfo.getPhoneNumber());
        holder.addressTV.setText(contactInfo.getAddress());
    }

    @Override
    public int getItemCount() {
        return contactInfoList.size();
    }

    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ContactInfo> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(contactInfoListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ContactInfo contactInfo : contactInfoListFull) {
                    if (contactInfo.getFirstName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(contactInfo);
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


    @Override
    public Filter getFilter() {
        return contactFilter;
    }
}
