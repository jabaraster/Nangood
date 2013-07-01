package jp.co.city.nangood.web.ui.page;

import jabara.general.ExceptionUtil;
import jabara.general.NotFound;
import jabara.wicket.CssUtil;
import jabara.wicket.ErrorClassAppender;
import jabara.wicket.JavaScriptUtil;
import jabara.wicket.beaneditor.BeanEditor;
import jabara.wicket.beaneditor.PropertyEditor;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import jp.co.city.nangood.entity.ESession;
import jp.co.city.nangood.entity.ESession_;
import jp.co.city.nangood.model.Duplicate;
import jp.co.city.nangood.service.ISessionService;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValueConversionException;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 * @author jabaraster
 */
@SuppressWarnings({ "serial", "synthetic-access" })
public class SessionEditorPage extends WebPageBase {
    private static final long        serialVersionUID   = 3755023173751841675L;

    private static final String      PARAM_SESSION_ID   = "id";                                     //$NON-NLS-1$

    @Inject
    private ISessionService          sessionService;

    private final Handler            handler            = new Handler();
    private final ESession           sessionValue;

    private final ErrorClassAppender errorClassAppender = new ErrorClassAppender(Model.of("error")); //$NON-NLS-1$

    private Form<ESession>           form;
    private FeedbackPanel            feedback;
    private BeanEditor<ESession>     editor;
    private Button                   submitter;
    private Button                   deleter;

    /**
     * 
     */
    public SessionEditorPage() {
        this.sessionValue = new ESession();
        initialize();
    }

    /**
     * @param pParameters -
     */
    public SessionEditorPage(final PageParameters pParameters) {
        try {
            final long id = n(pParameters).get(PARAM_SESSION_ID).toLong();
            this.sessionValue = this.sessionService.findById(id);
            initialize();
        } catch (final StringValueConversionException e) {
            throw new AbortWithHttpErrorCodeException(HttpServletResponse.SC_NOT_FOUND);
        } catch (final NotFound e) {
            throw new AbortWithHttpErrorCodeException(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        JavaScriptUtil.addJQuery1_9_1Reference(pResponse);
        JavaScriptUtil.addComponentJavaScriptReference(pResponse, SessionEditorPage.class);
        CssUtil.addComponentCssReference(pResponse, SessionEditorPage.class);
        addFocusScript(pResponse);
    }

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of(this.getClass().getSimpleName());
    }

    private void addFocusScript(final IHeaderResponse pResponse) {
        try {
            final PropertyEditor ic = getEditor().findInputComponent(ESession_.name.getName());
            ic.visitChildren(FormComponent.class, new IVisitor<FormComponent<?>, Object>() {
                @Override
                public void component(final FormComponent<?> pFormComponent, final IVisit<Object> pVisit) {
                    JavaScriptUtil.addFocusScript(pResponse, pFormComponent);
                    pVisit.stop();
                }
            });
        } catch (final NotFound e) {
            throw ExceptionUtil.rethrow(e); // 例外は通常起きないはず
        }
    }

    private Button getDeleter() {
        if (this.deleter == null) {
            this.deleter = new Button("deleter") { //$NON-NLS-1$
                @Override
                public boolean isVisible() {
                    return SessionEditorPage.this.sessionValue.isPersisted();
                }

                @Override
                public void onSubmit() {
                    SessionEditorPage.this.handler.onDeleterSubmit();
                }
            };
        }
        return this.deleter;
    }

    private BeanEditor<ESession> getEditor() {
        if (this.editor == null) {
            this.editor = new BeanEditor<>("editor", this.sessionValue); //$NON-NLS-1$
        }
        return this.editor;
    }

    private FeedbackPanel getFeedback() {
        if (this.feedback == null) {
            this.feedback = new ComponentFeedbackPanel("feedback", this); //$NON-NLS-1$
        }
        return this.feedback;
    }

    private Form<ESession> getForm() {
        if (this.form == null) {
            this.form = new Form<>("form"); //$NON-NLS-1$
            this.form.add(getFeedback());
            this.form.add(getEditor());
            this.form.add(getSubmitter());
            this.form.add(getDeleter());
        }
        return this.form;
    }

    private Button getSubmitter() {
        if (this.submitter == null) {
            this.submitter = new Button("submitter") { //$NON-NLS-1$
                @Override
                public void onError() {
                    SessionEditorPage.this.handler.onError();
                }

                @Override
                public void onSubmit() {
                    SessionEditorPage.this.handler.onSubmit();
                }
            };
        }
        return this.submitter;
    }

    private void initialize() {
        this.add(getForm());
    }

    /**
     * @param pSession -
     * @return {@link PageParameters}を引数に取るコンストラクタのためのオブジェクト.
     */
    public static PageParameters getIdParameter(final ESession pSession) {
        final PageParameters ret = new PageParameters();
        ret.add(PARAM_SESSION_ID, pSession.getId());
        return ret;
    }

    private static PageParameters n(final PageParameters pParameters) {
        return pParameters == null ? new PageParameters() : pParameters;
    }

    private class Handler implements Serializable {

        void onDeleterSubmit() {
            SessionEditorPage.this.sessionService.delete(SessionEditorPage.this.sessionValue);
            setResponsePage(SessionsPage.class);
        }

        void onError() {
            SessionEditorPage.this.errorClassAppender.addErrorClass(getForm());
        }

        void onSubmit() {
            try {
                SessionEditorPage.this.sessionService.save(SessionEditorPage.this.sessionValue);
                setResponsePage(SessionsPage.class);

            } catch (final Duplicate e) {
                try {
                    getEditor().findInputComponent(ESession_.englishName.getName()).error(getString("englishNameDuplicate")); //$NON-NLS-1$
                    SessionEditorPage.this.errorClassAppender.addErrorClass(getForm());

                } catch (final NotFound nf) {
                    throw ExceptionUtil.rethrow(nf);
                }
            }
        }
    }
}
