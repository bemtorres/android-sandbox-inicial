package com.example.sandbox.sandbox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sandbox.R;
import java.util.List;

/**
 * Adapter + ViewHolder para RecyclerView.
 * Patrón: onCreateViewHolder infla item_contact, onBindViewHolder bindea datos, ViewHolder cachea findViewById.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.VH> {

    public interface OnClick { void onClick(Contact c); }

    private final List<Contact> list;
    private final OnClick listener;

    public ContactAdapter(List<Contact> list, OnClick listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Contact c = list.get(position);
        h.tvName.setText(c.name);
        h.tvEmail.setText(c.email);
        h.tvPhone.setText(c.phone);
        h.imgPhoto.setImageResource(c.photoRes);
        h.itemView.setOnClickListener(v -> listener.onClick(c));
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvPhone;
        ImageView imgPhoto;
        VH(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
        }
    }
}
