package biz.sagal.calc;

/**
 * TimeMutation
 * @author Mark Sagal <mark@sagal.biz>
 * @since  2018-06-16
 */
public class TimeMutation {
	/**
	 * Mutable weeks
	 */
	private int[] mutableWeeks;

	/**
	 * Mutable days
	 */
	private int[] mutableDays;

	/**
	 * Mutable hours
	 */
	private int[] mutableHours;

	/**
	 * Mutable minutes
	 */
	private int[] mutableMinutes;

	/**
	 * default 5 days a week
	 */
	private int maxDays = 5;

	/**
	 * default 8 hours a day
	 */
	private int maxHours = 8;

	/**
	 * Maximum 60 minutes an hour
	 */
	private final int maxMinutes = 60;

	/**
	 * Initialize TimeMutation
	 * @param weeks   Weeks to mutate
	 * @param days    Days to mutate
	 * @param hours   Hours to mutate
	 * @param minutes Minutes to mutate
	 */
	public TimeMutation(final int weeks, final int days, final int hours, final int minutes) {
		this.mutableWeeks = new int[] {weeks};
		this.mutableDays = new int[] {days};
		this.mutableHours = new int[] {hours};
		this.mutableMinutes = new int[] {minutes};
	}

	/**
	 * Sets max days and hours margin
	 * @param  maxDays  # of days a week
	 * @param  maxHours # of hours a day
	 * @return TimeMutation
	 */
	public TimeMutation setMargin(final int maxDays, final int maxHours) {
		this.maxDays = maxDays;
		this.maxHours = maxHours;
		return this;
	}

	/**
	 * If lowerInt reach the threshold the value will mutate to higherInt
	 * @param  maxInt    Int threshold
	 * @param  higherInt Mutable higher int
	 * @param  lowerInt  Mutable lower int
	 * @return Value of lowerInt
	 * @throws Exception Max int cannot be 0
	 */
	private int getMutatedInt(final int maxInt, final int[] higherInt, final int[] lowerInt) throws Exception {
		if (maxInt == 0) {
			throw new Exception("Max int cannot be 0");
		}
		if (lowerInt[0] < maxInt) {
			return lowerInt[0];
		} else if (this.isDivisible(maxInt, lowerInt[0]) && lowerInt[0] >= maxInt) {
			higherInt[0] += lowerInt[0] / maxInt;
			return 0;
		} else {
			while (this.isDivisible(maxInt, lowerInt[0]) == false && lowerInt[0] >= maxInt) {
				higherInt[0] += 1;
				lowerInt[0] -= maxInt;
			}
			return lowerInt[0];
		}
	}

	/**
	 * Gets mutated time
	 * @return Array of ints {weeks, days, hours, minutes}
	 */
	public int[] getMutatedTime() {
		final int[] mutatedTime = {0, 0, 0, 0};
		try {
			mutatedTime[3] = this.getMutatedMinutes();
			mutatedTime[2] = this.getMutatedHours();
			mutatedTime[1] = this.getMutatedDays();
			mutatedTime[0] = this.getMutatedWeeks();
		} catch (final Exception e) {}
		return mutatedTime;
	}

	/**
	 * Gets mutated weeks
	 * @return mutated weeks
	 */
	private int getMutatedWeeks() {
		return this.mutableWeeks[0];
	}

	/**
	 * Gets mutated days
	 * @return mutated days
	 * @throws Exception Max int cannot be 0
	 */
	private int getMutatedDays() throws Exception {
		return this.getMutatedInt(this.maxDays, this.mutableWeeks, this.mutableDays);
	}

	/**
	 * Gets mutated hours
	 * @return mutated hours
	 * @throws Exception Max int cannot be 0
	 */
	private int getMutatedHours() throws Exception {
		return this.getMutatedInt(this.maxHours, this.mutableDays, this.mutableHours);
	}

	/**
	 * Gets mutated minutes
	 * @return mutated minutes
	 * @throws Exception Max int cannot be 0
	 */
	private int getMutatedMinutes() throws Exception {
		return this.getMutatedInt(this.maxMinutes, this.mutableHours, this.mutableMinutes);
	}

	/**
	 * Checks if dividend is divisible by divisor
	 * @param divisor
	 * @param dividend
	 * @return Returns true if dividend is divisible by divisor
	 */
	private final boolean isDivisible(final int divisor, final int dividend) {
		try {
			return (dividend % divisor) == 0;
		} catch (ArithmeticException e) {
			return false;
		}
	}
}
