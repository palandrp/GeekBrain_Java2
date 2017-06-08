import animals.*;
import obstances.*;
/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main {

    public static void main(String[] args) {
        Animal[] zoo = {new Cat("Murzik"), new Hen("Izzy"), new Hippo("Hippopo")};
        Track track = new Track(80);
        Wall wall = new Wall(3);
        Water water = new Water(10);

        for (Animal animal : zoo) {
            System.out.println(animal + " say: " + animal.voice());
            System.out.println(" run: " + track.doIt(animal));
            System.out.println(" jump: " + wall.doIt(animal));
            System.out.println(" swim: " + water.doIt(animal));
        }
    }
}
package animals;
/**
 * Write a description of class Cat here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Cat extends Animal implements Jumpable, Swimable {
    private int swim_limit;
    private float jump_limit;

    public Cat(String name) {
        this.name = name;
        this.run_limit = 100;
        swim_limit = 100;
        jump_limit = 3.8f;
    }

    @Override
    public String voice() {
        return "meow";
    }

    @Override
    public boolean swim(int length) {
        return swim_limit >= length;
    }

    @Override
    public boolean jump(float height) {
        return jump_limit >= height;
    }
}
package animals;
/**
 * Write a description of class Hen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Hen extends Animal implements Jumpable {
    private float jump_limit;

    public Hen(String name) {
        this.name = name;
        this.run_limit = 100;
        jump_limit = 10f;
    }

    @Override
    public String voice() {
        return "ko-ko-ko";
    }

    @Override
    public boolean jump(float height) {
        return jump_limit >= height;
    }
}
package animals;
/**
 * Write a description of class Hippo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Hippo extends Animal implements Swimable {
    private int swim_limit;

    public Hippo(String name) {
        this.name = name;
        this.run_limit = 50;
        swim_limit = 200;
    }

    @Override
    public String voice() {
        return "uf-uf-uf";
    }

    @Override
    public boolean swim(int length) {
        return swim_limit >= length;
    }
}
package animals;
/**
 * Write a description of interface Jumpable here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Jumpable {
    boolean jump(float height);
}
package animals;
/**
 * Write a description of interface Swimable here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Swimable {
    boolean swim(int length);
}
package obstances;
        import animals.*;
/**
 * Write a description of class Track here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Track {
    private int length;

    public Track(int length) {
        this.length = length;
    }

    public boolean doIt(Animal animal) {
        return animal.run(length);
    }
}
package obstances;
        import animals.*;
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
package obstances;
        import animals.*;
/**
 * Write a description of class Water here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Water {
    private int length;

    public Water(int length) {
        this.length = length;
    }

    public boolean doIt(Animal animal) {
        if (animal instanceof Swimable)
            return ((Swimable) animal).swim(length);
        else
            return false;
    }
}
