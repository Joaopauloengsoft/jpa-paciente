module org.jpapaciente {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.jpapaciente to javafx.fxml;
    exports org.jpapaciente;
}