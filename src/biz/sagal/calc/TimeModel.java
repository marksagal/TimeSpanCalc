package biz.sagal.calc;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * TimeModel
 * @author Mark Sagal <mark@sagal.biz>
 * @since  2018-06-14
 */
public class TimeModel {
	/**
	 * Time datas
	 */
	private String timeDatas;

	/**
	 * Time Model constructor
	 * @param timeDatas Time datas
	 */
	public TimeModel(final String timeDatas) {
		this.timeDatas = timeDatas;
	}

	/**
	 * time datas getter
	 * @return Returns StringProperty time datas
	 */
	public StringProperty getTimeDatas() {
		return new SimpleStringProperty(this.timeDatas);
	}
}
