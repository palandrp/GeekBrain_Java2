package ru.kimdo.obstacles;

import ru.kimdo.animals.Animal;
import ru.kimdo.animals.Swimable;

/**
 * @author GeekBrains
 * @version 06.06.2017
 */
public class Water implements Obstacle {
    private int length;

    public Water(int length) {
        this.length = length;
    }

    @Override
    public boolean doIt(Animal animal) {
        if (animal instanceof Swimable)
            return ((Swimable) animal).swim(length);
        else
            return false;
    }
}
