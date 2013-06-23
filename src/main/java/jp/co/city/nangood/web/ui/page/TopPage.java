package jp.co.city.nangood.web.ui.page;

import jp.co.city.nangood.Environment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 *
 */
@SuppressWarnings("synthetic-access")
public class TopPage extends RestrictedPageBase {
    private static final long serialVersionUID = -4965903336608758671L;

    private final Handler     handler          = new Handler();

    private Label             applicationName;
    private Label             now;
    private AjaxLink<?>       reloader;
    private Link<?>           goLogout;

    /**
     * 
     */
    public TopPage() {
        this.add(getApplicationName());
        this.add(getNow());
        this.add(getReloader());
        this.add(getGoLogout());
    }

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of("Top"); //$NON-NLS-1$
    }

    private Label getApplicationName() {
        if (this.applicationName == null) {
            this.applicationName = new Label("applicationName", Model.of(Environment.getApplicationName())); //$NON-NLS-1$
        }
        return this.applicationName;
    }

    private Link<?> getGoLogout() {
        if (this.goLogout == null) {
            this.goLogout = new BookmarkablePageLink<Object>("goLogout", LogoutPage.class); //$NON-NLS-1$
        }
        return this.goLogout;
    }

    @SuppressWarnings({ "serial", "nls" })
    private Label getNow() {
        if (this.now == null) {
            this.now = new Label("now", new AbstractReadOnlyModel<String>() {
                @Override
                public String getObject() {
                    return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()); //$NON-NLS-1$
                }
            });
        }
        return this.now;
    }

    @SuppressWarnings("serial")
    private AjaxLink<?> getReloader() {
        if (this.reloader == null) {
            this.reloader = new IndicatingAjaxLink<Object>("reloader") { //$NON-NLS-1$
                @Override
                public void onClick(final AjaxRequestTarget pTarget) {
                    TopPage.this.handler.onReloaderClick(pTarget);
                }
            };
        }
        return this.reloader;
    }

    private class Handler implements Serializable {
        private static final long serialVersionUID = 8826180320287426527L;

        private void onReloaderClick(final AjaxRequestTarget pTarget) {
            pTarget.add(getNow());
        }

    }
}
