package ie.dkit.ria.photoviewer.events.scroll;

/**
 * Created by patte on 14.04.15.
 */
public class WindowScrollEventHandlerImpl implements WindowScrollEventHandler {
    @Override
    public void onWindowScroll(WindowScrollEvent e) {
        if (e.getInnerEvent().getScrollTop() > 100) {
            e.getToTopButton().removeStyleName("hide");
        } else {
            e.getToTopButton().addStyleName("hide");
        }
    }
}
