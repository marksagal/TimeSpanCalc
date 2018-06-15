package biz.sagal;

import java.net.URL;
import java.util.ResourceBundle;
import biz.sagal.calc.TimeModel;
import biz.sagal.util.StringUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

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
	private TableView<TimeModel> timeTable;

	/**
	 * Reference of TableColumn#timeCol
	 */
	@FXML
	private TableColumn<TimeModel, String> timeCol;

	/**
	 * Reference of TableColumn#delCol
	 */
	@FXML
	private TableColumn<TimeModel, TimeModel> delCol;

	/**
	 * Reference of TextArea#timeInput
	 */
	@FXML
	private TextArea timeInput;

	/**
	 * Collection of timeDatas
	 */
	private ObservableList<TimeModel> timeDatas = FXCollections.observableArrayList();

	/**
	 * Initializes controller
	 */
	@Override
	public void initialize(final URL url, final ResourceBundle resource) {
		this.setTimeInputDefaultPrompt("Example:%n2w 3d 1h%n3w 2d");
		this.setDelCol("Remove");
		this.timeTable.setItems(this.timeDatas);
	}

	/**
	 * Delete column setter
	 * @param btnName Name of delete button
	 */
	private void setDelCol(final String btnName) {
		final ObservableList<TimeModel> timeDatas = this.timeDatas;
		delCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		delCol.setCellFactory(param -> new TableCell<TimeModel, TimeModel>() {
			private final Button delBtn = new Button(btnName);
			@Override
			protected void updateItem(final TimeModel data, final boolean empty) {
				super.updateItem(data, empty);
				if (data == null) {
					this.setGraphic(null);
					return;
				}
				this.setGraphic(this.delBtn);
				delBtn.setOnAction(event -> timeDatas.remove(data));
			}
		});
	}

	/**
	 * Add button action listener
	 * @param event Reference of Event
	 */
	@FXML
	private void onAddBtnAction(final ActionEvent event) {
		final String[] stringDatas = StringUtil.getLines(this.timeInput.getText());
		for (final String stringData: stringDatas) {
			if (stringData.length() > 0) {
				this.timeDatas.add(new TimeModel(stringData));
			}
		}
		this.timeCol.setCellValueFactory(cellVal -> cellVal.getValue().getTimeDatas());
		this.timeInput.clear();
		this.timeInput.focusedProperty();
	}

	/**
	 * Time input default prompt setter
	 * @param defaultMsg Time input default message
	 */
	private void setTimeInputDefaultPrompt(final String defaultMsg) {
		this.timeInput.setPromptText(String.format(defaultMsg));
	}
}
