package ru.kimdo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Pavel Petrikovskiy
 * @version 17.06.2017
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
        new ArrayFromText("Text.txt");
        new TelBook();
    }
}

class ArrayFromText {

    ArrayFromText(String FILE_NAME){
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
            file = file.toLowerCase();
            file = file.replaceAll("\\p{Cntrl}", " ");
            file = file.replaceAll("[,.»«—:]", "");
            file = file.replaceAll("  ", " ");
            file = file.trim();
            textArray = file.split("[ ]");
            countWords(textArray);
        }
    }
    private void countWords(String[] textArray){
        Set<String> set = new TreeSet<>();
        set.addAll(Arrays.asList(textArray));
        System.out.println("Список слов, из которых состоит текст:");
        System.out.println("(сразу с подсчетом вхождений слова)");
        for (String s: set) {
            int count = 0;
            for (String ss: textArray)
                if (s.equals(ss)) count++;
            System.out.printf("%s:%d; ",s,count);
        }
        System.out.println();
    }
}

class TelBook {
    private Map<String, String> fioTel = new TreeMap<>();
    private Map<String, String> fioMail = new TreeMap<>();

    //Конструктор нужен только для демонстрации работы
    TelBook(){
        setTelMail("Адросимов А", "79534323492", "bibl@comp.com");
        setTelMail("Адросимов Б", "79430854023", "qrwr@ya.ru");
        setTelMail("Пионерский", "79432543744", "fgdfg@mail.ru");
        setTelMail("Адросимова", "79729374875", "bfgge@list.ru");
        setTelMail("Голодец А", "79092389798", "dfad@bk.ru");
        //setTel("Пионерский", "79234234543");
        setTel("Рогожкин", "79234234543");
        setMail("Рогожкин", "rog@corp.ru");

        System.out.println(getTel("Пионерский"));
        System.out.println(getMail("Голодец А"));
        printTelMail("Рогожкин");
    }

    void setTel(String fio, String tel){
        Set<Map.Entry<String, String>> set = fioTel.entrySet();
        for (Map.Entry<String, String> s :
                set) {
            if (fio.equals(s.getKey())){
                System.out.println("ФИО уже присутствует.");
                return;
            }
        }
        fioTel.put(fio,tel);
    }
    void setMail(String fio, String mail){
        Set<Map.Entry<String, String>> set = fioMail.entrySet();
        for (Map.Entry<String, String > s :
                set) {
            if (fio.equals(s.getKey())){
                System.out.println("ФИО уже присутствует.");
                return;
            }
        }
        fioMail.put(fio,mail);
    }
    void setTelMail(String fio, String tel, String mail){
        Set<Map.Entry<String, String>> set0 = fioTel.entrySet();
        for (Map.Entry<String, String> s :
                set0) {
            if (fio.equals(s.getKey())){
                System.out.println("ФИО уже присутствует.");
                return;
            }
        }
        Set<Map.Entry<String, String>> set1 = fioMail.entrySet();
        for (Map.Entry<String, String > s :
                set1) {
            if (fio.equals(s.getKey())){
                System.out.println("ФИО уже присутствует.");
                return;
            }
        }
        fioTel.put(fio,tel);
        fioMail.put(fio,mail);
    }
    String  getTel(String fio){
        return fioTel.get(fio);
    }
    String getMail(String fio){
        return fioMail.get(fio);
    }
    void printTelMail(String fio){
        System.out.println(fioTel.get(fio));
        System.out.println(fioMail.get(fio));
    }
}