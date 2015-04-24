package ie.dkit.ria.photoviewer.client.service.amazon;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Created by patte on 21.04.15.
 */
@RemoteServiceRelativePath("AmazonService")
public interface AmazonService extends RemoteService {
    public String fetchItem(String s);

    /**
     * Utility/Convenience class.
     * Use EbayService.App.getInstance() to access static instance of EbayServiceAsync
     */
    public static class App {
        private static final AmazonServiceAsync ourInstance = (AmazonServiceAsync) GWT.create(AmazonService.class);

        public static AmazonServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
