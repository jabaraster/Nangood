/**
 * 
 */
package jp.co.city.nangood.web.ui.page;

import jabara.general.ArgUtil;
import jabara.general.Empty;
import jabara.general.NotFound;
import jabara.wicket.CssUtil;
import jabara.wicket.JavaScriptUtil;

import javax.inject.Inject;

import jp.co.city.nangood.entity.ESession;
import jp.co.city.nangood.service.ISessionService;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * @author jabaraster
 */
public class NangoodPage extends WebPageBase {
    private static final long serialVersionUID = 4409409145136534961L;

    @Inject
    private ISessionService   sessionService;

    private ESession          sessionValue;

    /**
     * @param pParameters -
     */
    public NangoodPage(final PageParameters pParameters) {
        final String sessionNameInEnglish = n(pParameters).get(0).toString(Empty.STRING);
        try {
            this.sessionValue = this.sessionService.findByEnglishName(sessionNameInEnglish);
            initialize();
        } catch (final NotFound e) {
            throw new RestartResponseException(SessionsPage.class);
        }
    }

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
<<<<<<< HEAD

        pResponse.render(StringHeaderItem.forString("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />")); //$NON-NLS-1$

        JavaScriptUtil.addJQuery1_9_1Reference(pResponse);
        pResponse.render(CssHeaderItem.forReference(new CssResourceReference(NangoodPage.class, "jquery.mobile-1.3.1.min.css"))); //$NON-NLS-1$
        pResponse.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(NangoodPage.class, "jquery.mobile-1.3.1.min.js"))); //$NON-NLS-1$

        CssUtil.addComponentCssReference(pResponse, NangoodPage.class);
=======
        CssUtil.addComponentCssReference(pResponse, NangoodPage.class);
        pResponse.render(JavaScriptHeaderItem.forUrl("http://code.jquery.com/jquery-1.9.1.min.js")); //$NON-NLS-1$
        pResponse.render(JavaScriptHeaderItem.forUrl("http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js")); //$NON-NLS-1$
        // JavaScriptUtil.addJQuery1_9_1Reference(pResponse);
>>>>>>> FETCH_HEAD
        JavaScriptUtil.addComponentJavaScriptReference(pResponse, NangoodPage.class);

        pResponse.render(StringHeaderItem
                .forString("<meta name=\"viewport\" content=\"width=device-width, user-scalable=0, initial-scale=1.0, maximum-scale=1.0\" />")); //$NON-NLS-1$
    }

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @SuppressWarnings("serial")
    @Override
    protected IModel<String> getTitleLabelModel() {
        return new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return "共感したら\"ぐっど！\"を連打！"; //$NON-NLS-1$
            }
        };
    }

    private void initialize() {
        this.add(new Label("name", this.sessionValue.getName())); //$NON-NLS-1$
    }

    /**
     * @param pSession -
     * @return {@link PageParameters}を引数に取るコンストラクタに渡せるオブジェクト.
     */
    public static PageParameters getSessionParameter(final ESession pSession) {
        ArgUtil.checkNull(pSession, "pSession"); //$NON-NLS-1$
        final PageParameters ret = new PageParameters();
        ret.set(0, pSession.getEnglishName());
        return ret;
    }

    private static PageParameters n(final PageParameters pParameters) {
        return pParameters == null ? new PageParameters() : pParameters;
    }

}
