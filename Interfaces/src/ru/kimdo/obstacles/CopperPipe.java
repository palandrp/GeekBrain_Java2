package ru.kimdo.obstacles;

import ru.kimdo.animals.Animal;

/**
 * @author GeekBrains
 * @version 06.06.2017
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
