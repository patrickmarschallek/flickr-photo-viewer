package ie.dkit.ria.photoviewer.client.application.home;

/*
 * #%L
 * GwtBootstrap3
 * %%
 * Copyright (C) 2013 GwtBootstrap3
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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import ie.dkit.ria.photoviewer.client.application.ApplicationPresenter;
import ie.dkit.ria.photoviewer.client.place.NameTokens;
import ie.dkit.ria.photoviewer.events.click.ImageClickEventHandlerImpl;
import ie.dkit.ria.photoviewer.events.click.ImageClickedEvent;
import ie.dkit.ria.photoviewer.events.click.SearchEvent;
import ie.dkit.ria.photoviewer.events.click.SearchEventHandlerImpl;
import ie.dkit.ria.photoviewer.events.key.SearchKeyEvent;
import ie.dkit.ria.photoviewer.events.key.SearchKeyEventHandlerImpl;
import ie.dkit.ria.photoviewer.shared.pojo.Image;
import org.gwtbootstrap3.client.ui.TextBox;

import java.util.List;


public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> {

    private final PlaceManager placeManager;

    public interface MyView extends View, ClickHandler {
        void setData(List<Image> data);

        void setPresenter(HomePresenter presenter);

        Widget asWidget();
    }

    @NameToken(NameTokens.HOME)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<HomePresenter> {
    }

    @Inject
    public HomePresenter(final EventBus eventBus,
                         final MyView view,
                         final MyProxy proxy,
                         PlaceManager placeManager) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);
        view.setPresenter(this);
        this.placeManager = placeManager;

        eventBus.addHandler(SearchKeyEvent.TYPE, new SearchKeyEventHandlerImpl());
        eventBus.addHandler(SearchEvent.TYPE, new SearchEventHandlerImpl());
        eventBus.addHandler(ImageClickedEvent.TYPE, new ImageClickEventHandlerImpl());
    }

    public void onSearchButtonClicked(String search) {
        getEventBus().fireEvent(new SearchEvent(search, getView()));
    }

    public void onItemClicked(ClickEvent e, Image selectedImage) {
        getEventBus().fireEvent(new ImageClickedEvent(e, selectedImage, this.placeManager));
    }

    public void onKeyDown(KeyDownEvent e, TextBox search) {
        getEventBus().fireEvent(new SearchKeyEvent(e, search, getEventBus(), getView()));
    }
}
