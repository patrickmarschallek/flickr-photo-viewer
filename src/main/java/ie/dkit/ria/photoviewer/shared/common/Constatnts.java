package ie.dkit.ria.photoviewer.shared.common;

/**
 * Created by patte on 16.04.15.
 */
public class Constatnts {
    public static final String FLICKR_EXIF_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.getExif&api_key=%s&photo_id=%s&secret=%s&format=json&nojsoncallback=1";
    public static final String FLICKR_IMAGE_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=%s&format=json&tags=%s&nojsoncallback=1&privacy_filter=1&content_type=1";


}
