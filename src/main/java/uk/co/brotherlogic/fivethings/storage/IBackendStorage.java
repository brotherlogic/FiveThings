package uk.co.brotherlogic.fivethings.storage;

import java.util.Collection;

import uk.co.brotherlogic.fivethings.ToDo;

/**
 * To deal with the backend storage
 * 
 * @author simon
 * 
 */
public interface IBackendStorage
{
	/**
	 * Retrieves the days todos from the given storage module
	 * 
	 * @return A collection of todos that are appropriate for the day
	 */
	Collection<ToDo> getTodaysTodos();

	/**
	 * Stores the todo
	 * 
	 * @param toStore
	 *            The todo to store
	 * @return whether the todo was added
	 */
	boolean storeToDo(ToDo toStore);
	
	/**
	 * Updates the todo
	 * @param toUpdate The todo to update
	 * @return whether the update was successful
	 */
	boolean updateToDo(ToDo toUpdate);
}
