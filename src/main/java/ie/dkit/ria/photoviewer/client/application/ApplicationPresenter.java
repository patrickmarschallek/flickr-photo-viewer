package ie.dkit.ria.photoviewer.client.application;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import ie.dkit.ria.photoviewer.events.scroll.WindowScrollEvent;
import ie.dkit.ria.photoviewer.events.scroll.WindowScrollEventHandlerImpl;

/**
 * @author Joshua Godi
 */
public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy> {

    @ProxyStandard
    public interface MyProxy extends Proxy<ApplicationPresenter> {
    }

    public interface MyView extends View, Window.ScrollHandler {
        void setPresenter(ApplicationPresenter presenter);
    }

    /**
     * Use this in leaf presenters, inside their {@link #revealInParent} method.
     */
    @ContentSlot
    public static final GwtEvent.Type<RevealContentHandler<?>> TYPE_SetMainContent = new GwtEvent.Type<RevealContentHandler<?>>();

    @Inject
    ApplicationPresenter(final EventBus eventBus,
                         final MyView view,
                         final MyProxy proxy) {
        super(eventBus, view, proxy, RevealType.Root);

        // Making the window scroll to top on every page change
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                Scheduler.get().scheduleDeferred(new Command() {
                    @Override
                    public void execute() {
                        Window.scrollTo(0, 0);
                    }
                });
            }
        });
        view.setPresenter(this);
        eventBus.addHandler(WindowScrollEvent.TYPE, new WindowScrollEventHandlerImpl());
    }

    public void onWindowScroll(Window.ScrollEvent event, Widget toTopButton) {
        getEventBus().fireEvent(new WindowScrollEvent(event, toTopButton));
    }
}