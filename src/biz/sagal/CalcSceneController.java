package biz.sagal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import biz.sagal.calc.Calculator;
import biz.sagal.calc.TimeModelInterface;
import biz.sagal.calc.Titlebar;

/**
 * Calculator Controller
 * @author Mark Sagal <mark@sagal.biz>
 * @since  2018-06-14
 */
public class CalcSceneController implements Initializable {
	/**
	 * Reference of TableView#timeTable
	 */
	@FXML
	private TableView<TimeModelInterface> timeTable;

	/**
	 * Reference of TableColumn#timeCol
	 */
	@FXML
	private TableColumn<TimeModelInterface, String> timeCol;

	/**
	 * Reference of TableColumn#delCol
	 */
	@FXML
	private TableColumn<TimeModelInterface, TimeModelInterface> delCol;

	/**
	 * Reference of TextArea#timeInput
	 */
	@FXML
	private TextArea timeInput;

	/**
	 * Reference of TextField#maxDaysInput
	 */
	@FXML
	private TextField maxDaysInput;

	/**
	 * Reference of TextField#maxHoursInput
	 */
	@FXML
	private TextField maxHoursInput;

	/**
	 * Reference of TextField#sumField
	 */
	@FXML
	private TextField sumField;

	/**
	 * Reference of Label#sumLabel
	 */
	@FXML
	private Label sumLabel;

	/**
	 * Reference of RadioButton#weeksRadio
	 */
	@FXML
	private RadioButton weeksRadio;

	/**
	 * Reference of RadioButton#daysRadio
	 */
	@FXML
	private RadioButton daysRadio;

	/**
	 * Reference of RadioButton#hoursRadio
	 */
	@FXML
	private RadioButton hoursRadio;

	/**
	 * Reference of Label#pinBtn
	 */
	@FXML
	private Label pinBtn;

	/**
	 * Reference of Tooltip#pinTooltip
	 */
	@FXML
	private Tooltip pinTooltip;

	/**
	 * Reference of ToggleGroup<SumRadio>
	 */
	private ToggleGroup sumRadioGroup;

	/**
	 * Reference of Calculator
	 */
	private Calculator calc;

	/**
	 * Reference of Titlebar
	 */
	private Titlebar titlebar;

	/**
	 * Initializes controller
	 */
	@Override
	public void initialize(final URL url, final ResourceBundle resource) {
		this.calc = new Calculator();
		this.calc.setCollection(FXCollections.observableArrayList());
		this.calc.setTimeTable(this.timeTable);
		this.calc.setTimeColumn(this.timeCol);
		this.calc.setDeleteColumn(this.delCol);
		this.calc.setTimeInput(this.timeInput);
		this.calc.setMaxDaysInput(this.maxDaysInput);
		this.calc.setMaxHoursInput(this.maxHoursInput);
		this.calc.setSumField(this.sumField);
		this.calc.setSumLabel(this.sumLabel);
		this.calc.setTimeInputDefaultPrompt("Example:%n2w 3d 1h 28m%n3w 2d");
		this.calc.setDeleteButton("Remove");
		this.sumRadioGroup = new ToggleGroup();
		this.daysRadio.setToggleGroup(this.sumRadioGroup);
		this.weeksRadio.setToggleGroup(this.sumRadioGroup);
		this.hoursRadio.setToggleGroup(this.sumRadioGroup);
		this.calc.setWeeksRadio(this.weeksRadio);
		this.calc.setDaysRadio(this.daysRadio);
		this.calc.setHoursRadio(this.hoursRadio);
		this.titlebar = new Titlebar();
		this.titlebar.setPinTooltip(pinTooltip);
		this.titlebar.setPinBtn(pinBtn);
	}

	/**
	 * Sum radion action listener
	 * @param event Reference of Event
	 */
	@FXML
	private void onSumRadioAction(final ActionEvent event) {
		this.calc.handleSumRadioAction(event);
	}

	/**
	 * Add button action listener
	 * @param event Reference of Event
	 */
	@FXML
	private void onAddBtnAction(final ActionEvent event) {
		this.calc.handleAddBtnAction(event);
	}

	/**
	 * Sum button action listener
	 * @param event Reference of Event
	 */
	@FXML
	private void onSumBtnAction(final ActionEvent event) {
		this.calc.handleSumBtnAction(event);
	}

	/**
	 * Clear button action listener
	 * @param event Reference of Event
	 */
	@FXML
	private void onClearBtnAction(final ActionEvent event) {
		this.calc.handleClearBtnAction(event);
	}

	/**
	 * Titlebar mouse pressed event listener
	 * @param event
	 */
	@FXML
	private void onTitlebarPressed(final MouseEvent event) {
		this.titlebar.handleTitlebarPressed(event);
	}

	/**
	 * Titlebar mouse dragged event listener
	 * @param event Reference of MouseEvent
	 */
	@FXML
	private void onTitlebarDragged(final MouseEvent event) {
		this.titlebar.handleTitlebarDragged(event);
	}

	/**
	 * Titlebar close button clicked event listener
	 * @param event Reference of MouseEvent
	 */
	@FXML
	private void onCloseBtnClicked(final MouseEvent event) {
		this.titlebar.handleCloseBtnClicked(event);
	}

	/**
	 * Titlebar minimize button clicked event listener
	 * @param event Reference of MouseEvent
	 */
	@FXML
	private void onMinBtnClicked(final MouseEvent event) {
		this.titlebar.handleMinBtnClicked(event);
	}

	/**
	 * Titlebar pin button clicked event listener
	 * @param event Reference of MouseEvent
	 */
	@FXML
	private void onPinBtnClicked(final MouseEvent event) {
		this.titlebar.handlePinBtnClicked(event);
	}
}
