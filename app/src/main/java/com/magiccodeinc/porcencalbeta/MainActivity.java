package com.magiccodeinc.porcencalbeta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.view.ViewCompat;

import com.google.android.gms.ads.MobileAds;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerFormat;
    private DecimalFormat decimalFormat, decimal;
    private EditText edtInitialValue;
    private EditText edtPercentage;
    private TextView tvResult, tvResult1, tvResult2, tvResult3, tvResult4;
    private Toolbar toolbar;
    private FrameLayout adContainer; // Nuevo: contenedor del banner
    private BannerAdManager bannerAdManager;

    @SuppressLint({"MissingInflatedId", "ObsoleteSdkInt"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar Edge-to-Edge
        Window window = getWindow();
        WindowCompat.setDecorFitsSystemWindows(window, false);
        setContentView(R.layout.activity_main);

        // Inicializar el SDK de AdMob
        MobileAds.initialize(this, initializationStatus -> {});

        // Llamar a enableEdgeToEdge para retrocompatibilidad
        enableEdgeToEdge();

        // Inicialización de vistas
        initializeViews();

        // Inicialización del contenedor del banner
        adContainer = findViewById(R.id.ad_view_container);
        bannerAdManager = new BannerAdManager(this, adContainer);
        bannerAdManager.loadAd();

        // Configuración de la Toolbar
        setupToolbar();

        // Configuración de Edge-to-Edge
        setupEdgeToEdge();

        // Configuración simplificada del Spinner (texto blanco)
        setupSpinner();

        // Inicializar formatos decimales
        decimalFormat = new DecimalFormat("0.00");
        decimal = new DecimalFormat("0.0");

        // Configurar listeners
        setupListeners();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        spinnerFormat = findViewById(R.id.spinnerFormat);
        edtInitialValue = findViewById(R.id.edtInitialValue);
        edtPercentage = findViewById(R.id.edtPercentage);
        tvResult = findViewById(R.id.tvResult);
        tvResult1 = findViewById(R.id.tvResult1);
        tvResult2 = findViewById(R.id.tvResult2);
        tvResult3 = findViewById(R.id.tvResult3);
        tvResult4 = findViewById(R.id.tvResult4);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bannerAdManager != null) bannerAdManager.onResume();
    }

    @Override
    protected void onPause() {
        if (bannerAdManager != null) bannerAdManager.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (bannerAdManager != null) bannerAdManager.onDestroy();
        super.onDestroy();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setupEdgeToEdge() {
        Window window = getWindow();
        WindowCompat.setDecorFitsSystemWindows(window, false);

        // Controlar visibilidad de íconos en barras
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(window, window.getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
        insetsController.setAppearanceLightNavigationBars(true);

        // Ajuste de padding para la toolbar
        if (toolbar != null) {
            ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
                int top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
                v.setPadding(v.getPaddingLeft(), top, v.getPaddingRight(), v.getPaddingBottom());
                return insets;
            });
        }

        // Ajuste de margen inferior para el CardView
        androidx.cardview.widget.CardView cardView = findViewById(R.id.cardView);
        if (cardView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(cardView, (v, insets) -> {
                int bottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                params.bottomMargin = bottom + 16;
                v.setLayoutParams(params);
                return insets;
            });
        }
    }

    private void enableEdgeToEdge() {
        Window window = getWindow();
        WindowCompat.setDecorFitsSystemWindows(window, false);

        // Controlar visibilidad de íconos en barras
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(window, window.getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
        insetsController.setAppearanceLightNavigationBars(true);

        // Ajuste de padding para la toolbar
        if (toolbar != null) {
            ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
                int top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
                v.setPadding(v.getPaddingLeft(), top, v.getPaddingRight(), v.getPaddingBottom());
                return insets;
            });
        }

        // Ajuste de margen inferior para el CardView
        androidx.cardview.widget.CardView cardView = findViewById(R.id.cardView);
        if (cardView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(cardView, (v, insets) -> {
                int bottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                params.bottomMargin = bottom + 16;
                v.setLayoutParams(params);
                return insets;
            });
        }
    }

    private void setupSpinner() {
        // Crear adapter con array de formatos
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.format_options,
                android.R.layout.simple_spinner_item
        );

        // Personalizar el dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Configurar el spinner
        spinnerFormat.setAdapter(adapter);

        // Listener para manejar la selección y color del texto
        spinnerFormat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Cambiar color del texto a blanco
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(Color.BLACK);
                }

                // Actualizar formato decimal según selección
                updateDecimalFormat(position);
                updateResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Asegurar texto blanco inicial
        View view = spinnerFormat.getSelectedView();
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(Color.WHITE);
        }
    }

    private void updateDecimalFormat(int position) {
        switch (position) {
            case 0: decimalFormat = new DecimalFormat("0"); break;
            case 1: decimalFormat = new DecimalFormat("0.00"); break;
            case 2: decimalFormat = new DecimalFormat("0.000"); break;
            case 3: decimalFormat = new DecimalFormat("0.0000"); break;
        }
    }

    private void setupListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateResult();
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        edtInitialValue.addTextChangedListener(textWatcher);
        edtPercentage.addTextChangedListener(textWatcher);
    }

    @SuppressLint("SetTextI18n")
    private void updateResult() {
        String initialValueStr = edtInitialValue.getText().toString();
        String percentageStr = edtPercentage.getText().toString();

        if (!initialValueStr.isEmpty() && !percentageStr.isEmpty()) {
            double initialValue = Double.parseDouble(initialValueStr);
            double percentage = Double.parseDouble(percentageStr);

            // Cálculos
            double increment = (initialValue * 5) / 100;
            double decrement = (initialValue * 5) / 100;
            double difference = (initialValue * percentage) / 100;
            double finalValueIncrement = initialValue + (initialValue * percentage) / 100;
            double finalValueDecrement = initialValue - (initialValue * percentage) / 100;
            double result = initialValue + increment;
            double result1 = initialValue - decrement;

            // Mostrar resultados
            tvResult.setText(decimalFormat.format(result));
            tvResult1.setText(decimalFormat.format(result1));
            tvResult2.setText(decimalFormat.format(finalValueIncrement));
            tvResult3.setText(decimalFormat.format(finalValueDecrement));
            tvResult4.setText(decimal.format(difference));
        } else {
            // Limpiar resultados si no hay entrada
            tvResult.setText("");
            tvResult1.setText("");
            tvResult2.setText("");
            tvResult3.setText("");
            tvResult4.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            MyDialogFragment dialog = new MyDialogFragment();
            dialog.show(getSupportFragmentManager(), "about_dialog");
            return true;
        }

        if (id == R.id.action_police) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://sites.google.com/view/porcencalbeta/"));
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_app_money) {
            // Realiza alguna acción cuando se hace clic en el elemento de menú "Política de Privacidad"
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.magiccodeinc.porcencal"));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}