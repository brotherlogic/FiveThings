package uk.co.brotherlogic.fivethings;

import uk.co.brotherlogic.fivethings.storage.IBackendStorage;
import uk.co.brotherlogic.fivethings.storage.LocalStorage;

/**
 * The controller for the
 * 
 * @author simon
 * 
 */
public class Controller
{
	/**
	 * Main Method
	 * 
	 * @param args
	 *            No arguments used
	 */
	public static void main(final String[] args)
	{
		Controller cont = new Controller();
		cont.run();
	}

	/**
	 * Start the app
	 */
	public final void run()
	{
		IBackendStorage storage = new LocalStorage();
		Model mod = new Model(storage);
		View view = new View(mod);

		view.setVisible(true);
	}
}
