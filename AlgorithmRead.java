import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

class AlgorithmRead{
	static String[] format = {"java", "py"}; //for codeStart
    static List<String> nameFile = new ArrayList<>(); //for codeStart()
    static List<Integer> stepFile = new ArrayList<>(); //for codeStart(), controlSEcode()
    static List<Integer> locationFile = new ArrayList<>(); //for codeStart(), controlSEcode()
    static List<Integer> linelist = new ArrayList<>(List.of(0)); //for volumeLine()


    static boolean createLine = false;



	//Записывает начало каждой строки как индекс, (в linelist)
	void volumeLine(String text, int currentLine){
        int new_word = 0;
        int remove_up = 0;
        int n = 0; // /n
        //System.out.println(currentLine);


        //Если новая строка снизу
        for(int i = (linelist.size()-1); i < currentLine ; i++){
            //Если одна строка и переход с конца слово
            if((i + 1) == currentLine && text.charAt(text.length() - 1) == '\n'){
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


        /*//Если изменения строки с середины
        linesize = linelist.size();//Задаём новое значения, работа серединый
        for(int i = linelist.get(currentLine); i < text.length(); i++){
            if((currentLine +1)<linelist.size() && linelist.get(currentLine+1)-1 < i){
                new_word++;
                System.out.println("new_word: "+ new_word);
            }
            if(text.charAt(i) == '\n'){
                //System.out.println("laaaaend");
                if(new_word == 0){
                    System.out.println("Do add");
                    //System.out.println(n);
                    n++;
                    new_word++;
                    linelist.add(n, linelist.get(n-1) + new_word);

                    System.out.println("i: "+i);
                }
                //new_word = new_word - (linelist.get(currentLine + 1) - (linelist.get(currentLine)));
                while(n < linelist.size()-1){
                    System.out.println("while");
                    //System.out.println("ss");
                    n++;
                    System.out.println(i);
                    //System.out.println("linelist.get(n): " + linelist.get(n));
                    //System.out.println("new_word: " + new_word);
                    linelist.set(n, linelist.get(n) + new_word);
                }
                i = linelist.get(n);
                //i < text.length()-1  ен санғы '\n'- ді оқып жатқан жоқ
            }
        }*/

        for(int i = linelist.get(currentLine); i < text.length(); i++){

            if(text.charAt(i) == '\n'){

                n++;
                if(n+currentLine <= linelist.size() -1){
                    linelist.set(n+currentLine, i+1);
                }
                else{//Если новая строка в середине
                    linelist.add(i+1);

                    //Если было вставленно несколько строк
                    if(n+currentLine == linelist.size()+1){
                        linelist.remove(currentLine);//Удаляем эту строку
                    }
                    //System.out.println("add: " + i);
                    remove_up++;
                }
                //
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
        //Если пройсходит удаления строки
        for(int i = linelist.size(); i > n +currentLine +1; i--){
            linelist.remove(i-1);
        }
        System.out.println(linelist);
        //System.out.println(linelist);
        /*for(int i = 0; i < linelist.size()-1;i++){
            System.out.println(text.substring(linelist.get(i), linelist.get(i+1)));
        }*/
    }

	//Создает, удаляет: nameFile, stepFile, locationFile. Изменяет значения: nameFile.
    boolean codeStart(String text, int currentLine){
        if(Test.hereCode == true && locationFile.get(0)!=currentLine){
            return false;
        }


        /*for(int i = 0; i<locationFile.size(); i++){
            if(Test.writingText.getParagraphLength(locationFile.get(i))-1 != nameFile.get(i).length() && stepFile.get(i) == 0){
                nameFile.remove(i);
                stepFile.remove(i);
                locationFile.remove(i);
                break;
            }
        }*/
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

                    for(String file : this.nameFile){
                        file = file + ":";
                        if(file.equals(text)){
                            //Если это тот саммый файл который был создан 
                            if(currentLine == locationFile.get(0)){
                                return true;
                            }
                            return false;
                        }
                    }

                    if(locationFile.size() > 0 && locationFile.get(0) == currentLine){
                        nameFile.set(0, name+"."+word);
                    }
                    else{
                        nameFile.add(0, name+"."+word);
                        locationFile.add(0, currentLine);
                        stepFile.add(0, 0);
                    }
                    
                    return true;
                }
                break; // уже проверили подходящее расширение — выходим из цикла
            }
        }
        return false;

    }

    //Изменяет значения stepFile, locationFile(Если меняется локация или размер написоного кода), Test.hereCode делает true если мы в коде;
    void controlSEcode(int oldLine, int oldlinesize, int currentLine){
        //(Оптимизация)
        if(nameFile.isEmpty()){
            return;
        }
        if(this.createLine == true){
            this.createLine = false;
            //Test.writingText.setParagraphStyle(linesize-1, "-fx-background-color: #333640; -fx-padding: 1 10 1 10;");
            return;
        }

        int linesize = this.linelist.size();
        //Если пройсходит добавления новой строки, изменяем размер locationFile или stepFile
        if(oldlinesize != linesize){ //linesize если поменялся
            //настройка нахождения кода
            for(int i = 0; i < locationFile.size(); i++){
                //Если это (не код)
                if(locationFile.get(i) > oldLine){
                    locationFile.set(i, locationFile.get(i)+ (linesize - oldlinesize));
                }
                //Если это (имя кода)
                else if(locationFile.get(i) == oldLine){
                    if(oldlinesize > linesize || ((linelist.get(currentLine) - linelist.get(currentLine - 1)) == 1) && linesize - oldlinesize == 1){
                        locationFile.set(i, locationFile.get(i)+ (linesize - oldlinesize));
                    }
                    else{
                        stepFile.set(i, stepFile.get(i)+ (linesize - oldlinesize));
                    }
                }
                //Если это (код)
                else if(locationFile.get(i) >= (oldLine - stepFile.get(i))){
                    stepFile.set(i, stepFile.get(i)+ (linesize - oldlinesize));
                }
            }
            //Если начало кода
            if(currentLine == locationFile.get(0)+1 && linelist.get(currentLine) == linelist.get(currentLine-1) + nameFile.get(0).length()+2){
                Test.hereCode = true;
                Test.writingText.setParagraphStyle(currentLine, "-fx-background-color: #25272e; -fx-padding: 0 10 0 10;");
            }
            //Если удалили код
            else if(currentLine == locationFile.get(0)){
                Test.hereCode = false;
            }
        }

        //Если новая сторка не добавилось, но изменилось строка где мы печатаем
        if(oldlinesize == linesize || linesize<oldlinesize && oldLine - currentLine != 1){
            for(int i = 0; i < locationFile.size(); i++){
                //Если это код
                if(currentLine - stepFile.get(i) <= locationFile.get(i) && currentLine >= locationFile.get(i)){
                    if(stepFile.get(i)!=0 || currentLine == locationFile.get(i))
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