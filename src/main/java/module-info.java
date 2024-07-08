module org.example.snakeladder {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.snakeladder to javafx.fxml;
    exports org.example.snakeladder;
}