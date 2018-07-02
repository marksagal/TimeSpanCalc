package biz.sagal.calc;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Calculator Titlebar
 * @author Mark Sagal <mark@sagal.biz>
 * @since  2018-07-02
 */
public class Titlebar {
	/**
	 * Reference of Label#pinBtn
	 */
	private Label pinBtn;

	/**
	 * Reference of Tooltip#pinTooltip
	 */
	private Tooltip pinTooltip;

	/**
	 * X and Y position
	 */
	private double posX, posY;

	/**
	 * Label#pinBtn setter
	 * @param pinBtn Reference of Label#pinBtn
	 */
	public void setPinBtn(final Label pinBtn) {
		this.pinBtn = pinBtn;
		this.pinBtn.setTextFill(Color.web("#fff"));
		this.pinTooltip.setText("Always On Top: Off");
	}

	/**
	 * Pin tooltip setter
	 * @param pinTooltip
	 */
	public void setPinTooltip(final Tooltip pinTooltip) {
		this.pinTooltip = pinTooltip;
	}

	/**
	 * Handles Titlebar's pressed
	 * @param event Reference of MouseEvent
	 */
	public void handleTitlebarPressed(final MouseEvent event) {
		this.posX = event.getSceneX();
		this.posY = event.getSceneY();
	}

	/**
	 * Handles Titlebar's dragged
	 * @param event Reference of MouseEvent
	 */
	public void handleTitlebarDragged(final MouseEvent event) {
		final Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setX(event.getScreenX() - this.posX);
		stage.setY(event.getScreenY() - this.posY);
	}

	/**
	 * Titlebar close button clicked event handler
	 * @param event Reference of MouseEvent
	 */
	public void handleCloseBtnClicked(final MouseEvent event) {
		final Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
	}

	/**
	 * Titlebar minimize button clicked event handler
	 * @param event Reference of MouseEvent
	 */
	public void handleMinBtnClicked(final MouseEvent event) {
		final Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	/**
	 * Titlebar pin button clicked event handler
	 * @param event Reference of MouseEvent
	 */
	public void handlePinBtnClicked(final MouseEvent event) {
		final Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		if (stage.isAlwaysOnTop()) {
			stage.setAlwaysOnTop(false);
			this.pinBtn.setTextFill(Color.web("#fff"));
			this.pinTooltip.setText("Always On Top: Off");
		} else {
			stage.setAlwaysOnTop(true);
			this.pinBtn.setTextFill(Color.web("#cc1515"));
			this.pinTooltip.setText("Always On Top: On");
		}
	}
}
