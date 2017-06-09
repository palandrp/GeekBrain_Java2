package ru.kimdo.obstacles;

import ru.kimdo.animals.Animal;

/**
 * @author Pavel Petrikovskiy
 * @version 09.06.2017
 */
public class CopperPipe implements Obstacle {
    private int length;

    public CopperPipe(int length) {
        this.length = length;
    }

    @Override
    public boolean doIt(Animal animal) {
        return animal.run(length);
    }
}
