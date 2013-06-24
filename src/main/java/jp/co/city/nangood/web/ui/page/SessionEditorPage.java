/**
 * 
 */
package jp.co.city.nangood.web.ui.page;

import jabara.general.ArgUtil;
import jabara.general.NotFound;
import jabara.wicket.CssUtil;
import jabara.wicket.ErrorClassAppender;
import jabara.wicket.JavaScriptUtil;
import jabara.wicket.ValidatorUtil;

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
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValueConversionException;
import org.apache.wicket.validation.validator.StringValidator;

/**
 * @author jabaraster
 */
@SuppressWarnings({ "synthetic-access", "serial" })
public class SessionEditorPage extends WebPageBase {
    private static final long        serialVersionUID   = 654683026519994877L;

    private static final String      PARAM_SESSION_ID   = "id";                                     //$NON-NLS-1$

    @Inject
    private ISessionService          sessionService;

    private final Handler            handler            = new Handler();

    private final ErrorClassAppender errorClassAppender = new ErrorClassAppender(Model.of("error")); //$NON-NLS-1$

    private final ESession           sessionValue;

    private Form<ESession>           form;
    private FeedbackPanel            feedback;
    private TextField<String>        name;
    private FeedbackPanel            nameFeedback;
    private TextField<String>        englishName;
    private FeedbackPanel            englishNameFeedback;
    private TextArea<String>         description;
    private FeedbackPanel            descriptionFeedback;
    private Button                   deleter;
    private Button                   submitter;

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

        CssUtil.addComponentCssReference(pResponse, SessionEditorPage.class);

        JavaScriptUtil.addComponentJavaScriptReference(pResponse, SessionEditorPage.class);
        JavaScriptUtil.addJQuery1_9_1Reference(pResponse);
        JavaScriptUtil.addFocusScript(pResponse, getName());
    }

    /**
     * @see jp.co.city.nangood.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of("セッションを編集"); //$NON-NLS-1$
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

    private TextArea<String> getDescription() {
        if (this.description == null) {
            this.description = new TextArea<>(ESession_.description.getName());
            this.description.add(new StringValidator(Integer.valueOf(0), Integer.valueOf(ESession.MAX_CHAR_COUNT_DESCRIPTION)));
        }
        return this.description;
    }

    private FeedbackPanel getDescriptionFeedback() {
        if (this.descriptionFeedback == null) {
            this.descriptionFeedback = new ComponentFeedbackPanel(getDescription().getId() + "Feedback", getDescription()); //$NON-NLS-1$
        }
        return this.descriptionFeedback;
    }

    private TextField<String> getEnglishName() {
        if (this.englishName == null) {
            this.englishName = new TextField<String>(ESession_.englishName.getName()) {
                @Override
                public boolean isEnabled() {
                    return !SessionEditorPage.this.sessionValue.isPersisted();
                }
            };
            ValidatorUtil.setSimpleStringValidator(this.englishName, ESession.class, ESession_.englishName);
        }
        return this.englishName;
    }

    private FeedbackPanel getEnglishNameFeedback() {
        if (this.englishNameFeedback == null) {
            this.englishNameFeedback = new ComponentFeedbackPanel(getEnglishName().getId() + "Feedback", getEnglishName()); //$NON-NLS-1$
        }
        return this.englishNameFeedback;
    }

    private FeedbackPanel getFeedback() {
        if (this.feedback == null) {
            this.feedback = new ComponentFeedbackPanel("feedback", this); //$NON-NLS-1$
        }
        return this.feedback;
    }

    private Form<ESession> getForm() {
        if (this.form == null) {
            this.form = new Form<>("form", new CompoundPropertyModel<>(this.sessionValue)); //$NON-NLS-1$
            this.form.add(getFeedback());
            this.form.add(getName());
            this.form.add(getNameFeedback());
            this.form.add(getEnglishName());
            this.form.add(getEnglishNameFeedback());
            this.form.add(getDescription());
            this.form.add(getDescriptionFeedback());
            this.form.add(getSubmitter());
            this.form.add(getDeleter());
        }
        return this.form;
    }

    private TextField<String> getName() {
        if (this.name == null) {
            this.name = new TextField<>(ESession_.name.getName());
            ValidatorUtil.setSimpleStringValidator(this.name, ESession.class, ESession_.name);
        }
        return this.name;
    }

    private FeedbackPanel getNameFeedback() {
        if (this.nameFeedback == null) {
            this.nameFeedback = new ComponentFeedbackPanel(getName().getId() + "Feedback", getName()); //$NON-NLS-1$
        }
        return this.nameFeedback;
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
     * @return {@link PageParameters}を引数に取るコンストラクタに、安全に渡せるオブジェクト.
     */
    public static PageParameters getIdParameter(final ESession pSession) {
        ArgUtil.checkNull(pSession, "pSession"); //$NON-NLS-1$
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
                getEnglishName().error(getString("englishNameDuplicate")); //$NON-NLS-1$
                SessionEditorPage.this.errorClassAppender.addErrorClass(getForm());
            }
        }
    }

}
