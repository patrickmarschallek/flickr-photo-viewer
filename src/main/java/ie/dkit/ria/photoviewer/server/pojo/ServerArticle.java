package ie.dkit.ria.photoviewer.server.pojo;

import com.google.gson.Gson;
import ie.dkit.ria.photoviewer.shared.pojo.Article;

/**
 * Created by patte on 23.04.15.
 */
public class ServerArticle extends Article {

    public ServerArticle(String imageUrl, String title, String price, String detailUrl) {
        super(imageUrl, title, price, detailUrl);
    }

    public String toJson() {
        return (new Gson()).toJson(this);
    }
}
