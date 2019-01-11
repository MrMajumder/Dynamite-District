package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/* This is a self created class for having the option of rotation
* of the image on the canvas, as only an Image object cannot do that
 */
public class MyImage {
    private ImageView imageView;
    private SnapshotParameters parameters;
    private Image image;
    private double degree;

    public ImageView getImageView() {   //returns the imageview obj
        return imageView;
    }

    //sets almost everything up for the myImage object - Shaf
    public void makeMyImage(String imageURL, double width, double height) {
        this.imageView = new ImageView(new Image( imageURL, width, height, false, false ));
        this.parameters = new SnapshotParameters();
        this.parameters.setFill(Color.TRANSPARENT);

    }

    //sets the rotation of the MyImage object - Shaf
    public Image setRotate (double degree) {
        this.degree = degree;
        this.imageView.setRotate(degree);
        this.image = this.imageView.snapshot(this.parameters, null);
        return this.image;
    }

    //returns the width - shaf
    public double getWidth () {
        return this.image.getWidth();
    }

    //returns the height - Shaft
    public double getHeight () {
        return this.image.getHeight();
    }
}
