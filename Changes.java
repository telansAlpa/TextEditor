import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

class AlgorithmRead{
	static String[] format = {"java", "py"}; //for code()
    static List<String> nameFile = new ArrayList<>(); //for code()
    static List<Integer> stepFile = new ArrayList<>(); //for code()
    static List<Integer> locationFile = new ArrayList<>(); //for code()
    static List<Integer> linelist = new ArrayList<>(List.of(0)); //for volumeLine



	//Записывает начало каждой строки как индекс, (в linelist)
	void volumeLine(String text, int currentLine){
        int n = 0; // /n
        int remove_up = 0; //Переменная в которой будем хронить сколько шагов оталкиватся
        //System.out.println(currentLine);

        int linesize = linelist.size();//Сохраняем чтобы не изменялась при add
        //Если новая строка снизу
        for(int i = (linesize-1); i < currentLine ; i++){
            //Если одна строка и переход с конца слово
            if(linesize == currentLine && text.charAt(text.length() - 1) == '\n'){
                linelist.add(text.length());
                break;
            }
            //Если несколько строк или переход с середины слово
            for(int j = linelist.get(i);j < text.length(); j++){
                if(text.charAt(j) == '\n'){
                    linelist.add(j+1);
                    break;
                }
            }
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
        for(int i = linelist.get(currentLine); i < text.length(); i++){

            if(text.charAt(i) == '\n'){

                n++;
                if(n+currentLine <= linesize -1){
                    linelist.set(n+currentLine, i+1);
                }
                else{//Если новая строка в середине
                    linelist.add(i+1);

                    //Если было вставленно несколько строк
                    if(n+currentLine == linesize+1){
                        linelist.remove(currentLine);//Удаляем эту строку
                    }
                    //System.out.println("add: " + i);
                    remove_up++;
                }
                //i < text.length()-1  ен санғы '\n'- ді оқып жатқан жоқ
            }
        }

        //
        //System.out.println(linelist);
        //Если вставленно несколько строк в середине
        int nabarot = remove_up;
        for(int i = linelist.get(currentLine); i > linelist.get(currentLine - remove_up); i--){
            if(text.charAt(i-1) == '\n'){

                linelist.set(currentLine - (remove_up - nabarot), i); //ошибка здесь мы идем набарот, не варю
                nabarot--;
            }
        }
        //System.out.println(linelist);
        //Если пройсходит удаления строки
        for(int i = linelist.size(); i > currentLine + n +1; i--){
            linelist.remove(i-1);
        }
        System.out.println(linelist);
    }
 
	//Возварашяет true если написали например: (a.py)
    boolean codeStart(String text, int currentLine){
        if(Test.hereCode == true && locationFile.get(0)!=currentLine){
            return false;
        }
        for (String word : this.format) {
            String suffix = "." + word + ":";
            if (text.endsWith(suffix)) {
                int index = text.indexOf(suffix);
                if (index <= 0) {
                    break;
                }

                String name = text.substring(0, index);

                //проверка: все ли символы в name — буквы
                boolean onlyLetters = true;
                for (int i = 0; i < name.length(); i++) {
                    char c = name.charAt(i);
                    if (!Character.isLetter(c)) { // проверяет латиницу и кириллицу
                        onlyLetters = false;
                        break;
                    }
                }

                if (onlyLetters) {
                    //Проверяем не был ли этот файл уже создан
                    for(String file : this.nameFile){
                        if(file.equals(text)){
                            //Если это тот саммый файл который был создан 
                            if(currentLine == locationFile.get(0)){
                                return true;
                            }
                            return false;
                        }
                    }
                    nameFile.add(0, text);
                    locationFile.add(0, currentLine);
                    stepFile.add(0, 0);
                    return true;
                }
                break; // уже проверили подходящее расширение — выходим из цикла
            }
        }
        /*if(currentLine == locationFile.get(0) && !text.equals(nameFile.get(0))){
            Test.hereCode = false;                    
            nameFile.remove(0);
            stepFile.remove(0);
            locationFile.remove(0);
        }*/
        return false;

    }
    //Контролирует нахождения кода по позиций
    void controlSEcode(int oldLine, int oldlinesize, int currentLine){
        int linesize = this.linelist.size();
        //(Оптимизация)
        if(currentLine - oldLine == 0 || nameFile.isEmpty()){
            return;
        }


        //Если пройсходит добавления новой строки, изменяем размер кода или размер текста
        if(oldlinesize != linesize){ //linesize если поменялся
            //Если начало кода
            if(currentLine == locationFile.get(0)+1){
                Test.hereCode = true;
            }
            //Если удалили код
            else if(currentLine == locationFile.get(0)){
                Test.hereCode = false;
            }


            //настройка нахождения кода
            for(int i = 0; i < locationFile.size(); i++){
                //Если это не код
                if(locationFile.get(i) > oldLine){
                    locationFile.set(i, locationFile.get(i)+ (linesize - oldlinesize));
                }
                //Если это имя кода
                else if(locationFile.get(i) == oldLine){
                    if(oldlinesize > linesize || (linelist.get(currentLine) - linelist.get(currentLine - 1)) == 1){
                        locationFile.set(i, locationFile.get(i)+ (linesize - oldlinesize));
                    }
                    else{
                        stepFile.set(i, stepFile.get(i)+ (linesize - oldlinesize));
                    }

                    
                }
                //Если это код
                else if(locationFile.get(i) >= (oldLine - stepFile.get(i))){
                    stepFile.set(i, stepFile.get(i)+ (linesize - oldlinesize));
                    System.out.println(oldLine);
                    System.out.println(currentLine);

                }
            }
        }

        //Если новая сторка не добавилось, но изменилось строка где мы печатаем
        if(oldlinesize == linesize || linesize<oldlinesize && oldLine - currentLine != 1){
            for(int i = 0; i < locationFile.size(); i++){
                //Если это код
                if(stepFile.get(i)!=0 && currentLine - stepFile.get(i) <= locationFile.get(i) && currentLine > locationFile.get(i)){
                    Test.hereCode = true;
                    //Тот код в котором мы печатаем ставим как первую!
                    if(nameFile.size()>1){
                        String swapName = nameFile.remove(i);
                        Integer swapStep = stepFile.remove(i);
                        Integer swapLocation = locationFile.remove(i);
                        nameFile.add(0, swapName);
                        stepFile.add(0, swapStep);
                        locationFile.add(0, swapLocation);
                    }
                    return;
                }
                //Если это не код
                else{
                    Test.hereCode = false;
                }
            }
        }
    }






}