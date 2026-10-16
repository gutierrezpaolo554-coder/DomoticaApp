package com.example.domoapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * CasaActivity - Control general de luces y enchufes de toda la casa
 * Con control de intensidad visual mediante SeekBar automático
 */
public class CasaActivity extends AppCompatActivity {

    // Botones de luces
    private ImageButton btnLuzP1, btnLuzP2, btnLuzP3, btnLuzExt;
    // Botones de enchufes
    private ImageButton btnEnchufeP1, btnEnchufeP2, btnEnchufeP3, btnEnchufeExt;
    // SeekBars de intensidad
    private SeekBar seekLuzP1, seekLuzP2, seekLuzP3, seekLuzExt;

    // Estados
    private boolean luzP1On = false, luzP2On = false, luzP3On = false, luzExtOn = false;
    private boolean enchufeP1On = false, enchufeP2On = false, enchufeP3On = false, enchufeExtOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casa);

        // Inicializar botones de luces
        btnLuzP1 = findViewById(R.id.btnLuzP1);
        btnLuzP2 = findViewById(R.id.btnLuzP2);
        btnLuzP3 = findViewById(R.id.btnLuzP3);
        btnLuzExt = findViewById(R.id.btnLuzExt);

        // Inicializar botones de enchufes
        btnEnchufeP1 = findViewById(R.id.btnEnchufeP1);
        btnEnchufeP2 = findViewById(R.id.btnEnchufeP2);
        btnEnchufeP3 = findViewById(R.id.btnEnchufeP3);
        btnEnchufeExt = findViewById(R.id.btnEnchufeExt);

        // Inicializar SeekBars
        seekLuzP1 = findViewById(R.id.seekLuzP1);
        seekLuzP2 = findViewById(R.id.seekLuzP2);
        seekLuzP3 = findViewById(R.id.seekLuzP3);
        seekLuzExt = findViewById(R.id.seekLuzExt);

        // ===== BOTÓN TODAS LAS LUCES =====
        findViewById(R.id.btnTodasLuces).setOnClickListener(v -> {
            boolean todasEncendidas = luzP1On && luzP2On && luzP3On && luzExtOn;
            boolean nuevoEstado = !todasEncendidas;

            luzP1On = nuevoEstado;
            luzP2On = nuevoEstado;
            luzP3On = nuevoEstado;
            luzExtOn = nuevoEstado;

            // Al encender todas, los seekbars van al máximo; al apagar, a cero
            int progreso = nuevoEstado ? 100 : 0;
            seekLuzP1.setProgress(progreso);
            seekLuzP2.setProgress(progreso);
            seekLuzP3.setProgress(progreso);
            seekLuzExt.setProgress(progreso);

            actualizarLuz(btnLuzP1, seekLuzP1, luzP1On);
            actualizarLuz(btnLuzP2, seekLuzP2, luzP2On);
            actualizarLuz(btnLuzP3, seekLuzP3, luzP3On);
            actualizarLuz(btnLuzExt, seekLuzExt, luzExtOn);
        });

        // ===== BOTÓN TODOS LOS ENCHUFES =====
        findViewById(R.id.btnTodosEnchufes).setOnClickListener(v -> {
            boolean todosEncendidos = enchufeP1On && enchufeP2On && enchufeP3On && enchufeExtOn;
            boolean nuevoEstado = !todosEncendidos;

            enchufeP1On = nuevoEstado;
            enchufeP2On = nuevoEstado;
            enchufeP3On = nuevoEstado;
            enchufeExtOn = nuevoEstado;

            toggleEnchufe(btnEnchufeP1, enchufeP1On);
            toggleEnchufe(btnEnchufeP2, enchufeP2On);
            toggleEnchufe(btnEnchufeP3, enchufeP3On);
            toggleEnchufe(btnEnchufeExt, enchufeExtOn);
        });

        // ===== LUCES POR PISO - Click corto (enciende/apaga + seekbar automático) =====
        btnLuzP1.setOnClickListener(v -> {
            luzP1On = !luzP1On;
            seekLuzP1.setProgress(luzP1On ? 100 : 0);  // SeekBar va al máximo o a cero
            actualizarLuz(btnLuzP1, seekLuzP1, luzP1On);
        });

        btnLuzP2.setOnClickListener(v -> {
            luzP2On = !luzP2On;
            seekLuzP2.setProgress(luzP2On ? 100 : 0);
            actualizarLuz(btnLuzP2, seekLuzP2, luzP2On);
        });

        btnLuzP3.setOnClickListener(v -> {
            luzP3On = !luzP3On;
            seekLuzP3.setProgress(luzP3On ? 100 : 0);
            actualizarLuz(btnLuzP3, seekLuzP3, luzP3On);
        });

        btnLuzExt.setOnClickListener(v -> {
            luzExtOn = !luzExtOn;
            seekLuzExt.setProgress(luzExtOn ? 100 : 0);
            actualizarLuz(btnLuzExt, seekLuzExt, luzExtOn);
        });

        // ===== ENCHUFES POR PISO =====
        btnEnchufeP1.setOnClickListener(v -> { enchufeP1On = !enchufeP1On; toggleEnchufe(btnEnchufeP1, enchufeP1On); });
        btnEnchufeP2.setOnClickListener(v -> { enchufeP2On = !enchufeP2On; toggleEnchufe(btnEnchufeP2, enchufeP2On); });
        btnEnchufeP3.setOnClickListener(v -> { enchufeP3On = !enchufeP3On; toggleEnchufe(btnEnchufeP3, enchufeP3On); });
        btnEnchufeExt.setOnClickListener(v -> { enchufeExtOn = !enchufeExtOn; toggleEnchufe(btnEnchufeExt, enchufeExtOn); });

        // ===== SEEKBARs DE INTENSIDAD (control manual) =====
        setupSeekBar(seekLuzP1, btnLuzP1, () -> luzP1On);
        setupSeekBar(seekLuzP2, btnLuzP2, () -> luzP2On);
        setupSeekBar(seekLuzP3, btnLuzP3, () -> luzP3On);
        setupSeekBar(seekLuzExt, btnLuzExt, () -> luzExtOn);
    }

    /**
     * Configura el SeekBar para cambiar la intensidad visual de la luz
     */
    private void setupSeekBar(SeekBar seekBar, ImageButton btnLuz, BooleanSupplier estadoLuz) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (estadoLuz.getAsBoolean()) {
                    if (progress > 0) {
                        btnLuz.setImageResource(R.drawable.luz_on);
                        // Alpha va de 0.3f (mínimo) a 1.0f (máximo)
                        float alpha = 0.3f + (progress / 100f) * 0.7f;
                        btnLuz.setAlpha(alpha);
                    } else {
                        btnLuz.setImageResource(R.drawable.luz_off);
                        btnLuz.setAlpha(1.0f);
                    }
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    /**
     * Actualiza el estado visual de una luz según su estado y seekbar
     */
    private void actualizarLuz(ImageButton btn, SeekBar seek, boolean encendida) {
        if (encendida) {
            btn.setImageResource(R.drawable.luz_on);
            int progress = seek.getProgress();
            if (progress > 0) {
                float alpha = 0.3f + (progress / 100f) * 0.7f;
                btn.setAlpha(alpha);
            } else {
                btn.setAlpha(1.0f);
            }
        } else {
            btn.setImageResource(R.drawable.luz_off);
            btn.setAlpha(1.0f);
        }
    }

    /**
     * Cambia la imagen del enchufe según su estado
     */
    private void toggleEnchufe(ImageButton btn, boolean encendido) {
        btn.setImageResource(encendido ? R.drawable.enchufe_on : R.drawable.enchufe_off);
    }

    /**
     * Interface funcional para obtener el estado de la luz
     */
    @FunctionalInterface
    private interface BooleanSupplier {
        boolean getAsBoolean();
    }
}