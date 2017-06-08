package ru.kimdo;

import ru.kimdo.animals.*;
import ru.kimdo.obstacles.CopperPipe;
import ru.kimdo.obstacles.Fire;
import ru.kimdo.obstacles.Water;

/**
 * @author GeekBrains
 * @version 06.06.2017
 */
public class Main {

    public static void main(String[] args) {
        Animal[] zoo = {new Cat("Murzik"), new Hen("Izzy"), new Hippo("Hippopo")};
        CopperPipe copperPipe = new CopperPipe(80);
        Fire fire = new Fire(3);
        Water water = new Water(10);

        for (Animal animal : zoo) {
            System.out.println(animal + " say: " + animal.voice());
            System.out.println(" run: " + copperPipe.doIt(animal));
            System.out.println(" jump: " + fire.doIt(animal));
            System.out.println(" swim: " + water.doIt(animal));
        }
    }
}
