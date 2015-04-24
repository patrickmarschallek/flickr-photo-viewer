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


import com.google.gwt.event.dom.client.KeyCodes;
import ie.dkit.ria.photoviewer.events.click.SearchEvent;

/**
 * Created by patte on 15.04.15.
 */
public class SearchKeyEventHandlerImpl implements SearchKeyEventHandler {

    @Override
    public void onKeyDown(SearchKeyEvent e) {
        if (e.getInnerEvent().getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            String searchTerm = e.getSearchTerm().getText();
            e.getEventBus().fireEvent(new SearchEvent(searchTerm, e.getView()));
        }
    }
}
