package com.example.receptorintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent receptorIntent = getIntent();
        String action = receptorIntent.getAction();
        String tipo = receptorIntent.getType();
        Button btn_map = (Button)findViewById(R.id.btn_map);
        final TextView txt_direccion = (TextView)findViewById(R.id.txt_direccion);

        if (Intent.ACTION_SEND_MULTIPLE.equals(action) && tipo != null){
            if("text/plain".equals(tipo)){
                ActualizarDatos(receptorIntent);
            }
        }

        /* Ubicar direccion*/
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uri location = Uri.parse("geo:0.0?q=-18.027760,-70.250985");
                String aux = txt_direccion.getText().toString();
                Intent locationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0.0?q="+aux));
                if (locationIntent.resolveActivity(getPackageManager()) != null){
                    if (txt_direccion.getText().equals(getResources().getText(R.string.hnt_direccion).toString())) {
                        Toast.makeText(getApplicationContext(),"Debe tener una direccion valida",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        startActivity(locationIntent);
                    }
                }
                else {
                    Toast.makeText(null, "No existe Actividad para esta accion",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void ActualizarDatos(Intent intent) {
        String nombre = intent.getStringExtra("nombre");
        String apellido = intent.getStringExtra("apellido");
        String celular = intent.getStringExtra("celular");
        String direccion = intent.getStringExtra("direccion");
        String email = intent.getStringExtra("email");
        Bitmap imagen = (Bitmap) intent.getParcelableExtra("imagen");

        if (nombre != null && apellido != null && celular != null && direccion != null && email != null){
            ((TextView)findViewById(R.id.txt_nombre)).setText(nombre);
            ((TextView)findViewById(R.id.txt_apellido)).setText(apellido);
            ((TextView)findViewById(R.id.txt_celular)).setText(celular);
            ((TextView)findViewById(R.id.txt_direccion)).setText(direccion);
            ((TextView)findViewById(R.id.txt_email)).setText(email);
            ((ImageView)findViewById(R.id.img_camera)).setImageBitmap(imagen);
        }
    }
}
