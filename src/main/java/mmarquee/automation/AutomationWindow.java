package mmarquee.automation;

import mmarquee.automation.uiautomation.*;

/**
 * Created by inpwt on 26/01/2016.
 */
public class AutomationWindow extends AutomationContainer {

    public void focus() {
        this.element.setFocus();
    }

    public AutomationWindow (IUIAutomationElement element, IUIAutomation uiAuto) {
        super(element, uiAuto);
    }

    public AutomationStatusBar getStatusBar() {
        IUIAutomationCondition condition = uiAuto.createTrueCondition();

        IUIAutomationElementArray collection = this.element.findAll(TreeScope.TreeScope_Descendants, condition);

        int length = collection.length();

        AutomationStatusBar found = null;

        for (int count = 0; count < length; count++) {
            IUIAutomationElement element = collection.getElement(count);

            int retVal = element.currentControlType();

            if (retVal == ControlTypeID.StatusBarControlTypeId) {
                found = new AutomationStatusBar(element, uiAuto);
            }
        }

        return found;
    }

    public AutomationMainMenu getMainMenu() {
        IUIAutomationElement menu = this.getControlByControlType(0, ControlTypeID.MenuBarControlTypeId);

        return (new AutomationMainMenu(menu, this.uiAuto));
    }
}