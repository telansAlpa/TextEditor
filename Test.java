import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.text.Font;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;

  import java.util.function.IntFunction;

import java.util.Collections;
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.model.PlainTextChange;

import org.reactfx.EventStream;
import org.reactfx.EventStreams;

public class Test extends Application {
    //Создаем текстовое поле
    static InlineCssTextArea writingText = new InlineCssTextArea();

    //Для получения не обновленных данных(старых)
    private int oldLine = 0;
    private int oldLinesize = 0;
    
    //Переменная для определения это код или нет
    static boolean hereCode = false;
    @Override
    public void start(Stage stage) {
        //Добавляем классы
        AlgorithmRead check = new AlgorithmRead();
        TextareaFuncs function = new TextareaFuncs();
        SaveText save = new SaveText();
        Changes changes = new Changes();

        //Загрузка шрифтов
        Font.loadFont(getClass().getResource("/TextStyle/UbuntuMono-Regular.ttf").toExternalForm(), 24);

        

        //Внешний вид текста и заднего фона
        writingText.setStyle("-fx-background-color: #333640; -fx-fill: #CDD0DD; -fx-font-family: 'Ubuntu Mono'; -fx-font-size: 26px;");
        writingText.setParagraphStyle(0, "-fx-padding: 1 10 1 10;");
        writingText.setStyle(0, 0, "-fx-fill: #CDD0DD;");
        writingText.setWrapText(true);


        EventStream<?> textChanges = writingText.plainTextChanges();
        EventStream<Integer> caretMoves = EventStreams.valuesOf(writingText.caretPositionProperty());


        EventStreams.merge(caretMoves, textChanges)
        .subscribe(event -> {

            // 1 textChanges
            if (event instanceof PlainTextChange tc) {
                onTextChange(tc, check, save);
            }
            // 2 caretMoves
            if (event instanceof Integer caret || check.linelist.size() < this.oldLinesize) {
                onCaretMove(writingText.getCaretPosition(), check, function);
            }

            // 3 merge-логика (общее)
            onAnyChange(event, check, changes);

        });

        
        //Скролл для текстового поле
        VirtualizedScrollPane<InlineCssTextArea> write = new VirtualizedScrollPane<>(writingText);


        // Отслеживаем прокрутку VirtualizedScrollPane
        Region topSpacer = new Region();
        topSpacer.setMinHeight(30); // отступ сверху
        topSpacer.setMaxHeight(20);
        topSpacer.setStyle("-fx-background-color: #333640;");//#4e5c73

        //VBox чтобы использовать
        VBox root = new VBox(topSpacer, write);

        VBox.setVgrow(write, Priority.ALWAYS);

        //Загрузка сцены
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("textEditor");
        stage.show();
    }

    void onTextChange(PlainTextChange e, AlgorithmRead check, SaveText save){
        String text = writingText.getText();
        int currentLine = writingText.getCurrentParagraph();

        //Алгоритм сохранения начало каждой стоки, индекса текста
        check.volumeLine(text, currentLine);

        /*//Сохранения написоного
        if(hereCode == true){
            int startCode = check.linelist.get(check.locationFile.get(0)+1);
            int endCodeLine = check.locationFile.get(0) + check.stepFile.get(0);
            int endCode = check.linelist.get(endCodeLine) + writingText.getParagraphLength(endCodeLine);
            save.saveCode(text.substring(startCode, endCode), check.nameFile.get(0));
        }
        else{
            save.saveToFile(text);
        }*/
    }

    void onCaretMove(int e, AlgorithmRead check, TextareaFuncs function){
        int select = writingText.getSelection().getLength();
        int currentLine = writingText.getCurrentParagraph();

        //Алгоритм для определения пространство кода
        check.controlSEcode(oldLine, oldLinesize, currentLine);
        
        //Locationfile bag 
        //Поток нужно настройить это должно выпонятся пойже


        //Функций для написаного кода
        writingText.setParagraphGraphicFactory(function::createGutter);
        Platform.runLater(() -> {
            function.renameFunction(select, e);
            function.newlineInCode(this.oldLinesize);
        });

        //Старые данные
        this.oldLine = writingText.getCurrentParagraph();
        this.oldLinesize = check.linelist.size(); 
    }

    void onAnyChange(Object e, AlgorithmRead check, Changes changes){
        String text = writingText.getText();
        int currentLine = writingText.getCurrentParagraph();


        int start = check.linelist.get(currentLine);
        int end = check.linelist.get(currentLine)+ writingText.getParagraphLength(currentLine);

        if(start != end){
            String lineText = text.substring(start, end);
            //check.codeStart(lineText); Если мы хотим написать код (Создали файл) возврашаят True
            boolean c = check.codeStart(lineText, currentLine);
            changes.appChanges(start, end, lineText, c);
        }
        //#-определяем и пишем при изменений строки обновляем
    }





    public static void main(String[] args) {
        launch(args);
    }
}