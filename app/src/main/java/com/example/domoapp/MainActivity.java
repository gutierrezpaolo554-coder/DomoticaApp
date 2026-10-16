package com.example.domoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity - Pantalla principal de la aplicación Domótica
 * Contiene el menú principal con navegación a cada sección
 */
public class MainActivity extends AppCompatActivity {

    // Declaración de botones
    private Button btnCasa, btnPrimerPiso, btnSegundoPiso, btnTercerPiso, btnExteriorGaraje;
    private ImageView imgUJCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        imgUJCV = findViewById(R.id.imgUJCV);
        btnCasa = findViewById(R.id.btnCasa);
        btnPrimerPiso = findViewById(R.id.btnPrimerPiso);
        btnSegundoPiso = findViewById(R.id.btnSegundoPiso);
        btnTercerPiso = findViewById(R.id.btnTercerPiso);
        btnExteriorGaraje = findViewById(R.id.btnExteriorGaraje);

        // Click largo en imagen UJCV - Muestra ventana emergente con información
        imgUJCV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mostrarInformacionApp();
                return true;
            }
        });

        // Click corto en botón Casa
        btnCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CasaActivity.class);
                startActivity(intent);
            }
        });

        // Click corto en botón Primer Piso
        btnPrimerPiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrimerPisoActivity.class);
                startActivity(intent);
            }
        });

        // Click corto en botón Segundo Piso
        btnSegundoPiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SegundoPisoActivity.class);
                startActivity(intent);
            }
        });

        // Click corto en botón Tercer Piso
        btnTercerPiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TercerPisoActivity.class);
                startActivity(intent);
            }
        });

        // Click corto en botón Exterior y Garaje
        btnExteriorGaraje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExteriorGarajeActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Muestra ventana emergente con información de la aplicación
     */
    private void mostrarInformacionApp() {
        // Crear vista personalizada para el diálogo
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_info, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}