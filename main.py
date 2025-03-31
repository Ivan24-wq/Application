from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.label import Label
from kivy.uix.textinput import TextInput
from kivy.uix.button import Button

class BMIApp(App):
     def build(self):
        self.layout = BoxLayout(orientation='vertical', padding=10, spacing=10)

        #Поле ввода массы
        self.weight_input = TextInput(hint_text = "Введите массу (в кг)", multiline=False, input_filter = 'float')
        self.layout.add_widget(self.weight_input)


        #Поле ввода роста
        self.height_input = TextInput(hint_text = "Введите рост (в м)", multiline=False, input_filter = 'float')
        self.layout.add_widget(self.height_input)

        #Кнопка посчета ИМТ
        self.calculate_btton = Button(text = "Расчет", on_press=self.calculate_bmi)
        self.layout.add_widget(self.calculate_btton)

        return self.layout
        
    
#Запуск
if __name__ == "__main__":
    BMIApp().run()