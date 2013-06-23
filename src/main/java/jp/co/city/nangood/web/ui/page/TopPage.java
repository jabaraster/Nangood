package jp.co.city.nangood.web.ui.page;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import jp.co.city.nangood.entity.ESession;
import jp.co.city.nangood.service.ISessionService;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 *
 */
@SuppressWarnings("synthetic-access")
public class TopPage extends RestrictedPageBase {
    private static final long       serialVersionUID = -4965903336608758671L;

    private final Handler           handler          = new Handler();

    @Inject
    private ISessionService         sessionService;

    private final List<ESession>    sessionsValue;

    private BookmarkablePageLink<?> goNewSession;
    private ListView<ESession>      sessions;
    private Link<?>                 goLogout;

    /**
     * 
     */
    public TopPage() {
        this.sessionsValue = this.sessionService.getAll();
        this.add(getGoNewSession());
        this.add(getSessions());
        this.add(getGoLogout());
    }

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of("Top"); //$NON-NLS-1$
    }

    private Link<?> getGoLogout() {
        if (this.goLogout == null) {
            this.goLogout = new BookmarkablePageLink<>("goLogout", LogoutPage.class); //$NON-NLS-1$
        }
        return this.goLogout;
    }

    private BookmarkablePageLink<?> getGoNewSession() {
        if (this.goNewSession == null) {
            this.goNewSession = new BookmarkablePageLink<>("goNewSession", SessionEditorPage.class); //$NON-NLS-1$
        }
        return this.goNewSession;
    }

    @SuppressWarnings("serial")
    private ListView<ESession> getSessions() {
        if (this.sessions == null) {
            this.sessions = new ListView<ESession>("sessions", this.sessionsValue) { //$NON-NLS-1$
                @Override
                protected void populateItem(final ListItem<ESession> pItem) {
                    pItem.setModel(new CompoundPropertyModel<>(pItem.getModelObject()));
                    pItem.add(new Label("id")); //$NON-NLS-1$
                    pItem.add(new Label("name")); //$NON-NLS-1$
                    pItem.add(new Label("englishName")); //$NON-NLS-1$
                }
            };
        }
        return this.sessions;
    }

    private class Handler implements Serializable {
        private static final long serialVersionUID = 8826180320287426527L;

    }
}
