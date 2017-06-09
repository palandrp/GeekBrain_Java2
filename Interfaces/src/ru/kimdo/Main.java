package ru.kimdo;

import ru.kimdo.animals.*;
import ru.kimdo.obstacles.ObstacleCourse;

/**
 * @author Pavel Petrikovskiy
 * @version 09.06.2017
 */
public class Main {

    public static void main(String[] args) {
        ObstacleCourse obstacleCourse = new ObstacleCourse(80,10,3);

        Team team1 = new Team("Ракета",
                new Cat("Murzik"),
                new Hen("Izzy"),
                new Hippo("Hippopo"),
                new Cat("Zaza"));
        Team team2 = new Team("Крылатки",
                new Hippo("Соня"),
                new Hen("Ловкач"),
                new Hippo("Масол"),
                new Cat("Шкипер"));

        team1.printTeam();
        team2.printTeam();
        team1.doIt(obstacleCourse);
        team2.doIt(obstacleCourse);
        System.out.println();
        team1.printResults();
        team2.printResults();
        System.out.println();
        team1.printWinners();
        team2.printWinners();
    }
}
