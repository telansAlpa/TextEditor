import java.util.ArrayList;
import java.util.List;

class AlgorithmRead{
	static String[] format = {"java", "py"}; //for code()


	//Разделяет весь написаный текст по строкам
	int[] volumeLine(String text, int currentLine){
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

	//Возварашяет true если написали например: (a.py)
    boolean code(String text){
        boolean match = false;
        for (String word : format) {
            String suffix = "." + word + ":";
            if (text.endsWith(suffix)) {
                int index = text.indexOf(suffix); 
                if (index <= 0) {
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






}