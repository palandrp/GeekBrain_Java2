package ru.kimdo.obstacles;

import ru.kimdo.animals.Animal;
import ru.kimdo.animals.Jumpable;

/**
 * @author Pavel Petrikovskiy
 * @version 09.06.2017
 */
public class Fire implements Obstacle {
    private float height;

    Fire(float height) {
        this.height = height;
    }

    @Override
    public boolean doIt(Animal animal) {
        if (animal instanceof Jumpable)
            return ((Jumpable) animal).jump(height);
        else
            return false;
    }
}
