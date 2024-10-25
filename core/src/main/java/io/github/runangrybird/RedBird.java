package io.github.runangrybird;


public class RedBird extends Bird {
    public RedBird(float velocity, float position, float angle) {
        super("Red", velocity, position, angle);
    }

    @Override
    public void specialAbility() {
        System.out.println("Red bird uses speed boost!");
    }
}
