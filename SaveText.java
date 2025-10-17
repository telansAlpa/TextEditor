import java.io.FileWriter;
import java.io.IOException;

class SaveText{
    //Сохраняет все написаное как txt файл
    void saveToFile(String text) {
        try (FileWriter writer = new FileWriter("a.txt")) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}