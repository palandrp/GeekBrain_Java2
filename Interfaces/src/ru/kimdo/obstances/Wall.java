package ru.kimdo.obstances;

import ru.kimdo.animals.Animal;
import ru.kimdo.animals.Jumpable;

/**
 * Write a description of class Wall here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Wall {
    private float height;

    public Wall(float height) {
        this.height = height;
    }

    public boolean doIt(Animal animal) {
        if (animal instanceof Jumpable)
            return ((Jumpable) animal).jump(height);
        else
            return false;
    }
}
