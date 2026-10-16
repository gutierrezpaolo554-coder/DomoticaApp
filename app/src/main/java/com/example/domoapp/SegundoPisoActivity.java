package com.example.domoapp;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

/**
 * SegundoPisoActivity - Control de luces y enchufes del Segundo Piso (Dormitorio)
 */
public class SegundoPisoActivity extends AppCompatActivity {

    private ImageButton btnLuz, btnEnchufe;
    private SeekBar seekLuz;
    private boolean luzOn = false, enchufeOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo_piso);

        btnLuz = findViewById(R.id.btnLuzP2);
        btnEnchufe = findViewById(R.id.btnEnchufeP2);
        seekLuz = findViewById(R.id.seekLuzP2);

        btnLuz.setOnClickListener(v -> {
            luzOn = !luzOn;
            seekLuz.setProgress(luzOn ? 100 : 0);
            actualizarLuz();
        });

        seekLuz.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (luzOn) {
                    if (progress > 0) {
                        btnLuz.setImageResource(R.drawable.luz_on);
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

        btnEnchufe.setOnClickListener(v -> {
            enchufeOn = !enchufeOn;
            btnEnchufe.setImageResource(enchufeOn ? R.drawable.enchufe_on : R.drawable.enchufe_off);
        });
    }

    private void actualizarLuz() {
        if (luzOn) {
            btnLuz.setImageResource(R.drawable.luz_on);
            int progress = seekLuz.getProgress();
            if (progress > 0) {
                float alpha = 0.3f + (progress / 100f) * 0.7f;
                btnLuz.setAlpha(alpha);
            } else {
                btnLuz.setAlpha(1.0f);
            }
        } else {
            btnLuz.setImageResource(R.drawable.luz_off);
            btnLuz.setAlpha(1.0f);
        }
    }
}