package ie.dkit.ria.photoviewer.client.application.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import ie.dkit.ria.photoviewer.client.pojo.ClientArticle;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Image;

/**
 * Created by patte on 24.04.15.
 */
public class ArticleView extends Composite {
    private ClientArticle article;

    interface MyUiBinder extends UiBinder<Widget, ArticleView> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    Image articleImage;
    @UiField
    Column articleTitle;
    @UiField
    Column articlePrice;

    public ArticleView(ClientArticle article) {
        initWidget(uiBinder.createAndBindUi(this));
        this.article = article;
        articleImage.setUrl(article.getImageUrl());
        articleTitle.getElement().setInnerHTML(article.getName());
        articlePrice.getElement().setInnerHTML(article.getPrice());
    }
}
