package jp.co.city.nangood.web.ui.page;

import jabara.general.ArgUtil;
import jp.co.city.nangood.Environment;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 *
 */
public abstract class WebPageBase extends WebPage {
    private static final long serialVersionUID = 9011478021815065944L;

    private Label             titleLabel;
    private Label             applicationNameInHeader;

    /**
     * 
     */
    protected WebPageBase() {
        this(new PageParameters());
    }

    /**
     * @param pParameters -
     */
    protected WebPageBase(final PageParameters pParameters) {
        super(pParameters);
        this.add(getApplicationNameInHeader());
        this.add(getTitleLabel());
    }

    /**
     * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        renderCommonHead(pResponse);
    }

    /**
     * headerタグ内のアプリケーション名を表示するラベルです. <br>
     * このメソッドはサブクラスでコンポーネントIDの重複を避けるためにprotectedにしています. <br>
     * 
     * @return headerタグ内のアプリケーション名を表示するラベル.
     */
    protected Label getApplicationNameInHeader() {
        if (this.applicationNameInHeader == null) {
            this.applicationNameInHeader = new Label("applicationNameInHeader", Model.of(Environment.getJapaneseApplicationName())); //$NON-NLS-1$
        }
        return this.applicationNameInHeader;
    }

    /**
     * titleタグの中を表示するラベルです. <br>
     * このメソッドはサブクラスでコンポーネントIDの重複を避けるためにprotectedにしています. <br>
     * 
     * @return titleタグの中を表示するラベル.
     */
    @SuppressWarnings({ "nls", "serial" })
    protected Label getTitleLabel() {
        if (this.titleLabel == null) {
            this.titleLabel = new Label("titleLabel", new AbstractReadOnlyModel<String>() {
                @Override
                public String getObject() {
                    return getTitleLabelModel().getObject() + " - " + Environment.getJapaneseApplicationName();
                }
            });
        }
        return this.titleLabel;
    }

    /**
     * @return HTMLのtitleタグの内容
     */
    protected abstract IModel<String> getTitleLabelModel();

    private static void addAppCss(final IHeaderResponse pResponse) {
        final String cssFileName = "App.css"; //$NON-NLS-1$
        final CssResourceReference ref = new CssResourceReference(WebPageBase.class, cssFileName);
        pResponse.render(CssHeaderItem.forReference(ref));
        WebApplication.get().mountResource(cssFileName, ref);
    }

    private static void addAppJavaScript(final IHeaderResponse pResponse) {
        final String jsFileName = "App.js"; //$NON-NLS-1$
        final JavaScriptResourceReference ref = new JavaScriptResourceReference(WebPageBase.class, jsFileName);
        pResponse.render(JavaScriptHeaderItem.forReference(ref));
        WebApplication.get().mountResource(jsFileName, ref);
    }

    private static void addBootstrapCss(final IHeaderResponse pResponse) {
        pResponse.render(CssHeaderItem.forReference(new CssResourceReference(WebPageBase.class, "bootstrap/css/bootstrap.min.css"))); //$NON-NLS-1$
    }

    /**
     * @param pResponse 全ての画面に共通して必要なheadタグ内容を出力します.
     */
    private static void renderCommonHead(final IHeaderResponse pResponse) {
        ArgUtil.checkNull(pResponse, "pResponse"); //$NON-NLS-1$
        addBootstrapCss(pResponse);
        addAppCss(pResponse);
        addAppJavaScript(pResponse);
    }

}
