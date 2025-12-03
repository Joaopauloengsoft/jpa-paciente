package org;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.utils.HibernateUtil;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface_paciente.fxml")); // <--- CONFIRA O NOME AQUI

            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setTitle("Gerenciamento de Clínica");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERRO CRÍTICO: Não foi possível carregar o arquivo FXML.");
            System.out.println("Verifique se o arquivo está na pasta 'resources' e se o nome está correto.");
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Encerrando conexão com o banco de dados...");
        HibernateUtil.shutdown(); // Fecha o Hibernate corretamente
    }

    public static void main(String[] args) {
        launch(args); // Esse comando que inicia o JavaFX
    }
}