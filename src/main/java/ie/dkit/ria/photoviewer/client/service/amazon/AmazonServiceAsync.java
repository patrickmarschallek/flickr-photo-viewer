package ie.dkit.ria.photoviewer.client.service.amazon;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by patte on 21.04.15.
 */
public interface AmazonServiceAsync {
    public void fetchItem(String s, AsyncCallback<String> callback);
}
