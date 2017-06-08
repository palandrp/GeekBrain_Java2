package ru.kimdo;

import ru.kimdo.animals.Animal;
import ru.kimdo.obstacles.Obstacle;
import ru.kimdo.obstacles.CopperPipe;
import ru.kimdo.obstacles.Fire;
import ru.kimdo.obstacles.Water;

/**
 * @author Pavel Petrikovskiy
 * @version 09.06.2017
 */
public class ObstacleCourse {
    private Obstacle[] obstacleCourse = new Obstacle[3];

    ObstacleCourse(int length1, int length2, int height){
        obstacleCourse[0] = new CopperPipe(length1);
        obstacleCourse[1] = new Water(length2);
        obstacleCourse[2] = new Fire(height);
    }

    boolean doIt(Animal animal){
        for (Obstacle o:
                obstacleCourse) {
            if (o.doIt(animal))
                ;
        }
    }
}
