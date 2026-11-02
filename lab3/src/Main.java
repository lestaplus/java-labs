import model.ShapeModel;
import service.LoggerConfig;
import view.ShapeView;
import controller.ShapeController;

import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LoggerConfig.setup();
        logger.info("App launched");

        ShapeModel model = new model.ShapeModel();
        ShapeView view = new ShapeView();
        ShapeController controller = new ShapeController(model, view);

        controller.processUser();

        logger.info("App finished");
    }
}
