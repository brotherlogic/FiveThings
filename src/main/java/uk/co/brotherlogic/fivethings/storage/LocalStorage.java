package uk.co.brotherlogic.fivethings.storage;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.JOptionPane;

import uk.co.brotherlogic.fivethings.ToDo;

/**
 * Backend storage that uses a local directory
 * 
 * @author simon
 * 
 */
public class LocalStorage implements IBackendStorage
{
	/** The file used to store the todos */
	private File storageFile;

	/**
	 * Constructor
	 */
	public LocalStorage()
	{
		prepStorage();
	}

	@Override
	public final Collection<ToDo> getTodaysTodos()
	{
		Collection<ToDo> todays = new LinkedList<ToDo>();

		try
		{
			Calendar today = Calendar.getInstance();

			// Read the objects from the file
			if (storageFile.length() > 0)
			{
				ObjectInputStream ois = new ObjectInputStream(
						new GZIPInputStream((new FileInputStream(storageFile))));
				ToDo next = (ToDo) ois.readObject();
				while (next != null)
				{
					System.err.println("Reading: " + next + " => "
							+ next.applies(today));
					if (next.applies(today))
						todays.add(next);

					next = (ToDo) ois.readObject();
				}
				ois.close();
			}

		}
		catch (EOFException e)
		{
			// Naughty
		}
		catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Cannot read from backend storage");
		}
		catch (ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
		}

		return todays;
	}

	/**
	 * Local storage is in a single file
	 */
	private void prepStorage()
	{
		try
		{
			// Get the home directory
			File homeDir = new File(System.getProperty("user.home"));
			File storageDir = new File(homeDir, ".fivethings");
			if (storageDir.exists() || storageDir.mkdir())
			{
				storageFile = new File(storageDir, "main.gz");
				if (!storageFile.exists())
					if (!storageFile.createNewFile())
						throw new IOException("Cannot create");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(null, "Unable to access storage file");
		}
	}

	@Override
	public final boolean storeToDo(final ToDo toStore)
	{
		try
		{
			// Read all the previous todos
			Collection<ToDo> previous = new LinkedList<ToDo>();

			// Only read if there's something to actually read
			if (storageFile.length() > 0)
			{
				ObjectInputStream ois = new ObjectInputStream(
						new GZIPInputStream(new FileInputStream(storageFile)));
				try
				{
					ToDo todo = (ToDo) ois.readObject();
					while (todo != null)
					{

						previous.add(todo);
						todo = (ToDo) ois.readObject();
					}
				}
				catch (IOException e)
				{
					// Ignore any io exception
					e.printStackTrace();
				}
				ois.close();
			}

			ObjectOutputStream oos = new ObjectOutputStream(
					new GZIPOutputStream((new FileOutputStream(storageFile))));
			for (ToDo todoP : previous)
				oos.writeObject(todoP);
			System.err.println("STORING: " + toStore);
			oos.writeObject(toStore);
			oos.close();
			return true;
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		return false;
	}
}
