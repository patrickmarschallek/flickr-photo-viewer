package ie.dkit.ria.photoviewer.client.application.image;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import ie.dkit.ria.photoviewer.client.application.ApplicationPresenter;
import ie.dkit.ria.photoviewer.client.common.Util;
import ie.dkit.ria.photoviewer.client.place.NameTokens;
import ie.dkit.ria.photoviewer.client.pojo.ClientArticle;
import ie.dkit.ria.photoviewer.client.service.amazon.AmazonService;
import ie.dkit.ria.photoviewer.shared.common.Constatnts;
import ie.dkit.ria.photoviewer.shared.pojo.ExifElement;
import ie.dkit.ria.photoviewer.shared.pojo.Image;

import java.util.ArrayList;
import java.util.List;

import static com.google.gwt.query.client.GQuery.console;

/**
 * Created by patte on 15.04.15.
 */
public class ImagePresenter extends Presenter<ImagePresenter.MyView, ImagePresenter.MyProxy> {

    private Image image;

    public interface MyView extends View {
        public void setPresenter(ImagePresenter presenter);

        public void setImageData(Image img);

        public void setExifData(List<ExifElement> exifMap);

        public void setAmazonData(List<ClientArticle> result);
    }

    @NameToken(NameTokens.IMAGE)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<ImagePresenter> {
    }

    @Inject
    public ImagePresenter(final EventBus eventBus,
                          final MyView view,
                          final MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);
        view.setPresenter(this);
//        eventBus.addHandler(SearchKeyEvent.TYPE, new SearchKeyEventHandlerImpl());

    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String id = request.getParameter("id", "");
        String secret = request.getParameter("secret", "");
        String server = request.getParameter("server", "");
        String farm = request.getParameter("farm", "");

        this.image = new Image(id, secret, server, farm);
        getView().setImageData(this.image);
        fetchExifData();
    }

    private void fetchAmazonData(String camera) {
        console.log(camera);
        AmazonService.App.getInstance().fetchItem(camera, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        console.log(caught);
                        Util.showError("Error", caught.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        List<ClientArticle> articles = new ArrayList<ClientArticle>();
                        JSONObject resultObj = JSONParser.parseLenient(result).isObject();
                        if (resultObj == null) {
                            Util.showError("Error", "wrong data from server.");
                        } else {
                            JSONString message = resultObj.get("result").isString();
                            if (null != message) {
                                Util.showWarning("Warning", message.stringValue());
                            } else {
                                JSONArray jsonArticles = resultObj.get("result").isArray();
                                int length = jsonArticles.size();
                                for (int i = 0; i < length; i++) {
                                    JSONObject jsonArticle = jsonArticles.get(i).isObject();
                                    articles.add(new ClientArticle(jsonArticle));
                                }
                                getView().setAmazonData(articles);
                            }
                        }

                    }
                }

        );
    }

    private void fetchExifData() {
        String apiKey = "d446d7aa0c715a33a1e2286087583aa0";
        String url = Util.format(Constatnts.FLICKR_EXIF_URL, apiKey, this.image.getId(), this.image.getSecret());
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
        try {
            builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    console.log(exception);
                    Util.showError("Error", exception.getMessage());
                }

                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()) {
                        List<ExifElement> exifMap = new ArrayList<ExifElement>();
                        JSONValue json = JSONParser.parseStrict(response.getText());
                        JSONObject responseObject = json.isObject();
                        JSONValue photosJson = responseObject.get("photo");
                        if (null != photosJson) {
                            JSONObject photosObj = photosJson.isObject();
                            image.setCamera(photosObj.get("camera").isString().toString());
                            JSONArray exifArray = photosObj.get("exif").isArray();
                            for (int i = 0; i < exifArray.size(); i++) {
                                JSONObject exifElement = exifArray.get(i).isObject();
                                String label = exifElement.get("label").isString().stringValue();
                                String value = exifElement.get("raw").isObject().get("_content").isString().stringValue();
                                exifMap.add(new ExifElement(label, value));
                            }
                        } else {
                            String errorCode = responseObject.get("code").toString();
                            String message = responseObject.get("message").isString().toString();
                            exifMap.add(new ExifElement(errorCode, message));
                        }
                        getView().setExifData(exifMap);
                        fetchAmazonData(image.getCamera());
                    } else {
                        Util.showError("Error", response.getText());
                    }
                }
            });
        } catch (RequestException e) {
            console.log(e);
            Util.showError("Error", e.getMessage());
        }
    }
}
