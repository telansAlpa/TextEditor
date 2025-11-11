import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.input.KeyCode;

import java.util.Collections;

import java.io.FileWriter;
import java.io.IOException;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.InlineCssTextArea;

public class Test extends Application {
    static InlineCssTextArea writingText = new InlineCssTextArea();
    //Текущая линия
    private int oldLine = 0;
    private int oldLinesize = 0;
    //
    static boolean hereCode = false;
    @Override
    public void start(Stage stage) {
        //Добавляем классы
        AlgorithmRead check = new AlgorithmRead();
        SaveText save = new SaveText();
        Changes changes = new Changes();

        //Загрузка шрифтов
        Font.loadFont(getClass().getResource("/TextStyle/Helvetica-Regular.otf").toExternalForm(), 28);
        

        //Внешний вид текста и заднего фона
        writingText.setStyle("-fx-background-color: #333640; -fx-padding: 50 100 0 100; -fx-font-family: 'Helvetica Regular';");
        writingText.setStyle(0 , 0 , "-fx-fill: #CDD0DD;-fx-font-size: 28px;");
        writingText.setWrapText(true);

        //Обработка положения курсора
        writingText.caretPositionProperty().addListener((obs, oldVal, newVal) -> {
            check.controlSEcode(oldLine, oldLinesize, writingText.getCurrentParagraph());
            this.oldLinesize = check.linelist.size();
            this.oldLine = writingText.getCurrentParagraph();
        });

        //Обработка ввода букв
        writingText.textProperty().addListener((obs, oldText, newText) -> {
            save.saveToFile(newText);

            int currentLine = writingText.getCurrentParagraph();
            check.volumeLine(newText, currentLine);
            

            int start = check.linelist.get(currentLine);
            int end = (currentLine == (check.linelist.size()-1)) ? (newText.length()) : check.linelist.get(currentLine + 1) - 1;

            if(start != end){
                String lineText = newText.substring(start, end);
                //check.codeStart(lineText); Если мы хотим написать код (Создали файл) возврашаят True
                boolean c = check.codeStart(lineText, currentLine);
                changes.appChanges(start, end, lineText, c);
            }
        });


        /*writingText.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter");
            }
        });*/

        
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