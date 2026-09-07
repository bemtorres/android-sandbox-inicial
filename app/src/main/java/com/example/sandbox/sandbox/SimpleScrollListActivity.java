package com.example.sandbox.sandbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sandbox.R;
import com.example.sandbox.utils.LoggerUtils;
import android.widget.TextView;
import android.widget.ImageView;

/**
 * Lab 9 — Lista simple con ScrollView + LinearLayout.
 * Para listas cortas (<20). No hay reciclaje: cada fila se infla y queda en memoria.
 * Cada fila es item_contact-like inflado dinámicamente.
 */
public class SimpleScrollListActivity extends AppCompatActivity {
    private static final String TAG = "SANDBOX_SCROLL";
    private LinearLayout container;

    private final String[][] data = {
        {"Ana García","ana@ejemplo.com","+34 600 111 222"},
        {"Luis Pérez","luis@ejemplo.com","+34 600 222 333"},
        {"María López","maria@ejemplo.com","+34 600 333 444"},
        {"Javier Ruiz","javier@ejemplo.com","+34 600 444 555"},
        {"Sofía Díaz","sofia@ejemplo.com","+34 600 555 666"},
        {"Carlos Martín","carlos@ejemplo.com","+34 600 666 777"},
        {"Elena Torres","elena@ejemplo.com","+34 600 777 888"},
        {"Pedro Sánchez","pedro@ejemplo.com","+34 600 888 999"},
        {"Laura Gómez","laura@ejemplo.com","+34 600 999 000"},
        {"David Jiménez","david@ejemplo.com","+34 600 000 111"},
    };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_scroll_list);
        container = findViewById(R.id.container);
        LayoutInflater inflater = LayoutInflater.from(this);

        for(String[] row : data){
            View item = inflater.inflate(R.layout.item_contact, container, false);
            ((TextView)item.findViewById(R.id.tvName)).setText(row[0]);
            ((TextView)item.findViewById(R.id.tvEmail)).setText(row[1]);
            ((TextView)item.findViewById(R.id.tvPhone)).setText(row[2]);
            ((ImageView)item.findViewById(R.id.imgPhoto)).setImageResource(R.mipmap.ic_launcher_round);
            String name = row[0];
            item.setOnClickListener(v->{
                Toast.makeText(this, getString(R.string.acscro_toast, name), Toast.LENGTH_SHORT).show();
                LoggerUtils.d(TAG, "Scroll click: "+name);
            });
            container.addView(item);
        }
        LoggerUtils.i(TAG, "ScrollView lista con "+data.length+" contactos inflados estáticamente");
    }
}
