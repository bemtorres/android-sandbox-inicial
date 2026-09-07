package com.example.sandbox.sandbox;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sandbox.R;
import com.example.sandbox.utils.LoggerUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Lab 10 — RecyclerView con lista de contactos.
 * Componente: | foto | nombre / correo / teléfono | (card)
 * Demuestra: RecyclerView + LayoutManager + Adapter + ViewHolder + reciclaje.
 */
public class ContactRecyclerActivity extends AppCompatActivity {
    private static final String TAG = "SANDBOX_RECYCLER";
    private final List<Contact> contacts = new ArrayList<>();
    private ContactAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_recycler);

        RecyclerView rv = findViewById(R.id.rvContacts);
        rv.setLayoutManager(new LinearLayoutManager(this));
        fillData();
        adapter = new ContactAdapter(contacts, c -> {
            Toast.makeText(this, getString(R.string.acrecy_toast_click, c.name, c.email), Toast.LENGTH_SHORT).show();
            LoggerUtils.d(TAG, "Recycler click: "+c.name);
        });
        rv.setAdapter(adapter);

        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            int n = contacts.size()+1;
            contacts.add(new Contact("Nuevo "+n, "nuevo"+n+"@ejemplo.com", "+34 600 000 "+String.format("%03d", n), R.mipmap.ic_launcher_round));
            adapter.notifyItemInserted(contacts.size()-1);
            LoggerUtils.i(TAG, "Contacto añadido, total="+contacts.size());
        });

        LoggerUtils.i(TAG, "RecyclerView con "+contacts.size()+" contactos + add dinámico");
    }

    private void fillData(){
        contacts.add(new Contact("Ana García","ana@ejemplo.com","+34 600 111 222", R.mipmap.ic_launcher_round));
        contacts.add(new Contact("Luis Pérez","luis@ejemplo.com","+34 600 222 333", R.mipmap.ic_launcher_round));
        contacts.add(new Contact("María López","maria@ejemplo.com","+34 600 333 444", R.mipmap.ic_launcher_round));
        contacts.add(new Contact("Javier Ruiz","javier@ejemplo.com","+34 600 444 555", R.mipmap.ic_launcher_round));
        contacts.add(new Contact("Sofía Díaz","sofia@ejemplo.com","+34 600 555 666", R.mipmap.ic_launcher_round));
        contacts.add(new Contact("Carlos Martín","carlos@ejemplo.com","+34 600 666 777", R.mipmap.ic_launcher_round));
        contacts.add(new Contact("Elena Torres","elena@ejemplo.com","+34 600 777 888", R.mipmap.ic_launcher_round));
        contacts.add(new Contact("Pedro Sánchez","pedro@ejemplo.com","+34 600 888 999", R.mipmap.ic_launcher_round));
    }
}
