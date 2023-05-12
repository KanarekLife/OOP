module com.nieradko.worldsim {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.nieradko.worldsim to javafx.fxml;
    exports com.nieradko.worldsim;
    exports com.nieradko.worldsim.controllers;
    opens com.nieradko.worldsim.controllers to javafx.fxml;
}