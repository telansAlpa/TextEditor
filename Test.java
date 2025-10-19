import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;


import java.util.Collections;

import java.io.FileWriter;
import java.io.IOException;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.InlineCssTextArea;

public class Test extends Application {
    static InlineCssTextArea writingText = new InlineCssTextArea();
    @Override
    public void start(Stage stage) {
        //Добавляем классы
        AlgorithmRead check = new AlgorithmRead();
        SaveText save = new SaveText();
        Changes changes = new Changes();

        //Загрузка шрифтов
        Font.loadFont(getClass().getResource("/TextStyle/Montserrat-Regular.ttf").toExternalForm(), 26);
        

        //Внешний вид текста и заднего фона
        writingText.setStyle("-fx-background-color: #333640; -fx-padding: 50 100 0 100; -fx-font-family: 'Montserrat Regular';");
        writingText.setStyle(0 , 0 , "-fx-fill: #CDD0DD;-fx-font-size: 26px;");
        writingText.setWrapText(true);

        //Обработка ввода букв
        writingText.textProperty().addListener((obs, oldText, newText) -> {
            save.saveToFile(newText);

            

            int currentLine = writingText.getCurrentParagraph();
            check.volumeLine(newText, currentLine);
            int start = check.linelist.get(currentLine);
            int end = (currentLine == (check.linelist.size()-1)) ? (newText.length()) : check.linelist.get(currentLine + 1) - 1;

            if(start != end){
                String lineText = newText.substring(start, end);
                boolean thiscode = check.code(lineText);//Если мы хотим написать код возврашаят True
                //changes.appChanges(start, end, lineText, thiscode);
            }
    

        });
        
        //Настройки сцены
        VirtualizedScrollPane<InlineCssTextArea> write = new VirtualizedScrollPane<>(writingText);
        Scene scene = new Scene(write);

        //Загрузка сцены
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Пример RichTextFX");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}