package ie.dkit.ria.photoviewer.client.application;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import ie.dkit.ria.photoviewer.client.application.animation.WindowScrollAnimation;
import org.gwtbootstrap3.client.ui.NavbarBrand;

import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;

/**
 * @author Joshua Godi
 */
public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {

    @UiField
    SimplePanel contentContainer;

    @UiField
    NavbarBrand brand;
    @UiField
    com.google.gwt.user.client.ui.Anchor toTopButton;


    private ApplicationPresenter presenter;

    @Override
    public void setPresenter(ApplicationPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onWindowScroll(Window.ScrollEvent event) {
        this.presenter.onWindowScroll(event, this.toTopButton);
    }

    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @Inject
    ApplicationView(final Binder binder) {
        initWidget(binder.createAndBindUi(this));
        Window.addWindowScrollHandler(this);
    }

    @UiHandler("toTopButton")
    public void handleClick(ClickEvent event) {
        WindowScrollAnimation animation = new WindowScrollAnimation(window);
        animation.scrollTo(0, 1500);
    }

    @Override
    public void setInSlot(final Object slot, final IsWidget content) {
        if (slot == ApplicationPresenter.TYPE_SetMainContent) {
            contentContainer.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }
}