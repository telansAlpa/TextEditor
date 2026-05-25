import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.animation.PauseTransition;
import javafx.util.Duration;


class TextareaFuncs{
	//Добавляем классы
	private AlgorithmRead check = new AlgorithmRead();

    

	Node createGutter(int lineIndex){
        String label_text = "|";
        String label_colorBack = "#333640;";

        if(check.locationFile.size() > 0){
            for(int i = 0; i<check.locationFile.size(); i++){
                if(lineIndex == check.locationFile.get(i)){
                    label_text = " ";
                }
                else if(lineIndex > check.locationFile.get(i) && lineIndex <= check.locationFile.get(i)+ check.stepFile.get(i)){
                    label_text = String.format("%3d |", lineIndex - check.locationFile.get(i));
                    label_colorBack = "#25272e;";
                    break;
                }
            }
        }
        Label lineNumber = new Label(label_text);
        lineNumber.setMinWidth(50);
        lineNumber.setAlignment(Pos.CENTER_RIGHT);
        lineNumber.setStyle("-fx-text-fill: #7b788a; -fx-font-size: 18px;");
        
        HBox lineGutter = new HBox(lineNumber);
        lineGutter.setAlignment(Pos.CENTER_LEFT);
        lineGutter.setStyle("-fx-background-color: " + label_colorBack);
        return lineGutter;
    }
    
    void newlineInCode(int oldLinesize){
        if(oldLinesize != check.linelist.size()){
            return;
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
            pause.setOnFinished(a -> {
                if(check.stepFile.size() > 0 && check.stepFile.get(0)>0 && check.stepFile.get(0)+ check.locationFile.get(0)+1 == check.linelist.size()){
                    check.createLine = true;
                    Test.hereCode = false;
                    Test.writingText.appendText("\n");
                    Test.writingText.moveTo(check.linelist.get(check.linelist.size()-1)-1);
                    System.out.println(check.stepFile.get(0));
                }
            });
            pause.play();
        //if(check.locationFile.get(0) == currentLine && )       
    }
    void renameFunction(int select, int e){
        //Баг при быстром удолений идет вперед
        if(select > 0 || check.stepFile.size() == 0){
            return;
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
        pause.setOnFinished(a -> {
            if(check.stepFile.get(0)>0 && check.linelist.get(check.locationFile.get(0)+1)-5 < e && check.linelist.get(check.locationFile.get(0)+1)>e){
                Test.writingText.moveTo(check.linelist.get(check.locationFile.get(0)+1)-5);
            }
        });
        pause.play();
        
    }
    
}