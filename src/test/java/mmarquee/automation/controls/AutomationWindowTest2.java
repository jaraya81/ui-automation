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

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import mmarquee.automation.AutomationElement;
import mmarquee.automation.AutomationException;
import mmarquee.automation.controls.rebar.AutomationReBar;
import mmarquee.automation.pattern.ItemContainer;
import mmarquee.automation.pattern.PatternNotFoundException;
import mmarquee.automation.pattern.Window;
import mmarquee.automation.uiautomation.IUIAutomationElement;
import mmarquee.automation.uiautomation.IUIAutomationInvokePattern;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Mark Humphreys on 28/12/2016.
 *
 * AutomationWindow tests that rely on mocking behaviour
 */
public class AutomationWindowTest2 {

    @Mock
    AutomationElement element;

    @Mock
    Window window;

    @Mock
    ItemContainer container;

    @Mock
    User32 user32;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMaximize_Gets_Maximize_From_Pattern() throws AutomationException, PatternNotFoundException {
        AutomationWindow wndw = new AutomationWindow(element, window, container);

        wndw.maximize();

        verify(window, atLeast(1)).maximize();
    }

    @Test
    public void testMinimize_Calls_Minimize_From_Pattern() throws AutomationException, PatternNotFoundException {
        AutomationWindow wndw = new AutomationWindow(element, window, container);

        wndw.minimize();

        verify(window, atLeast(1)).minimize();
    }

    @Test
    public void testGetName_Gets_Name_From_Element() throws AutomationException, PatternNotFoundException {
        when(element.getName()).thenReturn("NAME");

        AutomationWindow wndw = new AutomationWindow(element, window, container);

        String name = wndw.name();

        assertTrue(name.equals("NAME"));
    }

    @Test
    public void test_focus_Calls_setFocus_From_element() throws Exception {
        AutomationWindow wndw = new AutomationWindow(element, window, container);

        wndw.focus();

        verify(element, atLeastOnce()).setFocus();
    }

    @Test
    public void test_WaitForIdleInput_Calls_waitForInputIdle_From_Window() throws Exception {
        AutomationWindow wndw = new AutomationWindow(element, window, container);

        wndw.waitForInputIdle(100);

        verify(window, atLeastOnce()).waitForInputIdle(anyInt());
    }

    @Test
    public void test_isTopMost_Calls_isTopMost_From_Window() throws Exception {
        AutomationWindow wndw = new AutomationWindow(element, window, container);

        wndw.isTopMost();

        verify(window, atLeastOnce()).isTopMost();
    }

    @Test
    public void test_isModal_Calls_isModal_From_Window() throws Exception {
        AutomationWindow wndw = new AutomationWindow(element, window, container);

        wndw.isModal();

        verify(window, atLeastOnce()).isModal();
    }

    @Test(expected= Win32Exception.class)
    public void test_setTransparency_Throws_Exception_When_Win32_Calls_Throw_Error() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Object reference = (Object)args[0];

                reference = 1245;

                return 1234;
            }
        }).when(element).currentPropertyValue(anyInt());

        AutomationWindow wndw = new AutomationWindow(element, window, container);

        wndw.setTransparency(100);

        verify(element, atLeastOnce()).currentPropertyValue(anyInt());
    }

    @Test
    public void test_windowHandle_Calls_currentPropertyValue_From_Window() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Object reference = (Object)args[0];

                reference = 1245;

                return 1234;
            }
        }).when(element).currentPropertyValue(anyInt());

        AutomationWindow wndw = new AutomationWindow(element, window, container);

        WinDef.HWND handle = wndw.getNativeWindowHandle();

        verify(element, atLeastOnce()).currentPropertyValue(anyInt());
    }

    @Test(expected=AutomationException.class)
    @Ignore("Needs further work to make meaningful")
    public void test_windowHandle_Throws_Exception_When_currentPropertyValue_Returns_Error() throws Exception {

        IUIAutomationElement elem = Mockito.mock(IUIAutomationElement.class);

        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Object reference = (Object)args[0];

                reference = 1245;

                return 1;
            }
        }).when(elem).getCurrentPropertyValue(anyInt(), anyObject());

        AutomationElement localElement = Mockito.mock(AutomationElement.class);

        localElement.element = elem;

        AutomationWindow wndw = new AutomationWindow(localElement, window, container);

        WinDef.HWND handle = wndw.getNativeWindowHandle();

        verify(elem, atLeastOnce()).getCurrentPropertyValue(anyInt(), anyObject());
    }

    @Test
    public void test_setTransparency_Calls_currentPropertyValue_From_Window() throws Exception {

        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                return 1;
            }
        }).when(user32).SetWindowLong(anyObject(), anyInt(), anyInt());

        doAnswer(new Answer() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                return true;
            }
        }).when(user32).SetLayeredWindowAttributes(anyObject(), anyInt(), anyByte(), anyInt());

        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Object reference = (Object)args[0];

                reference = 1245;

                return 1234;
            }
        }).when(element).currentPropertyValue(anyInt());

        AutomationWindow wndw = new AutomationWindow(element, window, container, user32);

        wndw.setTransparency(100);

        verify(element, atLeastOnce()).currentPropertyValue(anyInt());
    }

    @Test(expected = Win32Exception.class)
    public void test_setTransparency_Throws_Exception_When_SetWindowLong_Returns_Error() throws Exception {

        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                return 0;
            }
        }).when(user32).SetWindowLong(anyObject(), anyInt(), anyInt());

        doAnswer(new Answer() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                return true;
            }
        }).when(user32).SetLayeredWindowAttributes(anyObject(), anyInt(), anyByte(), anyInt());

        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Object reference = (Object)args[0];

                reference = 1245;

                return 1234;
            }
        }).when(element).currentPropertyValue(anyInt());

        AutomationWindow wndw = new AutomationWindow(element, window, container, user32);

        wndw.setTransparency(100);

        verify(element, atLeastOnce()).currentPropertyValue(anyInt());
    }

    @Test(expected = Win32Exception.class)
    public void test_setTransparency_Throws_Exception_When_SetLayeredWindowAttributes_Returns_Error() throws Exception {

        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                return 1;
            }
        }).when(user32).SetWindowLong(anyObject(), anyInt(), anyInt());

        doAnswer(new Answer() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                return false;
            }
        }).when(user32).SetLayeredWindowAttributes(anyObject(), anyInt(), anyByte(), anyInt());

        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Object reference = (Object)args[0];

                reference = 1245;

                return 1234;
            }
        }).when(element).currentPropertyValue(anyInt());

        AutomationWindow wndw = new AutomationWindow(element, window, container, user32);

        wndw.setTransparency(100);

        verify(element, atLeastOnce()).currentPropertyValue(anyInt());
    }

    @Test
    public void test_GetRebar_By_Index() throws Exception {

        AutomationWindow wndw = new AutomationWindow(element, window, container, user32);

        AutomationReBar rebar = wndw.getReBar(0);

        verify(element, atLeastOnce()).findAll(anyObject(), anyObject());
    }

    @Test
    public void test_GetStatusBar_By_Index() throws Exception {

        AutomationWindow wndw = new AutomationWindow(element, window, container, user32);

        AutomationStatusBar bar = wndw.getStatusBar();

        verify(element, atLeastOnce()).findAll(anyObject(), anyObject());
    }

    @Test
    @Ignore("Better mocks required")
    public void test_GetAppBar_By_Index() throws Exception {

        AutomationWindow wndw = new AutomationWindow(element, window, container, user32);

        AutomationAppBar bar = wndw.getAppBar(0);

        verify(element, atLeastOnce()).findAll(anyObject(), anyObject());
    }

    @Test
    @Ignore("Better mocks required")
    public void test_GetTitleBar_By_Index() throws Exception {

        AutomationWindow wndw = new AutomationWindow(element, window, container, user32);

        AutomationTitleBar bar = wndw.getTitleBar();

        verify(element, atLeastOnce()).findAll(anyObject(), anyObject());
    }
}