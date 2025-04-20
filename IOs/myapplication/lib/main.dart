import 'package:flutter/material.dart';

void main() {
  runApp(BMIApp());
}

class BMIApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'BMI Calculator',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: BMICalculator(),
    );
  }
}

class BMICalculator extends StatefulWidget {
  @override
  _BMICalculatorState createState() => _BMICalculatorState();
}

class _BMICalculatorState extends State<BMICalculator> {
  final TextEditingController _weightController = TextEditingController();
  final TextEditingController _heightController = TextEditingController();
  String _resultText = '';

  @override
  void dispose() {
    _weightController.dispose();
    _heightController.dispose();
    super.dispose();
  }

  void _calculateBMI() {
    try {
      String weightText = _weightController.text;
      String heightText = _heightController.text;

      if (weightText.isEmpty || heightText.isEmpty) {
        setState(() {
          _resultText = 'Ошибка! Введите массу и рост.';
        });
        return;
      }

      double weight = double.parse(weightText);
      double height = double.parse(heightText);

      if (height <= 0) {
        setState(() {
          _resultText = 'Ошибка! Рост введён некорректно. Повторите ввод.';
        });
        return;
      }

      double bmi = weight / (height * height);
      String resultText;

      if (bmi <= 16) {
        resultText = 'Ваш индекс массы тела: ${bmi.toStringAsFixed(2)}\nВыраженный дефицит массы тела!';
      } else if (bmi < 18.5) {
        resultText = 'Ваш индекс массы тела: ${bmi.toStringAsFixed(2)}\nНедостаточная масса тела!';
      } else if (bmi <= 24.99) {
        resultText = 'Ваш индекс массы тела: ${bmi.toStringAsFixed(2)}\nНорма';
      } else if (bmi <= 30) {
        resultText = 'Ваш индекс массы тела: ${bmi.toStringAsFixed(2)}\nИзбыточная масса тела!';
      } else if (bmi <= 35) {
        resultText = 'Ваш индекс массы тела: ${bmi.toStringAsFixed(2)}\nОжирение первой степени!';
      } else if (bmi <= 40) {
        resultText = 'Ваш индекс массы тела: ${bmi.toStringAsFixed(2)}\nОжирение второй степени!';
      } else {
        resultText = 'Ваш индекс массы тела: ${bmi.toStringAsFixed(2)}\nОжирение третьей степени!';
      }

      setState(() {
        _resultText = resultText;
      });

    } catch (e) {
      setState(() {
        _resultText = 'Ошибка! Введите корректные числовые значения.';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Калькулятор ИМТ'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _weightController,
              keyboardType: TextInputType.number,
              decoration: InputDecoration(
                labelText: 'Вес (кг)',
                border: OutlineInputBorder(),
              ),
            ),
            SizedBox(height: 16),
            TextField(
              controller: _heightController,
              keyboardType: TextInputType.number,
              decoration: InputDecoration(
                labelText: 'Рост (м)',
                border: OutlineInputBorder(),
              ),
            ),
            SizedBox(height: 24),
            ElevatedButton(
              onPressed: _calculateBMI,
              child: Text('Рассчитать ИМТ'),
            ),
            SizedBox(height: 24),
            Text(
              _resultText,
              style: TextStyle(fontSize: 18),
              textAlign: TextAlign.center,
            ),
          ],
        ),
      ),
    );
  }
}