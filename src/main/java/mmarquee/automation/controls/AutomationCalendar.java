/*
 * Copyright 2016-17 inpwtepydjuf@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmarquee.automation.controls;

import mmarquee.automation.AutomationElement;
import mmarquee.automation.AutomationException;
import mmarquee.automation.UIAutomation;
import mmarquee.automation.pattern.PatternNotFoundException;
import mmarquee.automation.pattern.Value;

/**
 * Wrapper for the Calendar element.
 *
 * @author Mark Humphreys
 * Date 16/02/2016.
 *
 * Implements IGridProvider, IScrollProvider, ITableProvider, IValueProvider
 */
public class AutomationCalendar extends AutomationBase implements Valueable {
    /**
     * The value pattern.
     */
    private Value valuePattern;

    /**
     * Constructor for the AutomationCalendar.
     *
     * @param builder The element builder.
     * @throws AutomationException Automation library error.
     * @throws PatternNotFoundException Expected pattern not found.
     */
    public AutomationCalendar(final ElementBuilder builder)
            throws PatternNotFoundException, AutomationException {
        super(builder);

        if (builder.getHasValue()) {
            this.valuePattern = builder.getValue();
        } else {
            this.valuePattern = this.getValuePattern();
        }
    }

    /**
     * Gets the current value of the control.
     *
     * @return The current value.
     * @throws AutomationException Something has gone wrong.
     */
    public String getValue() throws AutomationException {
        return this.valuePattern.value();
    }
}