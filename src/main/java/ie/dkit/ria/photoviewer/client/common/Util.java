package ie.dkit.ria.photoviewer.client.common;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.extras.growl.client.ui.Growl;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlOptions;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlPosition;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlType;

/**
 * Created by patte on 15.04.15.
 */
public class Util {

    private static final Util instance = new Util();

    public static Util getInsatnce() {
        return instance;
    }

    //    public static native String format(String format, JsArrayMixed values) /*-{
//        return vsprintf(format, values);
//    }-*/;

    public static String format(final String format, final String... args) {
        String[] split = format.split("%s");
        final StringBuffer msg = new StringBuffer();
        for (int pos = 0; pos < split.length - 1; pos += 1) {
            msg.append(split[pos]);
            msg.append(args[pos]);
        }
        msg.append(split[split.length - 1]);
        return msg.toString();
    }

    public static void showError(String title, String message) {
        GrowlOptions go = new GrowlOptions();
        go.setType(GrowlType.DANGER);
        go.setPosition(GrowlPosition.TOP_CENTER);
        go.setAllowDismiss(false);
        Growl.growl(title, message, IconType.WARNING, go);
    }

    public static void showWarning(String warning, String message) {
        GrowlOptions go = new GrowlOptions();
        go.setType(GrowlType.WARNING);
        go.setPosition(GrowlPosition.TOP_CENTER);
        go.setAllowDismiss(false);
        Growl.growl(warning, message, IconType.WARNING, go);
    }

//    public Properties getProperties() throws IOException {
//        Properties prop = new Properties();
//        String propFileName = "config.properties";
//
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
//
//        if (inputStream != null) {
//            prop.load(inputStream);
//        } else {
//            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
//        }
//
//        return prop;
//    }
}
