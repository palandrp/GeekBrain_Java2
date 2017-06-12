package ru.kimdo;

/**
 * @author Pavel Petrikovskiy
 * @version 12.06.2017
 */

public class Main {
    public static void main(String[] args) {
        new Java2homework2("1 3 1 2\n2 3 2 2\n5 6 7 1\n3 3 1 0");
    }
}

class Java2homework2 {
    private int[][] intArray = new int[4][4];

    Java2homework2(String strToSplit){
        String[][] strArray = new String[4][4];
        String[] zeroArray = strToSplit.split(" |\n");
        int count = 0;
        try {
            for (int i = 0; i < zeroArray.length / 4; i++)
                for (int j = 0; j < zeroArray.length / 4; j++) {
                    strArray[i][j] = zeroArray[count];
                    count++;
                }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Выход за пределы массива!");
            System.out.println("Обработано исключение.");
            System.out.println("Результирующий массив:");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    strArray[i][j] = zeroArray[count];
                    count++;
                    System.out.printf("%s ",strArray[i][j]);
                }
                System.out.println();
            }
        }
        System.out.println("Половина суммы элементов:");
        System.out.println(task2(strArray));
    }
    private int task2(String[][] strArray){
        int sum = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                try {
                    intArray[i][j] = Integer.parseInt(strArray[i][j]);
                }
                catch (NumberFormatException e){
                    System.out.println("В массиве найдено не числовое значение!");
                    System.out.println("Обработано исключение:");
                    System.out.printf("Элемент [%s] приравнян к нулю.\n", strArray[i][j]);
                    intArray[i][j] = 0;
                }
                sum += intArray[i][j];
            }
        return sum/2;
    }
}
