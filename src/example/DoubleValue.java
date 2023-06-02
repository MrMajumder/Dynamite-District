package example;


/*This class is used to setup a double value inside the
* animation timer as it cannot have any thing that will
* be reinitialized inside it - Shaf
*/
public class DoubleValue {
    double value;

    public double getValue() {  //getter
        return value;
    }

    public void setValue(double value) {    //setter
        this.value = value;
    }
}
