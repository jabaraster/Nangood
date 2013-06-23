/**
 * 
 */
package jp.co.city.nangood.web.ui.page;

import jabara.general.Empty;
import jabara.general.NotFound;

import java.io.Serializable;

import javax.inject.Inject;

import jp.co.city.nangood.service.ISessionService;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author jabaraster
 */
public class NangoodPage extends WebPageBase {
    private static final long serialVersionUID = 4409409145136534961L;

    @Inject
    private ISessionService   sessionService;

    /**
     * @param pParameters
     */
    public NangoodPage(final PageParameters pParameters) {
        final String sessionNameInEnglish = n(pParameters).get(0).toString(Empty.STRING);
        try {
            jabara.Debug.write(this.sessionService.findByEnglishName(sessionNameInEnglish));
        } catch (final NotFound e) {
            throw new RestartResponseException(TopPage.class);
        }
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

    private static PageParameters n(final PageParameters pParameters) {
        return pParameters == null ? new PageParameters() : pParameters;
    }

    private class Handler implements Serializable {

    }
}
