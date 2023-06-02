package example;

public class Spawn extends Sprite {
    private boolean isPicked;

    public Spawn(String imageURL, double absolutePosX, double absolutePosY, double velocity, double width, double height) {
        super(imageURL, absolutePosX, absolutePosY, velocity, width, height);
        isPicked = false;
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }
}
