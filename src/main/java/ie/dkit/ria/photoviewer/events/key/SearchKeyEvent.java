package ie.dkit.ria.photoviewer.events.key;

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

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.EventBus;
import ie.dkit.ria.photoviewer.client.application.home.HomePresenter;
import org.gwtbootstrap3.client.ui.TextBox;

/**
 * Created by patte on 15.04.15.
 */
public class SearchKeyEvent extends GwtEvent<SearchKeyEventHandler> {
    public static final GwtEvent.Type<SearchKeyEventHandler> TYPE = new GwtEvent.Type<SearchKeyEventHandler>();

    private final KeyDownEvent innerEvent;
    private final EventBus eventBus;
    private final HomePresenter.MyView view;
    private final TextBox searchTerm;

    public SearchKeyEvent(KeyDownEvent e, TextBox search, EventBus eventBus, HomePresenter.MyView view) {
        this.innerEvent = e;
        this.eventBus = eventBus;
        this.view = view;
        this.searchTerm = search;
    }

    @Override
    public Type<SearchKeyEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SearchKeyEventHandler handler) {
        handler.onKeyDown(this);
    }

    public KeyDownEvent getInnerEvent() {
        return innerEvent;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public HomePresenter.MyView getView() {
        return view;
    }

    public TextBox getSearchTerm() {
        return searchTerm;
    }
}
