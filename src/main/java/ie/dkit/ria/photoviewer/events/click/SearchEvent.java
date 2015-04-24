package ie.dkit.ria.photoviewer.events.click;

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
import com.gwtplatform.mvp.client.View;

/**
 * Created by patte on 14.04.15.
 */
public class SearchEvent extends GwtEvent<SearchEventHandler> {

    public final static Type<SearchEventHandler> TYPE = new Type<SearchEventHandler>();

    private String searchString;
    private View view;

    public SearchEvent(String s, View v) {
        this.searchString = s;
        this.view = v;
    }

    @Override
    public Type<SearchEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SearchEventHandler handler) {
        handler.onClick(this);
    }

    public String getSearchString() {
        return searchString;
    }

    public View getView() {
        return view;
    }

}
