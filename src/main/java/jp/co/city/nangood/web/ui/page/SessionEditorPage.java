/**
 * 
 */
package jp.co.city.nangood.web.ui.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author jabaraster
 */
public class SessionEditorPage extends RestrictedPageBase {
    private static final long serialVersionUID = 654683026519994877L;

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of("セッションを編集"); //$NON-NLS-1$
    }

}
