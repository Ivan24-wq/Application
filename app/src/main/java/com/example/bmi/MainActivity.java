package com.example.bmi;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;
import android.view.MenuItem;
import android.view.Menu;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bmi.R;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.res.Configuration;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {


    private EditText weightInput;
    private EditText heightInput;
    private Button calculateButton;
    private TextView resultLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Применяем язык и тему перед созданием View
        applyLocale();

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        boolean darkMode = prefs.getBoolean("dark_mode", false);
        setTheme(darkMode ? R.style.AppTheme_Dark : R.style.AppTheme_White);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация UI
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        weightInput = findViewById(R.id.weightInput);
        heightInput = findViewById(R.id.heightInput);
        calculateButton = findViewById(R.id.calculateButton);
        resultLabel = findViewById(R.id.resultLabel);

        calculateButton.setOnClickListener(v -> calculateBMI());
    }

    // Меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            openSettings();
            return true;
        } else if (id == R.id.action_theme) {
            toggleTheme();
            return true;
        } else if (id == R.id.action_language_russian) {
            setLocale("ru");
            return true;
        } else if (id == R.id.action_language_english){
            setLocale("en");
            return true;
        } else if (id == R.id.action_language_german){
            setLocale("de");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setLocale(String languageCode) {
        // Сохраняем выбранный язык
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        prefs.edit().putString("language", languageCode).apply();

        // Применяем язык
        applyLocale();
    }

    private void applyLocale() {
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        String language = prefs.getString("language", "en"); // по умолчанию английский

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }

        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void openSettings() {
        Toast.makeText(this, "Открыть настроки", Toast.LENGTH_SHORT).show();
    }

    private void toggleTheme(){
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        boolean darkMode = prefs.getBoolean("dark_mode", false);
        AppCompatDelegate.setDefaultNightMode(darkMode ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);

        prefs.edit().putBoolean("dark_mode", !darkMode).apply();
        recreate();
    }

    private void calculateBMI() {
        try {
            String weightText = weightInput.getText().toString();
            String heightText = heightInput.getText().toString();

            if (weightText.isEmpty() || heightText.isEmpty()) {
                resultLabel.setText("Ошибка! Введите массу и рост.");
                return;
            }

            double weight = Double.parseDouble(weightText);
            double height = Double.parseDouble(heightText);

            if (height <= 0) {
                resultLabel.setText("Ошибка! Рост введён некорректно. Повторите ввод.");
                return;
            }

            double bmi = weight / (height * height);
            String resultText;

            if (bmi <= 16) {
                resultText = String.format("Ваш индекс массы тела: %.2f\nВыраженный дефицит массы тела!", bmi);
            } else if (bmi < 18.5) {
                resultText = String.format("Ваш индекс массы тела: %.2f\nНедостаточная масса тела!", bmi);
            } else if (bmi <= 24.99) {
                resultText = String.format("Ваш индекс массы тела: %.2f\nНорма", bmi);
            } else if (bmi <= 30) {
                resultText = String.format("Ваш индекс массы тела: %.2f\nИзбыточная масса тела!", bmi);
            } else if (bmi <= 35) {
                resultText = String.format("Ваш индекс массы тела: %.2f\nОжирение первой степени!", bmi);
            } else if (bmi <= 40) {
                resultText = String.format("Ваш индекс массы тела: %.2f\nОжирение второй степени!", bmi);
            } else {
                resultText = String.format("Ваш индекс массы тела: %.2f\nОжирение третьей степени!", bmi);
            }

            resultLabel.setText(resultText);

        } catch (NumberFormatException e) {
            resultLabel.setText("Ошибка! Введите корректные числовые значения.");
        }
    }
}