package jp.co.city.nangood.web.ui.page;

import jabara.general.ExceptionUtil;
import jabara.general.NotFound;
import jabara.wicket.CssUtil;
import jabara.wicket.ErrorClassAppender;
import jabara.wicket.beaneditor.BeanEditor;

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
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValueConversionException;

/**
 * @author jabaraster
 */
@SuppressWarnings({ "serial", "synthetic-access" })
public class SessionEditorPage2 extends WebPageBase {
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
    public SessionEditorPage2() {
        this.sessionValue = new ESession();
        initialize();
    }

    /**
     * @param pParameters -
     */
    public SessionEditorPage2(final PageParameters pParameters) {
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
        CssUtil.addComponentCssReference(pResponse, SessionEditorPage2.class);
    }

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of(this.getClass().getSimpleName());
    }

    private Button getDeleter() {
        if (this.deleter == null) {
            this.deleter = new Button("deleter") { //$NON-NLS-1$
                @Override
                public boolean isVisible() {
                    return SessionEditorPage2.this.sessionValue.isPersisted();
                }

                @Override
                public void onSubmit() {
                    SessionEditorPage2.this.handler.onDeleterSubmit();
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
            this.form.add(getDeleter());
            this.form.add(getSubmitter());
        }
        return this.form;
    }

    private Button getSubmitter() {
        if (this.submitter == null) {
            this.submitter = new Button("submitter") { //$NON-NLS-1$
                @Override
                public void onError() {
                    SessionEditorPage2.this.handler.onError();
                }

                @Override
                public void onSubmit() {
                    SessionEditorPage2.this.handler.onSubmit();
                }
            };
        }
        return this.submitter;
    }

    private void initialize() {
        this.add(getForm());
        // TODO セッション英名の不感化
    }

    private static PageParameters n(final PageParameters pParameters) {
        return pParameters == null ? new PageParameters() : pParameters;
    }

    private class Handler implements Serializable {

        void onDeleterSubmit() {
            SessionEditorPage2.this.sessionService.delete(SessionEditorPage2.this.sessionValue);
            setResponsePage(SessionsPage.class);
        }

        void onError() {
            SessionEditorPage2.this.errorClassAppender.addErrorClass(getForm());
        }

        void onSubmit() {
            try {
                SessionEditorPage2.this.sessionService.save(SessionEditorPage2.this.sessionValue);
                setResponsePage(SessionsPage.class);

            } catch (final Duplicate e) {
                try {
                    getEditor().findInputComponent(ESession_.englishName.getName()).error(getString("englishNameDuplicate")); //$NON-NLS-1$
                    SessionEditorPage2.this.errorClassAppender.addErrorClass(getForm());

                } catch (final NotFound nf) {
                    throw ExceptionUtil.rethrow(nf);
                }
            }
        }
    }
}
