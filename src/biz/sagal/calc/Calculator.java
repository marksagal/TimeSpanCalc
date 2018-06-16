package biz.sagal.calc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import biz.sagal.util.StringUtil;

/**
 * Calculator Business Logic
 * @author Mark Sagal <mark@sagal.biz>
 * @since  2018-06-14
 */
public class Calculator {
	/**
	 * Reference of ObservableList
	 */
	private ObservableList<TimeModelInterface> collection;

	/**
	 * Reference of TableView#timeTable
	 */
	private TableView<TimeModelInterface> timeTable;

	/**
	 * Reference of TableColumn#timeCol
	 */
	private TableColumn<TimeModelInterface, String> timeCol;

	/**
	 * Reference of TableColumn#delCol
	 */
	private TableColumn<TimeModelInterface, TimeModelInterface> delCol;

	/**
	 * Reference of TextArea#timeInput
	 */
	private TextArea timeInput;

	/**
	 * Reference of TextField#maxDaysInput
	 */
	private TextField maxDaysInput;

	/**
	 * Reference of TextField#maxHoursInput
	 */
	private TextField maxHoursInput;

	/**
	 * Reference of TextField#sumField
	 */
	private TextField sumField;

	/**
	 * Reference of Label#sumLabel
	 */
	private Label sumLabel;

	/**
	 * Collection setter
	 * @param collection Reference of ObservableList
	 */
	public void setCollection(final ObservableList<TimeModelInterface> collection) {
		this.collection = collection;
	}

	/**
	 * Time table setter
	 * @param timeTable Reference of TableView#timeTable
	 */
	public void setTimeTable(final TableView<TimeModelInterface> timeTable) {
		this.timeTable = timeTable;
		this.timeTable.setItems(this.collection);
	}

	/**
	 * Time column setter
	 * @param timeCol Reference of TableColumn#timeCol
	 */
	public void setTimeColumn(final TableColumn<TimeModelInterface, String> timeCol) {
		this.timeCol = timeCol;
	}

	/**
	 * Delete column setter
	 * @param delCol Reference of TableColumn#delCol
	 */
	public void setDeleteColumn(final TableColumn<TimeModelInterface, TimeModelInterface> delCol) {
		this.delCol = delCol;
	}

	/**
	 * Time input setter
	 * @param timeInput Reference of TextArea#timeInput
	 */
	public void setTimeInput(final TextArea timeInput) {
		this.timeInput = timeInput;
	}

	/**
	 * Max days input setter
	 * @param maxDaysInput Reference of TextField#maxDaysInput
	 */
	public void setMaxDaysInput(final TextField maxDaysInput) {
		this.maxDaysInput = maxDaysInput;
	}

	/**
	 * Max hours input setter
	 * @param maxHoursInput Reference of TextField#maxHoursInput
	 */
	public void setMaxHoursInput(final TextField maxHoursInput) {
		this.maxHoursInput = maxHoursInput;
	}

	/**
	 * Sum field setter
	 * @param sumField Reference of TextField#sumField
	 */
	public void setSumField(final TextField sumField) {
		this.sumField = sumField;
	}

	/**
	 * Sum label setter
	 * @param sumLabel Reference of Label#sumLabel
	 */
	public void setSumLabel(final Label sumLabel) {
		this.sumLabel = sumLabel;
	}

	/**
	 * Time input default prompt setter
	 * @param defaultMsg String placeholder
	 */
	public void setTimeInputDefaultPrompt(final String defaultMsg) {
		this.timeInput.setPromptText(String.format(defaultMsg));
	}

	/**
	 * Delete button setter
	 * @param btnName Name of button(s)
	 */
	public void setDeleteButton(final String btnName) {
		final ObservableList<TimeModelInterface> collection = this.collection;
		final Calculator calc = this;
		this.delCol.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));
		this.delCol.setCellFactory(cell -> new TableCell<TimeModelInterface, TimeModelInterface>() {
			private final Button delBtn = new Button(btnName);
			@Override
			protected void updateItem(final TimeModelInterface data, final boolean empty) {
				super.updateItem(data, empty);
				if (data == null) {
					this.setGraphic(null);
					return;
				}
				this.setGraphic(this.delBtn);
				this.delBtn.setOnAction(event -> {
					calc.clearSummary();
					collection.remove(data);
				});
			}
		});
	}

	/**
	 * Alert dialog
	 * @param title   Alert Title
	 * @param header  Alert Header
	 * @param message Alert Message
	 * @param type    Alert type
	 */
	private void alert(final String title, final String header, final String message, final AlertType type)
	{
		final Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
	 * Add button action handler
	 * @param event Reference of Event
	 */
	public void handleAddBtnAction(final ActionEvent event) {
		this.clearSummary();
		final String[] rawStringDatas = StringUtil.getLines(this.timeInput.getText());
		final String[] stringDatas = StringUtil.formatStringArray(rawStringDatas);
		try {
			this.checkStringDatas(stringDatas);
		} catch (final Exception e) {
			this.alert("Invalid Input", "Please correct the following line(s)", e.getMessage(), AlertType.ERROR);
			return;
		}
		for (final String stringData: stringDatas) {
			if (stringData.length() == 0) {
				continue;
			}
			this.collection.add(new TimeModel(stringData));
		}
		this.timeCol.setCellValueFactory(cellVal -> cellVal.getValue().getTimeDatas());
		this.timeInput.clear();
	}

	/**
	 * Checks string datas throws exception on failure
	 * @param  stringDatas
	 * @throws Exception
	 */
	private void checkStringDatas(final String[] stringDatas) throws Exception {
		final String regex = "^\\d{1,}[w,d,h,m]$";
		final int dataLen = stringDatas.length;
		final HashMap<Integer, String> errors = new HashMap<>();
		for (int i = 0; i < dataLen; i++) {
			final String timeSpan = stringDatas[i];
			final String[] times = timeSpan.split(" ");
			int errCount = 0;
			for (String time: times) {
				if (time.matches(regex) || time.length() == 0) {
					continue;
				}
				errCount++;
			}
			if (errCount == 0) {
				continue;
			}
			errors.put(i + 1, timeSpan);
		}
		if (errors.isEmpty() == false) {
			String errMsg = "";
			Iterator<Entry<Integer, String>> it = errors.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Integer, String> err = it.next();
				errMsg += String.format("line %d: %s%n", err.getKey(), err.getValue());
			}
			throw new Exception(errMsg);
		}
	}

	/**
	 * Checks time margin input throws exception on failure
	 * @throws Exception
	 */
	private void checkTimeMarginInput() throws Exception {
		final String maxDaysInputText = this.maxDaysInput.getText();
		final String maxHoursInputText = this.maxHoursInput.getText();
		String errMsg = "";
		// Checks Day(s) a week field
		if (maxDaysInputText.length() == 0) {
			errMsg += "Day(s) a week is a required field%n";
		} else if (maxDaysInputText.matches("^\\d{1,}$") == false) {
			errMsg += "Day(s) a week should be a number%n";
		} else if (Integer.parseInt(maxDaysInputText) <= 0) {
			errMsg += "Day(s) a week cannot be lessthan or equal to zero%n";
		}
		// Checks Hour(s) a day field
		if (maxHoursInputText.length() == 0) {
			errMsg += "Hour(s) a day is a required field%n";
		} else if (maxHoursInputText.matches("^\\d{1,}$") == false) {
			errMsg += "Hours(s) a day should be a number%n";
		} else if (Integer.parseInt(maxHoursInputText) <= 0) {
			errMsg += "Hours(s) a day cannot be lessthan or equal to zero%n";
		}
		// Checks error message
		if (errMsg.length() != 0) {
			throw new Exception(String.format(errMsg));
		}
	}

	/**
	 * Sum button action handler
	 * @param event Reference of Event
	 */
	public void handleSumBtnAction(final ActionEvent event) {
		this.clearSummary();
		try {
			this.checkTimeMarginInput();
		} catch (Exception e) {
			this.alert("Invalid Input", "Please correct the following field(s)", e.getMessage(), AlertType.ERROR);
			return;
		}
		if (this.collection.isEmpty()) {
			this.alert("Warning!", "Warning!", "No content in table!", AlertType.WARNING);
			return;
		}
		final HashMap<String, Integer> collectionSum = this.getCollectionSum();
		final int weeks = collectionSum.get("w");
		final int days = collectionSum.get("d");
		final int hours = collectionSum.get("h");
		final int minutes = collectionSum.get("m");
		final int maxDays = Integer.parseInt(this.maxDaysInput.getText());
		final int maxHours = Integer.parseInt(this.maxHoursInput.getText());
		final TimeMutation mutate = new TimeMutation(weeks, days, hours, minutes);
		mutate.setMargin(maxDays, maxHours);
		final int[] mutatedTimeSpan = mutate.getMutatedTime();
		this.showSummary(this.parseSummary(mutatedTimeSpan));
	}

	/**
	 * Gets collection sum
	 * @return Returns Annotation, Sum
	 */
	private HashMap<String, Integer> getCollectionSum() {
		final HashMap<String, Integer> collectionSum = new HashMap<>();
		final String[] annotations = {"w", "d", "h", "m"};
		for (final String annotation: annotations) {
			collectionSum.put(annotation, 0);
		}
		for (final TimeModelInterface collection: this.collection) {
			final String timeSpans = collection.getTime();
			for (String timeSpan: timeSpans.split(" ")) {
				for (final String annotation: annotations) {
					if (timeSpan.contains(annotation)) {
						final int timeValue = Integer.parseInt(timeSpan.replace(annotation, ""));
						final int currentValue = collectionSum.get(annotation);
						collectionSum.put(annotation, currentValue + timeValue);
					}
				}
			}
		}
		return collectionSum;
	}

	/**
	 * Parses summary
	 * @param timeSpans Array of ints {weeks, days, hours, minutes}
	 * @return Parsed summary
	 */
	private String parseSummary(final int[] timeSpans) {
		final int weeks = timeSpans[0];
		final int days = timeSpans[1];
		final int hours = timeSpans[2];
		final int minutes = timeSpans[3];
		String parsedSummary = "";
		if (weeks != 0) {
			parsedSummary += String.format("%dw ", weeks);
		}
		if (days != 0) {
			parsedSummary += String.format("%dd ", days);
		}
		if (hours != 0) {
			parsedSummary += String.format("%dh ", hours);
		}
		if (minutes != 0) {
			parsedSummary += String.format("%dm ", minutes);
		}
		return parsedSummary.trim();
	}

	/**
	 * Clears and hide summary
	 */
	private void clearSummary() {
		this.sumField.setVisible(false);
		this.sumLabel.setVisible(false);
		this.sumField.clear();
	}

	/**
	 * Shows summary
	 * @param summary Text summary
	 */
	private void showSummary(final String summary) {
		this.sumField.setText(summary);
		this.sumField.setVisible(true);
		this.sumLabel.setVisible(true);
	}
}
