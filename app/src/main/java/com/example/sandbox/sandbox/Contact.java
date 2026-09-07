package com.example.sandbox.sandbox;

/** Modelo simple para RecyclerView */
public class Contact {
    public final String name;
    public final String email;
    public final String phone;
    public final int photoRes;

    public Contact(String name, String email, String phone, int photoRes) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.photoRes = photoRes;
    }
}
