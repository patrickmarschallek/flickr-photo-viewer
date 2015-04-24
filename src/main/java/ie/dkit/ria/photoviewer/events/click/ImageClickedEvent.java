package ie.dkit.ria.photoviewer.events.click;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import ie.dkit.ria.photoviewer.shared.pojo.Image;

/**
 * Created by patte on 14.04.15.
 */
public class ImageClickedEvent extends GwtEvent<ImageClickedEventHandler> {

    public final static Type<ImageClickedEventHandler> TYPE = new Type<ImageClickedEventHandler>();
    private final Image image;

    private PlaceManager placeManager;

    public ImageClickedEvent(ClickEvent e, Image selectedImage, PlaceManager placeManager) {
        super();
        this.setSource(e.getSource());
        this.placeManager = placeManager;
        this.image = selectedImage;
    }

    @Override
    public Type<ImageClickedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ImageClickedEventHandler handler) {
        handler.onImageClickEvent(this);
    }

    public PlaceManager getPlaceManager() {
        return placeManager;
    }

    public Image getImage() {
        return image;
    }
}
