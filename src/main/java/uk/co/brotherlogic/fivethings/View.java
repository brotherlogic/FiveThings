package uk.co.brotherlogic.fivethings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The main view for the five things app
 * 
 * @author simon
 * 
 */
public class View extends JFrame
{
	/** Generated serial id */
	private static final long serialVersionUID = 9220990076400973307L;

	/** The model to use for this view */
	private final Model model;

	/** The minimum width of the window */
	private static final int MINIMUM_WIDTH = 300;

	/**
	 * Constructor
	 * 
	 * @param mod
	 *            The Model to use in the view
	 */
	public View(final Model mod)
	{
		model = mod;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initDisplay();
		pack();

		// Check our width
		if (this.getWidth() < MINIMUM_WIDTH)
			this.setSize(MINIMUM_WIDTH, this.getHeight());

		setLocationRelativeTo(null);
	}

	/**
	 * Prepares the display for use
	 */
	private void initDisplay()
	{
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		// Do a first pass layout
		for (int i = 0; i < Model.NUMBER_TODOS; i++)
		{
			JTextField textField = new JTextField();
			model.processForDisplay(i, textField);

			JPanel mainPanel = new JPanel();
			if (textField.getText().length() == 0)
				setForUndecided(i, mainPanel, textField);
			else if (textField.getText().equals("Complete"))
				setForDone(mainPanel, textField);
			else
				setForDecided(i, mainPanel, textField);

			gbl.addLayoutComponent(mainPanel, new GridBagConstraints(0, i, 1,
					1, 1.0, 1.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			this.add(mainPanel);
		}
	}

	/**
	 * Sets the panel to show an undecided element
	 * 
	 * @param num
	 *            The number of the todo
	 * @param panel
	 *            The panel to prepare
	 * @param textField
	 *            THe text field to use for preparation
	 */
	private void setForDecided(final int num, final JPanel panel,
			final JTextField textField)
	{
		GridBagLayout gbl = new GridBagLayout();
		panel.setLayout(gbl);

		gbl.setConstraints(textField, new GridBagConstraints(0, 0, 1, 1, 1.0,
				1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		JButton setButton = new JButton("Done");
		gbl.setConstraints(setButton, new GridBagConstraints(1, 0, 1, 1, 0.0,
				1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		panel.add(textField);
		panel.add(setButton);
		panel.validate();

		setButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				panel.removeAll();
				model.setDone(num);
				setForDone(panel, textField);
			}
		});
	}

	/**
	 * Sets the panel to show an undecided element
	 * 
	 * @param panel
	 *            The panel to prepare
	 * @param textField
	 *            THe text field to use for preparation
	 */
	private void setForDone(final JPanel panel, final JTextField textField)
	{
		GridBagLayout gbl = new GridBagLayout();
		panel.setLayout(gbl);

		gbl.setConstraints(panel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));
		panel.add(textField);
	}

	/**
	 * Sets the panel to show an undecided element
	 * 
	 * @param num
	 *            The number of the todo
	 * @param panel
	 *            The panel to prepare
	 * @param textField
	 *            THe text field to use for preparation
	 */
	private void setForUndecided(final int num, final JPanel panel,
			final JTextField textField)
	{
		GridBagLayout gbl = new GridBagLayout();
		panel.setLayout(gbl);

		gbl.setConstraints(textField, new GridBagConstraints(0, 0, 1, 1, 1.0,
				1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		panel.add(textField);

		textField.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				panel.removeAll();
				model.setTodo(num, textField.getText());
				setForDecided(num, panel, textField);
			}
		});
	}
}
