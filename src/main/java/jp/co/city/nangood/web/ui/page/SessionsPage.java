package jp.co.city.nangood.web.ui.page;

import jabara.general.Empty;
import jabara.jpa.entity.EntityBase_;
import jabara.wicket.CssUtil;

import java.util.List;

import javax.inject.Inject;

import jp.co.city.nangood.entity.ESession;
import jp.co.city.nangood.entity.ESession_;
import jp.co.city.nangood.service.ISessionService;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 *
 */
@SuppressWarnings("synthetic-access")
public class SessionsPage extends WebPageBase {
    private static final long       serialVersionUID       = -4965903336608758671L;

    private static final int        DESCRIPTION_CHAR_COUNT = 100;

    @Inject
    private ISessionService         sessionService;

    private final List<ESession>    sessionsValue;

    private BookmarkablePageLink<?> goNewSession;
    private ListView<ESession>      sessions;

    /**
     * 
     */
    public SessionsPage() {
        this.sessionsValue = this.sessionService.getAll();
        this.add(getGoNewSession());
        this.add(getSessions());
    }

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);

        CssUtil.addComponentCssReference(pResponse, SessionsPage.class);
    }

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of("Top"); //$NON-NLS-1$
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
                    final ESession session = pItem.getModelObject();

                    pItem.setModel(new CompoundPropertyModel<>(session));

                    pItem.add(new Label(EntityBase_.id.getName()));
                    pItem.add(new Label(ESession_.name.getName()));
                    pItem.add(new Label(ESession_.englishName.getName()));
                    pItem.add(new MultiLineLabel(ESession_.description.getName(), cut(session.getDescription(), DESCRIPTION_CHAR_COUNT)));

                    pItem.add(createGoNangoodLink(session));
                    pItem.add(createGoEditorLink(session));

                }

                private BookmarkablePageLink<Object> createGoEditorLink(final ESession pSession) {
                    final PageParameters params = SessionEditorPage.getIdParameter(pSession);
                    final BookmarkablePageLink<Object> link = new BookmarkablePageLink<>("goEditor", SessionEditorPage2.class, params); //$NON-NLS-1$
                    return link;
                }

                private BookmarkablePageLink<Object> createGoNangoodLink(final ESession pSession) {
                    final PageParameters params = NangoodPage.getSessionParameter(pSession);
                    final BookmarkablePageLink<Object> link = new BookmarkablePageLink<>("goNangood", NangoodPage.class, params); //$NON-NLS-1$
                    return link;
                }
            };
        }
        return this.sessions;
    }

    private static String cut(final String s, final int pMaxCharCount) {
        if (s == null) {
            return Empty.STRING;
        }
        if (s.length() < pMaxCharCount) {
            return s;
        }
        return s.substring(0, pMaxCharCount) + "..."; //$NON-NLS-1$
    }
}
