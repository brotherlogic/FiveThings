package uk.co.brotherlogic.fivethings;

import java.util.Collection;

import javax.swing.text.JTextComponent;

import uk.co.brotherlogic.fivethings.storage.IBackendStorage;

/**
 * Stores the details of the todo
 * 
 * @author simon
 * 
 */
public class Model
{
	/** The number of todos to use */
	public static final int NUMBER_TODOS = 5;

	/** The todos to deal with */
	private final ToDo[] todos = new ToDo[NUMBER_TODOS];

	/** The backend storage module */
	private final IBackendStorage backend;

	/**
	 * Constructor for the model
	 * 
	 * @param storage
	 *            The storage model to use;
	 */
	public Model(final IBackendStorage storage)
	{
		// Set the storage module
		backend = storage;

		// Read the todos
		Collection<ToDo> stored = storage.getTodaysTodos();

		int todoPointer = 0;
		for (ToDo toDo : stored)
			todos[todoPointer++] = toDo;

		for (; todoPointer < todos.length; todoPointer++)
			todos[todoPointer] = new ToDo();
	}

	/**
	 * Process the model for display
	 * 
	 * @param comp
	 *            The component to fill
	 * @param index
	 *            the index of the component
	 */
	public final void processForDisplay(final int index,
			final JTextComponent comp)
	{
		todos[index].fill(comp);
	}

	/**
	 * Sets the todo as done
	 * 
	 * @param index
	 *            The index of the todo
	 */
	public final void setDone(final int index)
	{
		todos[index].completeTask();
		backend.updateToDo(todos[index]);
	}

	/**
	 * Sets the properties of the given todo
	 * 
	 * @param num
	 *            The number of the todo to set
	 * @param text
	 *            The text to set
	 */
	public final void setTodo(final int num, final String text)
	{
		todos[num].processDetails(text);
		backend.storeToDo(todos[num]);
	}
}
