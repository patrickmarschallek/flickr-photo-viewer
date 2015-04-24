package ie.dkit.ria.photoviewer.events.scroll;

/*
 * #%L
 * GwtBootstrap3
 * %%
 * Copyright (C) 2013 - 2015 GwtBootstrap3
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by patte on 14.04.15.
 */
public class WindowScrollEvent extends GwtEvent<WindowScrollEventHandler> {
    public static final Type<WindowScrollEventHandler> TYPE = new Type<WindowScrollEventHandler>();
    private final Window.ScrollEvent innerEvent;
    private Widget toTopButton;

    public WindowScrollEvent(Window.ScrollEvent event, Widget toTopButton) {
        this.toTopButton = toTopButton;
        this.innerEvent = event;
    }


    @Override
    public Type<WindowScrollEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(WindowScrollEventHandler handler) {
        handler.onWindowScroll(this);
    }

    public Window.ScrollEvent getInnerEvent() {
        return innerEvent;
    }

    public Widget getToTopButton() {
        return toTopButton;
    }

    public void setToTopButton(Widget toTopButton) {
        this.toTopButton = toTopButton;
    }
}
