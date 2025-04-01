from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.label import Label
from kivy.uix.textinput import TextInput
from kivy.uix.button import Button


class BMIApp(App):
    def build(self):
        self.layout = BoxLayout(orientation='vertical', padding=10, spacing=10)

        # Поле ввода массы
        self.weight_input = TextInput(hint_text="Введите массу (в кг)", multiline=False, input_filter='float')
        self.layout.add_widget(self.weight_input)

        # Поле ввода роста
        self.height_input = TextInput(hint_text="Введите рост (в м)", multiline=False, input_filter='float')
        self.layout.add_widget(self.height_input)

        # Кнопка расчета ИМТ
        self.calculate_button = Button(text="Расчет", on_press=self.calculate_bmi)
        self.layout.add_widget(self.calculate_button)

        # Метка для результата
        self.result_label = Label(text="Ваш ИМТ: ")
        self.layout.add_widget(self.result_label)

        return self.layout

    # Функция расчета ИМТ
    def calculate_bmi(self, instance):
        try:
            weight = float(self.weight_input.text)
            height = float(self.height_input.text)

            if height <= 0:
                self.result_label.text = "Ошибка! Рост введён некорректно. Повторите ввод."
                return

            bmi = weight / (height ** 2)

            if bmi <= 16:
                result_text = f"Ваш индекс массы тела: {bmi:.2f}\nВыраженный дефицит массы тела!"
            elif bmi < 18.5:
                result_text = f"Ваш индекс массы тела: {bmi:.2f}\nНедостаточная масса тела!"
            elif bmi <= 24.99:
                result_text = f"Ваш индекс массы тела: {bmi:.2f}\nНорма"
            elif bmi <= 30:
                result_text = f"Ваш индекс массы тела: {bmi:.2f}\nИзбыточная масса тела!"
            elif bmi <= 35:
                result_text = f"Ваш индекс массы тела: {bmi:.2f}\nОжирение первой степени!"
            elif bmi <= 40:
                result_text = f"Ваш индекс массы тела: {bmi:.2f}\nОжирение второй степени!"
            else:
                result_text = f"Ваш индекс массы тела: {bmi:.2f}\nОжирение третьей степени!"

            self.result_label.text = result_text  # Обновляем текст метки

        except ValueError:
            self.result_label.text = "Ошибка! Введите корректные числовые значения."


# Запуск приложения
if __name__ == "__main__":
    BMIApp().run()
