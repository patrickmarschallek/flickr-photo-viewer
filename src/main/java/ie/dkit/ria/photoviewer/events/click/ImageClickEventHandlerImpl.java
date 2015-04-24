package ie.dkit.ria.photoviewer.events.click;


import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import ie.dkit.ria.photoviewer.client.place.NameTokens;
import ie.dkit.ria.photoviewer.shared.pojo.Image;

/**
 * Created by patte on 14.04.15.
 */
public class ImageClickEventHandlerImpl implements ImageClickedEventHandler {

    @Override
    public void onImageClickEvent(ImageClickedEvent e) {
        Image img = e.getImage();
        PlaceRequest request = new PlaceRequest.Builder().
                nameToken(NameTokens.getImage())
                .with("id", img.getId())
                .with("secret", img.getSecret())
                .with("server", img.getServer())
                .with("farm", img.getFarm())
                .build();
        e.getPlaceManager().revealPlace(request);
    }

}
