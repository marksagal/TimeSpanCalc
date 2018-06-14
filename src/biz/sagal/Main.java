package biz.sagal;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(final Stage stage) {
		try {
			final BorderPane root = (BorderPane)FXMLLoader.load(this.getClass().getResource("CalcScene.fxml"));
			final Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(this.getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (final Exception e) {}
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}
}
