package ru.kimdo.obstacles;

import ru.kimdo.animals.Animal;

/**
 * @author Pavel Petrikovskiy
 * @version 09.06.2017
 */
public class ObstacleCourse {
    private Obstacle[] obstacleCourse = new Obstacle[3];

    public ObstacleCourse(int length1, int length2, int height){
        obstacleCourse[0] = new CopperPipe(length1);
        obstacleCourse[1] = new Water(length2);
        obstacleCourse[2] = new Fire(height);
    }

    public boolean doIt(Animal animal){
        for (Obstacle o:
                obstacleCourse) {
            if (!o.doIt(animal))
                return false;
        }
        return true;
    }
}
