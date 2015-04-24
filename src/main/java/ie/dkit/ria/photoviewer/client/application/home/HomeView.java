package ie.dkit.ria.photoviewer.client.application.home;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import ie.dkit.ria.photoviewer.client.application.animation.WindowScrollAnimation;
import ie.dkit.ria.photoviewer.shared.pojo.Image;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.ImageType;

import java.util.List;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.window;

public class HomeView extends ViewImpl implements HomePresenter.MyView {

    private HomePresenter presenter;
    private List<Image> imageList;

    @UiField
    Button searchButton;

    @UiField
    TextBox search;

    @UiField
    Container searchResult;

    interface Binder extends UiBinder<Widget, HomeView> {
    }

    @Inject
    HomeView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("searchButton")
    void handleClickEvent(ClickEvent e) {
        this.searchResult.clear();
        this.searchResult.addStyleName("hide");
        presenter.onSearchButtonClicked(search.getText());
    }

    @UiHandler("search")
    void onKeyDown(KeyDownEvent e) {
        this.searchResult.clear();
        this.searchResult.addStyleName("hide");
        presenter.onKeyDown(e, search);
    }

    @Override
    public void onClick(ClickEvent event) {
        String id = $(event.getSource()).attr("id");
        Image selectedImage = this.imageList.get(Integer.valueOf(id.replace("img_", "")));
        this.presenter.onItemClicked(event, selectedImage);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        $("html,body").css("background", "url(\"images/background.jpg\") no-repeat #3e3f3a");
        this.search.clear();
        this.search.setFocus(true);
        this.searchResult.clear();
        this.searchResult.addStyleName("hide");
    }

    @Override
    public void setData(List<Image> data) {
        this.imageList = data;
        Row row = null;
        int i = 0;
        for (Image img : imageList) {
            if (i % 4 == 0)
                row = new Row();
            Column col = new Column(ColumnSize.SM_3);
            Anchor anchor = new Anchor();
            org.gwtbootstrap3.client.ui.Image htmlImage = new org.gwtbootstrap3.client.ui.Image(img.getUrl());
            htmlImage.addStyleName("img-responsive");
            htmlImage.setType(ImageType.THUMBNAIL);
            anchor.add(htmlImage);
            anchor.setId("img_" + i);
            anchor.addClickHandler(this);

            col.add(anchor);
            col.addStyleName("image-item");
            row.add(col);
            searchResult.add(row);
            i++;
        }
        Timer timer = new Timer() {
            public void run() {
                searchResult.removeStyleName("hide");
                final int elementTop = searchResult.getElement().getAbsoluteTop();
                int offset = 60;
                WindowScrollAnimation animation = new WindowScrollAnimation(window);
                animation.scrollTo(elementTop - offset, 1500);
            }
        };
        timer.schedule(1000);
    }

    @Override
    public void setPresenter(HomePresenter presenter) {
        this.presenter = presenter;
    }

}
