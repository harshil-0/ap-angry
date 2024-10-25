package io.github.runangrybird;

public abstract class Bird {
    private String type;
    private float velocity;
    private float position;
    private float angle;

    public Bird(String type, float velocity, float position, float angle) {
        this.type = type;
        this.velocity = velocity;
        this.position = position;
        this.angle = angle;
    }

    public void launch() {
        System.out.println(type + " bird launched at velocity " + velocity + " and angle " + angle);
    }

    public void adjustAngle(float angle) {
        this.angle = angle;
        System.out.println(type + " bird angle adjusted to " + angle);
    }

    public void adjustVelocity(float velocity) {
        this.velocity = velocity;
        System.out.println(type + " bird velocity adjusted to " + velocity);
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public abstract void specialAbility();
}
