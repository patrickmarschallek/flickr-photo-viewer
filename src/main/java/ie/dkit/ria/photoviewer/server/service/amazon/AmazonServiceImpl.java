package ie.dkit.ria.photoviewer.server.service.amazon;

import com.amazon.advertising.api.AWSECommerceServiceStub;
import com.amazon.advertising.api.common.HmacSecurityHandler;
import com.amazon.webservices.awsecommerceservice._2011_08_01.*;
import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ie.dkit.ria.photoviewer.client.service.amazon.AmazonService;
import ie.dkit.ria.photoviewer.server.pojo.ServerArticle;
import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by patte on 21.04.15.
 */
public class AmazonServiceImpl extends RemoteServiceServlet implements AmazonService {

    private static final Logger logger = Logger.getLogger(AmazonServiceImpl.class.getCanonicalName());

    private static final String endpoint = "https://webservices.amazon.co.uk/onca/soap?Service=AWSECommerceService";
    private static final String accessKey = "AKIAILOFAI74ZBFVGQBQ";
    private static final String secretKey = "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";
    private static final String associateKey = "0321-7003-9725";


    @Override
    public String fetchItem(String s) {
        logger.debug("start fetching amazon data");
        List<ServerArticle> articles = new ArrayList<ServerArticle>();
        if (null != s && !s.isEmpty()) {
            try {
                AWSECommerceServiceStub client = new AWSECommerceServiceStub(endpoint);

                logger.debug("created amazon service client");
                this.addSecurityHeader(client);

                logger.debug("added amazon security header");
                ItemSearchDocument doc = ItemSearchDocument.Factory.newInstance();
                ItemSearchDocument.ItemSearch search = ItemSearchDocument.ItemSearch.Factory.newInstance();
                ItemSearchRequest request = search.addNewRequest();
                search.setAWSAccessKeyId(accessKey);
                search.setAssociateTag(associateKey);
                request.setKeywords(s);

                List<String> responseGroups = new ArrayList<String>();
                responseGroups.add("ItemAttributes");
                responseGroups.add("Images");
                responseGroups.add("Medium");
                request.setResponseGroupArray(responseGroups.toArray(new String[]{}));
                request.setSearchIndex("All");
                doc.setItemSearch(search);

                logger.debug("defined amazon service request");
                ItemSearchResponseDocument response = client.itemSearch(doc);

                logger.debug("fetched amazon service data in a response");
                ItemsDocument.Items[] itemsArray = response.getItemSearchResponse().getItemsArray();
                for (ItemsDocument.Items items : itemsArray) {
                    int length = items.sizeOfItemArray();
                    for (int j = 0; j < length; j++) {
                        ItemDocument.Item item = items.getItemArray(j);
                        String title = item.getItemAttributes().getTitle();
                        String detailUrl = item.getDetailPageURL();
                        Image smallImage = item.getSmallImage();
                        String imageUrl;
                        if (null != smallImage)
                            imageUrl = smallImage.getURL();
                        else
                            imageUrl = "http://gwtbootstrap3.github.io/gwtbootstrap3-demo/images/test.png";
                        String price = "";
                        Price priceObj = item.getItemAttributes().getListPrice();
                        if (null != priceObj) {
                            price = priceObj.getFormattedPrice();
                        }
                        ServerArticle article = new ServerArticle(imageUrl, title, price, detailUrl);
                        articles.add(article);
                    }
                }
            } catch (RemoteException e) {
                logger.warn(e);
            } catch (Exception e) {
                logger.warn(e);
            }
            logger.info("Build json response for amazon");
            String json = "{\"result\":" + (new Gson()).toJson(articles) + "}";
            return json;
        } else {
            logger.info("No Camera in EXIF data");
            return "{\"result\":\"No Camera in EXIF data\"}";
        }


    }

    private void addSecurityHeader(AWSECommerceServiceStub client) throws Exception {
        String namespace = "http://security.amazonaws.com/doc/2007-01-01/";

        HmacSecurityHandler handler = new HmacSecurityHandler(accessKey, secretKey);
        handler.init();

        String time = handler.getTimestamp();

        client._getServiceClient().addStringHeader(
                new QName(namespace, "AWSAccessKeyId"),
                accessKey);
        client._getServiceClient().addStringHeader(
                new QName(namespace, "Timestamp"),
                time);
        client._getServiceClient().addStringHeader(
                new QName(namespace, "Signature"),
                handler.calculateSignature("ItemSearch", time));

    }
}