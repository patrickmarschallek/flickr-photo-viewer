package ie.dkit.ria.photoviewer.shared.pojo;

/**
 * Created by patte on 23.04.15.
 */
public class Article {
    protected static final String IMAGE_URL = "imageUrl";
    protected static final String DETAIL_PAGE_URL = "detailPageUrl";
    protected static final String NAME = "name";
    protected static final String PRICE = "price";

    protected String imageUrl;
    protected String name;
    protected String price;
    protected String detailPageUrl;

    public Article() {
    }

    public Article(String imageUrl, String name, String price, String detailPageUrl) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.detailPageUrl = detailPageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Article setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getName() {
        return name;
    }

    public Article setName(String name) {
        this.name = name;
        return this;
    }


    public String getPrice() {
        return price;
    }

    public Article setPrice(String price) {
        this.price = price;
        return this;
    }


    public String getDetailPageUrl() {
        return detailPageUrl;
    }

    public Article setDetailPageUrl(String detailPageUrl) {
        this.detailPageUrl = detailPageUrl;
        return this;
    }
}
