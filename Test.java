import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
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
    private static InlineCssTextArea writingText = new InlineCssTextArea();
    @Override
    public void start(Stage stage) {
        //Загрузка шрифтов
        Font.loadFont(getClass().getResource("/TextStyle/Montserrat-Regular.ttf").toExternalForm(), 26);
        Font.loadFont(getClass().getResource("/TextStyle/Montserrat-SemiBold.ttf").toExternalForm(), 26);
        

        //Внешний вид текста и заднего фона
        writingText.setStyle("-fx-background-color: #333640; -fx-padding: 50 100 0 100; -fx-font-family: 'Montserrat Regular';");
        writingText.setStyle(0 , 0 , "-fx-fill: #CDD0DD;-fx-font-size: 26px;");
        writingText.setWrapText(true);



        //Обработка ввода букв
        writingText.textProperty().addListener((obs, oldText, newText) -> {
            saveToFile(newText);

            

            int currentLine = writingText.getCurrentParagraph();
            int[] startEndLine = volumeLine(newText, currentLine);


            if(startEndLine[0] != startEndLine[1]){
                String lineText = newText.substring(startEndLine[0], startEndLine[1]);
                boolean thiscode = code(lineText);//Если мы хотим написать код возврашаят True
                System.out.println(thiscode);
                appChanges(startEndLine[0], startEndLine[1], lineText);
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
    static String[] format = {"java", "py"};
    private boolean code(String text){
        boolean match = false;
        for (String word : format) {
            String suffix = "." + word + ":";
            if (text.endsWith(suffix)) {
                int index = text.indexOf(suffix); // позиция точки перед расширением
                if (index <= 0) { // имя пустое или точка на нулевой позиции -> неверно
                    break;
                }

                String name = text.substring(0, index);

                // inline-проверка: все ли символы в name — буквы
                boolean onlyLetters = true;
                for (int i = 0; i < name.length(); i++) {
                    char c = name.charAt(i);
                    if (!Character.isLetter(c)) { // проверяет латиницу и кириллицу
                        onlyLetters = false;
                        break;
                    }
                }

                if (onlyLetters) {
                    match = true;
                }
                break; // уже проверили подходящее расширение — выходим из цикла
            }
        }
        return match;

    }

    private void saveToFile(String text) {
        try (FileWriter writer = new FileWriter("a.txt")) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static char firstLetter;
    private void appChanges(int start, int end, String lineText){
        char firstLetter = lineText.charAt(0);
        if(firstLetter == '#'){
            writingText.setStyle(start , end, "-fx-fill: #CDD0DD;-fx-font-size: 26px;-fx-font-family: 'Montserrat SemiBold'");
        }
        else if(this.firstLetter != firstLetter){
            writingText.setStyle(start , end, "-fx-fill: #CDD0DD;-fx-font-size: 26px; -fx-font-family: 'Montserrat Regular';");
        }
        this.firstLetter = firstLetter;

    }




    private int[] volumeLine(String text, int currentLine){
        int[] volume = new int[2];
        System.out.println(currentLine);

        for(int i = 0; i < text.length(); i++)
        {
            //Доходим до нужной строки
            if(currentLine > 0 && text.charAt(i) == '\n'){
                currentLine--;
            }
            //Дошли до нужной строки
            else{
                if(text.charAt(i) == '\n'){ //Если начелась новая строка завершаем
                    break;
                }
                else if(currentLine == 0){ //Начало нужной строки
                    volume[0] = i;
                    volume[1] = i+1;
                    currentLine--;
                }
                else if(currentLine == -1){ //Продолжения нужной строки
                    volume[1] = i+1;
                }
            }
        }
        System.out.println("V0: " + volume[0] +";V1: " + volume[1]);
        return volume;
    }

    public static void main(String[] args) {
        launch(args);
    }
}