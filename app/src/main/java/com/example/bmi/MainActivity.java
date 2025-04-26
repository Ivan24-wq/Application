package com.example.bmi;

import com.example.bmi.R;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bmi.R;

public class MainActivity extends AppCompatActivity {

    private EditText weightInput;
    private EditText heightInput;
    private Button calculateButton;
    private TextView resultLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Подключим Toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Инициализация элементов интерфейса
        weightInput = findViewById(R.id.weightInput);
        heightInput = findViewById(R.id.heightInput);
        calculateButton = findViewById(R.id.calculateButton);
        resultLabel = findViewById(R.id.resultLabel);

        // Установка слушателя кнопки расчета
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
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