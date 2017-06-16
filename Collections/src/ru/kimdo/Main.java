package ru.kimdo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * 1) Создать массив с набором слов (20-30 слов, должны встречаться повторяющиеся):
 * а. Посчитать сколько раз встречается каждое слово;
 * б. Найти список слов, из которых состоит текст (дубликаты не считать);
 * 2) Написать простой класс ТелефонныйСправочник:
 * а. В каждой записи всего три поля: Фамилия, Телефон, email;
 * б. Отдельный метод для поиска номера телефона по фамилии (ввели фамилию, получили 
 * телефон), и отдельный метод для поиска email по фамилии. Следует учесть, что под
 * одной фамилией может быть несколько записей.
 */

public class Main {

    public static void main(String[] args) {
        new arrayFromText("Java 2. Методичка #3.docx");
    }
}

class arrayFromText {
    List<String> textList;

    arrayFromText(String FILE_NAME){
        String line;
        String file = "";
        String[] textArray;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(FILE_NAME)))){
            while ((line = reader.readLine()) != null)
                file += line + "\n";
        } catch (Exception e){
            System.out.println(e);
        }
        if (file.length() > 0) {
            textArray = file.split(" |,|.|!|-|<|>|:|;|}|[|]");
            //textList = new LinkedList<String>(textArray);
            System.out.println(textArray.toString());
        }
    }
    
}
