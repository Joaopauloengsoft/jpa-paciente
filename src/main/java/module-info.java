module org.jpapaciente {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jakarta.persistence;


    opens org.jpapaciente to javafx.fxml;
    exports org.jpapaciente;
}