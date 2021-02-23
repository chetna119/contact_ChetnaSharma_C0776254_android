package com.example.contact_chetnasharma_c0776253.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Contact")
public class ContactInfo {

        @PrimaryKey(autoGenerate = true)
        private int id;

        @NonNull
        @ColumnInfo(name = "firstName")
        private String firstName;

        @NonNull
        @ColumnInfo(name = "lastName")
        private String lastName;

        @NonNull
        @ColumnInfo(name = "email")
        private String email;

        @NonNull
        @ColumnInfo(name = "phoneNumber")
        private String phoneNumber;

        @NonNull
        @ColumnInfo(name = "address")
        private String address;

    public ContactInfo(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    /**
     * setId() is mandatory
     * @param id
     */

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
