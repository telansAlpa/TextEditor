import java.util.ArrayList;
import java.util.List;

class AlgorithmRead{
	static String[] format = {"java", "py"}; //for code()
    static List<Integer> linelist = new ArrayList<>(List.of(0)); //for volumeLine

	//Записывает начало каждой строки как индекс, (в linelist)
	void volumeLine(String text, int currentLine){
        int n = 0; // /n
        int remove_up = 0; //Переменная в которой будем хронить сколько шагов оталкиватся
        System.out.println(currentLine);

        //Если новая строка снизу
        int linesize = linelist.size();//Сохраняем чтобы не изменялась при add
        for(int i = (linesize-1); i < currentLine ; i++){

            //Если было вставленно несколько строк
            if(linesize<currentLine){
                //Упорядочивания вставленных строк
                for(int j = linelist.get(i);j < text.length()-1; j++){
                    if(text.charAt(j) == '\n'){
                        linelist.add(j+1);
                        break;
                    }
                }
            }
            //Если одна строка
            else{
                linelist.add(text.length());
            }
            System.out.println("add..");
        }


        //Если изменения строки с середины (более оптемезированое)
        /*linesize = linelist.size();//Задаём новое значения, работа серединый
        if(linesize > 2 && currentLine != linesize - 1){
            for(int i = linelist.get(currentLine-1); i < linelist.get(currentLine+1); i++){
                System.out.println(i);
            }
        }*/
        //Если изменения строки с середины
        linesize = linelist.size();//Задаём новое значения, работа серединый
        for(int i = linelist.get(currentLine); i < text.length()-1; i++){

            if(text.charAt(i) == '\n'){

                n++;
                if(n+currentLine <= linesize -1){
                    linelist.set(n+currentLine, i+1);
                }
                else{//Если новая строка в середине
                    linelist.add(i+1);

                    //Если было вставленно несколько строк
                    if(n+currentLine == linesize+1){
                        linelist.remove(currentLine);//Удоляем эту строку
                    }
                    System.out.println("add: " + i);
                    remove_up++;
                }
                //
            }
        }
        System.out.println(linelist);
        //Если вставленно несколько строк в середине
        int nabarot = remove_up;
        for(int i = linelist.get( currentLine); i > linelist.get(currentLine - remove_up); i--){
            System.out.println("End: " + i);
            if(text.charAt(i-1) == '\n'){

                linelist.set(currentLine - (remove_up - nabarot), i); //ошибка здесь мы идем набарот, не варю
                nabarot--;
            }
        }
        System.out.println(linelist);
        //Если пройсходит удаления строки
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