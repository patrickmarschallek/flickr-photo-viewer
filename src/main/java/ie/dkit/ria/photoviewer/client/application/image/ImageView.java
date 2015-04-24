package ie.dkit.ria.photoviewer.client.application.image;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import ie.dkit.ria.photoviewer.client.application.template.ArticleView;
import ie.dkit.ria.photoviewer.client.pojo.ClientArticle;
import ie.dkit.ria.photoviewer.shared.pojo.ExifElement;
import ie.dkit.ria.photoviewer.shared.pojo.Image;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Pagination;
import org.gwtbootstrap3.client.ui.constants.ImageType;
import org.gwtbootstrap3.client.ui.gwt.CellTable;

import java.util.List;

import static com.google.gwt.query.client.GQuery.$;

/**
 * Created by patte on 15.04.15.
 */
public class ImageView extends ViewImpl implements ImagePresenter.MyView {

    private ImagePresenter presenter;
    private Image image;

    @UiField
    org.gwtbootstrap3.client.ui.Image imageElement;

    @UiField
    Column exifContainer;

    @UiField
    Column amazonContainer;

    interface Binder extends UiBinder<Widget, ImageView> {
    }

    @Inject
    ImageView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        $("html,body").css("background-image", "none").css("background-color", "#DDDDDD");
        this.amazonContainer.addStyleName("hide");
        this.exifContainer.addStyleName("hide");
        this.amazonContainer.clear();
        this.exifContainer.clear();
    }

    @Override
    public void setPresenter(ImagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setImageData(Image img) {
        this.image = img;
        imageElement.setUrl(this.image.getUrl(Image.Type.MEDIUM_640));
        imageElement.setType(ImageType.THUMBNAIL);
    }

    @Override
    public void setExifData(List<ExifElement> exifList) {
        CellTable<ExifElement> table = new CellTable<ExifElement>();
        final Pagination pagination = new Pagination();
        // Create a SimplePager.
        final SimplePager pager = new SimplePager();

        // Create name column.
        TextColumn<ExifElement> tagColumn = new TextColumn<ExifElement>() {
            @Override
            public String getValue(ExifElement exifElement) {
                return exifElement.getTag();
            }
        };

        // Create address column.
        TextColumn<ExifElement> valueColumn = new TextColumn<ExifElement>() {
            @Override
            public String getValue(ExifElement exifElement) {
                return exifElement.getValue();
            }
        };

        // Create a data provider.
        ListDataProvider<ExifElement> dataProvider = new ListDataProvider<ExifElement>(exifList);

        // Connect the table to the data provider.
        dataProvider.addDataDisplay(table);

        // Add the columns.
        table.addColumn(tagColumn, "Exif-Tag");
        table.addColumn(valueColumn, "Value");
        // Set the total row count. This isn't strictly necessary, but it affects
        // paging calculations, so its good habit to keep the row count up to date.
        table.setRowCount(exifList.size(), true);

        // Push the data into the widget.
        table.setRowData(0, exifList);

        // Set the cellList as the display.
        pager.setDisplay(table);

        // Adding / Removing data
        pagination.rebuild(pager);

        // Setting the RangeChangeHandler
        table.addRangeChangeHandler(new RangeChangeEvent.Handler() {
            @Override
            public void onRangeChange(RangeChangeEvent event) {
                pagination.rebuild(pager);
            }
        });
        exifContainer.removeStyleName("hide");
        exifContainer.clear();
        exifContainer.add(table);
        exifContainer.add(pagination);
    }

    @Override
    public void setAmazonData(List<ClientArticle> result) {
        VerticalPanel row = new VerticalPanel();
        for (ClientArticle a : result) {
            ArticleView box = new ArticleView(a);
            row.add(box);
        }
        amazonContainer.removeStyleName("hide");
        amazonContainer.add(row);
    }

}