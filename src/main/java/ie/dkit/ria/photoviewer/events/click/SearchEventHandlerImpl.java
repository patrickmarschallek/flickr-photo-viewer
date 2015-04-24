package ie.dkit.ria.photoviewer.events.click;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import ie.dkit.ria.photoviewer.client.application.home.HomePresenter;
import ie.dkit.ria.photoviewer.client.common.Util;
import ie.dkit.ria.photoviewer.client.service.flickr.FlickrService;
import ie.dkit.ria.photoviewer.shared.pojo.Image;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by patte on 14.04.15.
 */
public class SearchEventHandlerImpl implements SearchEventHandler {

    @Override
    public void onClick(final SearchEvent e) {
        FlickrService.App.getInstance().fetchImages(e.getSearchString(), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                Util.showError("Error", caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                List<Image> images = new ArrayList<Image>();
                if (!result.isEmpty()) {
                    JSONValue json = JSONParser.parseStrict(result);
                    JSONValue photosJson = json.isObject().get("photos");
                    if (photosJson != null) {
                        JSONObject object = json.isObject().get("photos").isObject();
                        JSONArray imgList = object.get("photo").isArray();
                        for (int i = 0; i < imgList.size(); i++) {
                            Image img = new Image();

                            JSONObject jsonImg = imgList.get(i).isObject();
                            img.setId(jsonImg.get("id").isString().stringValue())
                                    .setServer(jsonImg.get("server").isString().stringValue())
                                    .setSecret(jsonImg.get("secret").isString().stringValue())
                                    .setFarm(jsonImg.get("farm").isNumber().toString())
                                    .setTitle(jsonImg.get("title").isString().stringValue());
                            images.add(img);
                        }
                        ((HomePresenter.MyView) e.getView()).setData(images);
                    } else {
                        String messag = json.isObject().get("message").isString().toString();
                        Util.showError("Error", messag);
                    }
                } else {
                    Util.showError("Error", "TODO error message");
                }
            }
        });
    }
}
