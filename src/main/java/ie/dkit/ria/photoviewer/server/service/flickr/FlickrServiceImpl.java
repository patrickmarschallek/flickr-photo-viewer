package ie.dkit.ria.photoviewer.server.service.flickr;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ie.dkit.ria.photoviewer.client.common.Util;
import ie.dkit.ria.photoviewer.client.service.flickr.FlickrService;
import ie.dkit.ria.photoviewer.shared.common.Constatnts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by patte on 14.04.15.
 */
public class FlickrServiceImpl extends RemoteServiceServlet implements
        FlickrService {
    private static final Logger logger = Logger.getLogger(FlickrServiceImpl.class.getCanonicalName());

    @Override
    public String fetchImages(String searchTerm) {
        String result = "";
        String apiKey = "d446d7aa0c715a33a1e2286087583aa0";

        logger.debug("started flickr service");
        System.out.println(Constatnts.FLICKR_IMAGE_URL);
        CloseableHttpResponse response = null;
        try {
            searchTerm = URLEncoder.encode(searchTerm, "UTF-8");
            String url = Util.format(Constatnts.FLICKR_IMAGE_URL, apiKey, searchTerm);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            logger.debug("flickr request created");
            response = httpclient.execute(httpGet);

            logger.debug("flickr response recieved");
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                result = entity != null ? EntityUtils.toString(entity) : null;
            } else {
                result = "Unexpected response status: " + status;
            }
        } catch (IOException e) {
            logger.warn(e);
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                logger.warn(e);
            }
        }

        return result;
    }
}
