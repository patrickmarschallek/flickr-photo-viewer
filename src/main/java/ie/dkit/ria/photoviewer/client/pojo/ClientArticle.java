package ie.dkit.ria.photoviewer.client.pojo;

import com.google.gwt.json.client.JSONObject;
import ie.dkit.ria.photoviewer.shared.pojo.Article;

/**
 * Created by patte on 24.04.15.
 */
public class ClientArticle extends Article {
    public ClientArticle(JSONObject jsonArticle) {
        this.imageUrl = jsonArticle.get(IMAGE_URL).isString().stringValue();
        this.name = jsonArticle.get(NAME).isString().stringValue();
        this.price = jsonArticle.get(PRICE).isString().stringValue();
        this.detailPageUrl = jsonArticle.get(DETAIL_PAGE_URL).isString().stringValue();
    }
}
