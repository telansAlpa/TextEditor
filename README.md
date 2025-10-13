# Text_Editor
Проект, Text_Editor будет обеспечивать удобный процесс написания текста для учебных лекций в сфере программирования.

<Оссобенности:

Данный проект разрабатывается на языке программирования JAVA на версий 17.0.16 <-(Это важно) с использованием библеотеки JavaFx. Если для запуска использовать любое IDE то запуск будет пройсходит автоматический.


Если работаете в текстовом редакторе нужно установить и распоковать JavaFx на версий 17.0.16 рядом с главным файлом проекта Test.java. Для запуска через обычный терменал(командную строку) нужно ввести данную команду:


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
