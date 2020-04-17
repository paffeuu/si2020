package si.lista3.gui.control;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class FieldCircle extends Circle
{

    public FieldCircle(Paint paint, double rectangleSize)
    {
        setRadius(rectangleSize);
        setFill(paint);
    }

    public void setPlayerX()
    {
        setFill(Paint.valueOf("red"));
        setDisable(true);
        setDisabled(true);
    }

    public void setPlayerY()
    {
        setFill(Paint.valueOf("green"));
        setDisable(true);
        setDisabled(true);
    }
}
