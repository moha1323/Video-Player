package timer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import events.TimerRanOutEvent;
import events.TimerTickedEvent;

/**
 * The class, {@code Timer}, allows a certain time period to be set when
 * created. It sends signals back to its creator every second and a timer runs
 * out message when the time period has elapsed.
 *
 * @author Yahya Mohamed, Marvin Va, and Adnan Wazwaz
 * @version 12 December 2020
 *
 */
public class Timer implements PropertyChangeListener {
	private int timeValue;
	private Notifiable client;

	/**
	 * Sets up the timer for a certain client with an initial time value.
	 *
	 * @param client    the client, a Notifiable object
	 * @param timeValue the initial time value after which the timer runs out of
	 *                  time.
	 */
	public Timer(Notifiable client, int timeValue) {
		this.client = client;
		this.timeValue = timeValue;
		Clock.instance().addPropertyChangeListener(this);
	}

	/**
	 * The timer value could be changed using this method.
	 *
	 * @param value the increment (could be negative) for the time value.
	 */
	public void addTimeValue(int value) {
		timeValue += value;
	}

	/**
	 * Stops the timer by deleting itself from the list of observers
	 */
	public void stop() {
		Clock.instance().removePropertyChangeListener(this);
	}

	/**
	 * Returns the time value left
	 *
	 * @return the time value left in the timer
	 */
	public int getTimeValue() {
		return timeValue;
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (--timeValue <= 0) {
			client.handleEvent(TimerRanOutEvent.instance());
			Clock.instance().removePropertyChangeListener(this);
		} else {
			client.handleEvent(new TimerTickedEvent(timeValue));
		}
	}
}