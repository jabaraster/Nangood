/**
 * 
 */
package jp.co.city.nangood.web.ui.component;

import jabara.bean.BeanProperty;
import jabara.wicket.beaneditor.IEditorFactory;

import java.util.Calendar;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author jabaraster
 */
public class CategoryEditor extends Panel {
    private static final long serialVersionUID = -4547128241079893782L;

    /**
     * @param pId
     */
    public CategoryEditor(final String pId) {
        super(pId);
        this.add(new Label("now", String.valueOf(Calendar.getInstance().getTime()))); //$NON-NLS-1$
    }

    /**
     * @author jabaraster
     */
    public static class Factory implements IEditorFactory {
        /**
         * @see jabara.wicket.beaneditor.IEditorFactory#create(java.lang.String, java.lang.Object, jabara.bean.BeanProperty)
         */
        @Override
        public Panel create(final String pId, final Object pBean, final BeanProperty pProperty) {
            return new CategoryEditor(pId);
        }
    }
}
