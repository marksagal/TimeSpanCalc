package biz.sagal.calc;

import javafx.beans.property.StringProperty;

/**
 * TimeModelInterface
 * @author Mark Sagal <mark@sagal.biz>
 * @since  2018-06-16
 */
public interface TimeModelInterface {
	/**
	 * Time data getter
	 * @return Returns time data in string
	 */
	public String getTime();

	/**
	 * time datas getter
	 * @return Returns StringProperty time datas
	 */
	public StringProperty getTimeDatas();
}
