module com.ceri.ceribnb {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.ceri.ceribnb to javafx.fxml;
    exports com.ceri.ceribnb;
}