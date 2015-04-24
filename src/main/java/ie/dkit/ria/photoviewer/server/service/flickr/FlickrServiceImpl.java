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

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by patte on 14.04.15.
 */
public class FlickrServiceImpl extends RemoteServiceServlet implements
        FlickrService {

    @Override
    public String fetchImages(String searchTerm) {
        String result = "";
        String apiKey = "d446d7aa0c715a33a1e2286087583aa0";
        System.out.println(Constatnts.FLICKR_IMAGE_URL);
        CloseableHttpResponse response = null;
        try {
            searchTerm = URLEncoder.encode(searchTerm, "UTF-8");
            String url = Util.format(Constatnts.FLICKR_IMAGE_URL, apiKey, searchTerm);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            System.out.println(url);
            response = httpclient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                result = entity != null ? EntityUtils.toString(entity) : null;
            } else {
                result = "Unexpected response status: " + status;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
