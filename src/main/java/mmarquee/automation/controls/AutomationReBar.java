package mmarquee.automation.controls;

import mmarquee.automation.AutomationException;
import mmarquee.automation.pattern.PatternNotFoundException;

/**
 * Specialised type of pane, with a specific control name.
 *
 * @author Mark Humphreys
 * Date 02/03/2016.
 *
 */
public class AutomationReBar extends AutomationPanel {

    /**
     * The class name.
     */
	public final static String CLASS_NAME = "ReBarWindow32";

    /**
     * Construct the AutomationReBar.
     *
     * @param builder The builder
     * @throws AutomationException Automation library error
     * @throws PatternNotFoundException Pattern not found
     */
    public AutomationReBar(final ElementBuilder builder)
            throws PatternNotFoundException, AutomationException {
        super(builder);
        assertClassName(CLASS_NAME);
    }
}
