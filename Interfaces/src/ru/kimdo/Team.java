package ru.kimdo;

import ru.kimdo.animals.Animal;
import ru.kimdo.obstacles.Obstacle;

/**
 * @author Pavel Petrikovskiy
 * @version 09.06.2017
 */
public class Team {
    private String name;
    private Animal[] team = new Animal[4];
    private String[][] results = new String[4][3];

    public Team(String name,
         Animal animal1,
         Animal animal2,
         Animal animal3,
         Animal animal4){

        this.name = name;
        this.team[0] = animal1;
        this.team[1] = animal2;
        this.team[2] = animal3;
        this.team[3] = animal4;
    }

    public void printWinners(){
        System.out.printf("Команда %s, дистанцию прошли:\n",name);

    }
    public boolean teamDoIt(Obstacle obstacle){
        for (Animal a: team) {
            obstacle.doIt(a);
        }
        return false; 
    }
    public void printTeam(){
        for (Animal a: team) {
            a.toString();
        } 
    }
    void setResults(Animal animal, boolean result){
        this.results[]
    }
}
