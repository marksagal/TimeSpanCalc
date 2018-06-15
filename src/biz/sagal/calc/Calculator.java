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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
				this.delBtn.setOnAction(event -> collection.remove(data));
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
	 * Sum button action handler
	 * @param event Reference of Event
	 */
	public void handleSumBtnAction(final ActionEvent event) {
		if (this.collection.isEmpty()) {
			this.alert("Warning!", "Warning!", "No content in table!", AlertType.WARNING);
			return;
		}
		for (TimeModelInterface t: this.collection) {
			System.out.println(t.getTime());
		}
	}
}
