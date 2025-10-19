import java.util.ArrayList;
import java.util.List;

class AlgorithmRead{
	static String[] format = {"java", "py"}; //for code()
    static List<Integer> linelist = new ArrayList<>(List.of(0)); //for volumeLine

	//Разделяет весь написаный текст по строкам (в linelist)
	void volumeLine(String text, int currentLine){
        int n = 0;
        System.out.println(currentLine);

        //Если новая строка снизу
        for(int i = (linelist.size()-1); i < currentLine; i++){
            linelist.add(text.length());
        }
        //Если изменения строки с середины
        for(int i = linelist.get((currentLine)); i < text.length()-1; i++)// text.langth - 1, для выравнивания с индексом
        {
            System.out.println("Start: " + i);
            if(text.charAt(i) == '\n'){

                n++;
                if(n+currentLine <= linelist.size() -1){
                    linelist.set(n+currentLine, i+1);
                }
                else{//Если новая строка в середине
                    linelist.add(i+1);
                    System.out.println("add");
                }
                //
            }
        }
        //Если удаления строки
        for(int i = linelist.size(); i > currentLine + n +1; i--){
            linelist.remove(i-1);
        }
        System.out.println(linelist);

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