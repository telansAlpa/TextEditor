import javafx.scene.text.Font;

class Changes{

	Changes(){
		//Загрузка шрифтов
        Font.loadFont(getClass().getResource("/TextStyle/UbuntuMono-Regular.ttf").toExternalForm(), 26);
        Font.loadFont(getClass().getResource("/TextStyle/UbuntuMono-Bold.ttf").toExternalForm(), 26);
        Font.loadFont(getClass().getResource("/TextStyle/JetBrainsMono-Regular.ttf").toExternalForm(), 22);
	}

    //Изменения внешнего вида строки
	void appChanges(int start, int end, String lineText, boolean codeFormat){
        //Код
        if(codeFormat == true){
            Test.writingText.setStyle(start , end, "-fx-fill: #7b788a;-fx-font-size: 26px; -fx-font-family: 'Ubuntu Mono';");
        }
        else if(Test.hereCode == true){
            Test.writingText.setStyle(start , end, "-fx-fill: #CDD0DD;-fx-font-size: 22px; -fx-font-family: 'JetBrains Mono';");
        }
        //Обычный текст
        else{
            char firstLetter = lineText.charAt(0);
            if(firstLetter == '#'){
                Test.writingText.setStyle(start , end, "-fx-fill: #CDD0DD;-fx-font-size: 26px; -fx-font-weight: bold;-fx-font-family: 'Ubuntu Mono'");
            }
            //нужно придумать оптемизацию
            else{
                Test.writingText.setStyle(start , end, "-fx-fill: #CDD0DD;-fx-font-size: 26px; -fx-font-family: 'Ubuntu Mono';");
            }

        }
    }
}