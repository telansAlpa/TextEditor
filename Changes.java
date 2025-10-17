import javafx.scene.text.Font;

class Changes{
    static char firstLetter;// for appChanges

	Changes(){
		//Загрузка шрифтов
        Font.loadFont(getClass().getResource("/TextStyle/Montserrat-Regular.ttf").toExternalForm(), 26);
        Font.loadFont(getClass().getResource("/TextStyle/Montserrat-SemiBold.ttf").toExternalForm(), 26);
	}

    //Изменения внешнего вида (строки) - в обычном текстеs
	void appChanges(int start, int end, String lineText, boolean stopFunction){
        char firstLetter = lineText.charAt(0);
        System.out.println("in change " + start +" " + end);
        System.out.println(lineText);
        if(firstLetter == '#'){
            Test.writingText.setStyle(start , end, "-fx-fill: #CDD0DD;-fx-font-size: 26px;-fx-font-family: 'Montserrat SemiBold'");
        }
        else if(stopFunction){
            Test.writingText.setStyle(start , end, "-fx-fill: #7b788a;-fx-font-size: 26px; -fx-font-family: 'Montserrat Regular';");
        }
        else if(this.firstLetter != firstLetter || !stopFunction){
            Test.writingText.setStyle(start , end, "-fx-fill: #CDD0DD;-fx-font-size: 26px; -fx-font-family: 'Montserrat Regular';");
        }
        this.firstLetter = firstLetter;

    }

}