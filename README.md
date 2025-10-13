# Text_Editor
Проект созданный для удобного печатания текста для лекций

<Оссобенности:
Данный проект разрабатывается на языке программирования <JAVA> на версий 17.0.16 <-(Это важно) с использованием библеотеки <JavaFx>. Если для запуска использовать любое IDE то запуск будет пройсходит автоматический. Если вы используете для запуска обычный текстовый редактор и терменал(командную строку) для запуска нужна ввести данную команду:
   Для Mac/Linux:
   
	# Компиляция
	javac --module-path javafx-sdk-17.0.16/lib --add-modules javafx.controls -cp "libs/*" Test.java
	# Запуск
	java --module-path javafx-sdk-17.0.16/lib --add-modules javafx.controls -cp ".:libs/*" Test
   

   Для Windows:

	#Компиляция
	javac --module-path javafx-sdk-17.0.16\lib --add-modules javafx.controls -cp "libs/*" Test.java
    #Запуск
	java --module-path javafx-sdk-17.0.16\lib --add-modules javafx.controls -cp ".;libs/*" Test
