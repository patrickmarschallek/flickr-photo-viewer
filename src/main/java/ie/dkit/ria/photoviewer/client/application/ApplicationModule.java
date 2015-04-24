package ie.dkit.ria.photoviewer.client.application;


import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import ie.dkit.ria.photoviewer.client.application.home.HomePresenter;
import ie.dkit.ria.photoviewer.client.application.home.HomeView;
import ie.dkit.ria.photoviewer.client.application.image.ImagePresenter;
import ie.dkit.ria.photoviewer.client.application.image.ImageView;

/**
 * @author Joshua Godi
 */
public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        // Main Application View
        bindPresenter(ApplicationPresenter.class,
                ApplicationPresenter.MyView.class,
                ApplicationView.class,
                ApplicationPresenter.MyProxy.class);

        // Home
        bindPresenter(HomePresenter.class,
                HomePresenter.MyView.class,
                HomeView.class,
                HomePresenter.MyProxy.class);

        //Image
        bindPresenter(ImagePresenter.class,
                ImagePresenter.MyView.class,
                ImageView.class,
                ImagePresenter.MyProxy.class);
    }
}
