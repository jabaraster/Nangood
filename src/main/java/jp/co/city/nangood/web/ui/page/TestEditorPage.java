/**
 * 
 */
package jp.co.city.nangood.web.ui.page;

import jabara.general.ArgUtil;
import jabara.wicket.CssUtil;
import jabara.wicket.beaneditor.BeanEditor;

import java.io.Serializable;

import jp.co.city.nangood.entity.ETest;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author jabaraster
 */
@SuppressWarnings("synthetic-access")
public class TestEditorPage extends WebPageBase {
    private static final long serialVersionUID = 2913469784279132006L;

    private final Handler     handler          = new Handler();

    private final ETest       beanValue;

    private Form<?>           form;
    private BeanEditor<ETest> bean;
    private Button            submitter;

    /**
     * 
     */
    public TestEditorPage() {
        this(new ETest());
    }

    /**
     * @param pBean 編集対象のオブジェクト.
     */
    public TestEditorPage(final ETest pBean) {
        this.beanValue = ArgUtil.checkNull(pBean, "pBean"); //$NON-NLS-1$
        this.add(getForm());
    }

    /**
     * @return -
     */
    public ETest getBean() {
        return this.beanValue;
    }

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        CssUtil.addComponentCssReference(pResponse, TestEditorPage.class);
    }

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of(BeanEditor.class.getSimpleName() + "のテスト"); //$NON-NLS-1$
    }

    private BeanEditor<ETest> getEditor() {
        if (this.bean == null) {
            this.bean = new BeanEditor<>("editor", this.beanValue); //$NON-NLS-1$
        }
        return this.bean;
    }

    private Form<?> getForm() {
        if (this.form == null) {
            this.form = new Form<>("form"); //$NON-NLS-1$
            this.form.add(getEditor());
            this.form.add(getSubmitter());
        }
        return this.form;
    }

    @SuppressWarnings("serial")
    private Button getSubmitter() {
        if (this.submitter == null) {
            this.submitter = new Button("submitter") { //$NON-NLS-1$
                @Override
                public void onSubmit() {
                    TestEditorPage.this.handler.onSubmit();
                }
            };
        }
        return this.submitter;
    }

    private class Handler implements Serializable {
        private static final long serialVersionUID = 2974830439817040233L;

        void onSubmit() {
            final ETest e = TestEditorPage.this.beanValue;
            jabara.Debug.write(e);
        }
    }
}
