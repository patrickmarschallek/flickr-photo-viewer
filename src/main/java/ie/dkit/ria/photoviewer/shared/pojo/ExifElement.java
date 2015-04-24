package ie.dkit.ria.photoviewer.shared.pojo;

/**
 * Created by patte on 16.04.15.
 */
public class ExifElement {
    private String tag;
    private String value;

    public ExifElement(String tag, String value) {
        this.tag = tag;
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
