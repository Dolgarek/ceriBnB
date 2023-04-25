module com.ceri.ceribnb {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires com.google.gson;


    opens com.ceri.ceribnb to javafx.fxml;
    exports com.ceri.ceribnb;
    exports com.ceri.ceribnb.helper;
    opens com.ceri.ceribnb.helper to javafx.fxml;
}