package ie.dkit.ria.photoviewer.shared.pojo;

import com.google.gwt.core.client.JsArrayMixed;
import ie.dkit.ria.photoviewer.client.common.Util;

import java.util.HashMap;

/**
 * Created by patte on 14.04.15.
 */
public class Image {

    private static final String URL = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";

    public Image() {

    }

    public Image(String id, String secret, String server, String farm) {
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
    }

    public enum Type {
        SMALL_SQUARE("s"),  //klein, quadratisch, 75 x 75
        LARGE_SQUARE("q"),  //large square 150x150
        THUMBNAIL("t"),     //Thumbnail, 100 an der Längsseite
        SMALL_240("m"),     //klein, 240 an der Längsseite
        SMALL_320("n"),     //small, 320 on longest side
        MEDIUM_500("-"),    //mittel, 500 an der Längsseite
        MEDIUM_640("z"),    //mittel 640, 640 an der längsten Seite
        MEDIUM_800("c"),    //mittel 800, 800 an der längsten Seite†
        LARGE_1024("b"),    //groß, 1024 an der längsten Seite*
        LARGE_1600("h"),    //groß mit 1600 Pixel, 1600 an längster Seite†
        LARGE_2048("k"),    //groß mit 2048 Pixel, 2048 an längster Seite†
        ORIGINAL("o");      //Originalbild, entweder JPG, GIF oder PNG, je nach Quellformat

        private final String name;

        private Type(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        public String toString() {
            return name;
        }
    }

    private String id;
    private String secret;
    private String server;
    private String farm;
    private String title;
    private String camera;
    private HashMap<String, String> exif = new HashMap<String, String>();

    public Image(String id, String secret, String server, String farm, String title) {
        this(id, secret, server, farm);
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public Image setId(String id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
//        JsArrayMixed params = JsArrayMixed.createArray().cast();
//        params.push();
//        params.push();
//        params.push();
//        params.push();
//        params.push();
        return Util.format(URL, this.farm, this.server, this.id, this.secret, Type.SMALL_240.toString());
    }

    public String getUrl(Type size) {
        JsArrayMixed params = JsArrayMixed.createArray().cast();
        params.push(this.farm);
        params.push(this.server);
        params.push(this.id);
        params.push(this.secret);
        params.push(size.toString());
        return Util.format(URL, this.farm, this.server, this.id, this.secret, size.toString());
    }

    public HashMap<String, String> getExif() {
        return exif;
    }

    public Image setExif(HashMap<String, String> exif) {
        this.exif = exif;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Image setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public Image setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public String getServer() {
        return server;
    }

    public Image setServer(String server) {
        this.server = server;
        return this;
    }

    public String getFarm() {
        return farm;
    }

    public Image setFarm(String farm) {
        this.farm = farm;
        return this;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getCamera() {
        return camera;
    }
}
