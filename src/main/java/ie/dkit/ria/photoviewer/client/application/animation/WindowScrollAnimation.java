package ie.dkit.ria.photoviewer.client.application.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;

import static com.google.gwt.query.client.GQuery.$;

/**
 * Created by patte on 24.04.15.
 */
public class WindowScrollAnimation extends Animation {
    private final Element element;
    private int startY;
    private int finalY;

    public WindowScrollAnimation(Element element) {
        this.element = element;
    }

    public void scrollTo(int y, int milliseconds) {
        this.finalY = y;
        startY = element.getScrollTop();
        run(milliseconds);
    }

    public void scrollDown(double progress) {
        $(this.element).scrollTop((int) ((this.finalY + 1) * progress));
    }

    public void scrollUp(double progress) {
        $(this.element).scrollTop((int) (this.startY - ((this.startY) * progress)));
    }

    @Override
    protected void onUpdate(double progress) {
        if (this.startY > this.finalY) {
            this.scrollUp(progress);
        } else {
            this.scrollDown(progress);
        }
    }

    @Override
    protected void onComplete() {
        super.onComplete();
        this.element.setScrollTop(this.finalY);
    }
}
