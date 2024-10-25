package io.github.runangrybird;

public abstract class Pig {
    private String size;
    private float position;
    private int health;

    public Pig(String size, float position, int health) {
        this.size = size;
        this.position = position;
        this.health = health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            System.out.println(size + " pig is destroyed!");
        } else {
            System.out.println(size + " pig took " + damage + " damage, remaining health: " + health);
        }
    }

    public void move(float newPosition) {
        position = newPosition;
        System.out.println(size + " pig moved to position " + position);
    }

    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
