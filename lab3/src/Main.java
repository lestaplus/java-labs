import model.ShapeModel;
import view.ShapeView;
import controller.ShapeController;

public class Main {
    public static void main(String[] args) {
        ShapeModel model = new model.ShapeModel();
        view.ShapeView view = new ShapeView();
        ShapeController controller = new ShapeController(model, view);

        controller.processUser();
    }
}
