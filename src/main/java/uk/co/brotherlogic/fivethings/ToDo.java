package uk.co.brotherlogic.fivethings;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;

import javax.swing.text.JTextComponent;

/**
 * Class which represents a single todo item
 * 
 * @author simon
 * 
 */
public class ToDo implements Serializable
{
	/** Generated serial version ID */
	private static final long serialVersionUID = -4386679053013346720L;

	/** The date that the todo must be complete by */
	private final Calendar date;

	/** The details of the todo */
	private String todo;

	/** Whether the task is complete */
	private boolean complete;

	/** The comparison metrics */
	private static int[] comps = new int[]
	{ Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH };

	/**
	 * Constructor
	 */
	public ToDo()
	{
		todo = "";
		date = Calendar.getInstance();
		complete = false;
	}

	/**
	 * Constructor
	 * 
	 * @param text
	 *            The text of the todo
	 * @param timestamp
	 *            The date of the todo
	 * @param state
	 *            The state of the task
	 */
	public ToDo(final String text, final Calendar timestamp, final boolean state)
	{
		date = Calendar.getInstance();
		date.setTime(timestamp.getTime());
		todo = text;
		complete = state;
	}

	/**
	 * See if this todo applies to the current date
	 * 
	 * @param dateAppl
	 *            THe date to compare
	 * @return Boolean if the todo applies
	 */
	public final boolean applies(final Calendar dateAppl)
	{
		for (int comp : comps)
			if (dateAppl.get(comp) != date.get(comp))
				return false;

		return true;
	}

	/**
	 * Method to finish off the task
	 */
	public final void completeTask()
	{
		complete = true;
	}

	/**
	 * Fills a text component with the details
	 * 
	 * @param comp
	 *            The text component to fill
	 */
	public final void fill(final JTextComponent comp)
	{
		if (complete)
			comp.setText("Complete");
		else
			comp.setText(todo);
	}

	/**
	 * Process the details of this todo
	 * 
	 * @param details
	 *            The details of the todo
	 */
	public final void processDetails(final String details)
	{
		todo = details;
	}

	@Override
	public final String toString()
	{
		DateFormat df = DateFormat.getDateInstance();
		return complete + ": " + todo + " (" + df.format(date.getTime());
	}
}
