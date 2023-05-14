module com.nieradko.worldsim {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.nieradko.worldsim to javafx.fxml;
    exports com.nieradko.worldsim;
    exports com.nieradko.worldsim.controllers;
    exports com.nieradko.worldsim.core;
    exports com.nieradko.worldsim.core.animals;
    exports com.nieradko.worldsim.core.plants;
    opens com.nieradko.worldsim.controllers to javafx.fxml;
    exports com.nieradko.worldsim.core.positions;
}