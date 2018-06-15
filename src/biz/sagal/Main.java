package biz.sagal;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * @author Mark Sagal <mark@sagal.biz>
 * @since  2018-06-14
 */
public class Main extends Application {
	@Override
	public void start(final Stage stage) {
		try {
			final BorderPane root = (BorderPane)FXMLLoader.load(this.getClass().getResource("CalcScene.fxml"));
			final Scene scene = new Scene(root, 380, 380);
			scene.getStylesheets().add(this.getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("Time Span Calculator");
			stage.setResizable(false);
			stage.show();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}
}
