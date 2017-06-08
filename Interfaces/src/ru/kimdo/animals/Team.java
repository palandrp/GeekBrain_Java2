package ru.kimdo.animals;

import ru.kimdo.animals.Animal;
import ru.kimdo.obstacles.ObstacleCourse;

/**
 * @author Pavel Petrikovskiy
 * @version 09.06.2017
 */
public class Team {
    private String name;
    private Animal[] team = new Animal[4];
    private String[] results = new String[4];
    private boolean[] resultsB = new boolean[4];

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
        int i = 0;
        System.out.printf("Команда %s, дистанцию прошли:\n",name);
        for (boolean B:
             resultsB) {
            if (B)
                System.out.printf("%s\n", results[i]);
            i++;
        }
    }
    public void printResults(){
        System.out.printf("Команда %s, результаты дистанции:\n",name);
        for (String r :
                results) {
            System.out.printf("%s\n", r);
        }
    }
    public void doIt(ObstacleCourse obstacle){
        int i = 0;
        for (Animal a: team) {
            if (obstacle.doIt(a))
                setResults(a.toString(),"ok",true,i);
            else
                setResults(a.toString(),"no",false,i);
            i++;
        }
    }
    public void printTeam(){
        System.out.println(name);
        for (Animal a: team) {
            System.out.println(a.toString());
        } 
    }
    private void setResults(String name, String result,boolean B,int i){
        this.results[i] = name + " " + result;
        this.resultsB[i] = B;
    }
}
