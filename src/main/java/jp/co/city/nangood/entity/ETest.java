/**
 * 
 */
package jp.co.city.nangood.entity;

import jabara.bean.annotation.Nullable;
import jabara.general.SortRule;
import jabara.wicket.beaneditor.EditorFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jp.co.city.nangood.web.ui.component.CategoryEditor;

/**
 * @author jabaraster
 */
@Entity
public class ETest extends AppEntity<ETest> {
    private static final long serialVersionUID = 7567625597865562427L;

    /**
     * 
     */
    protected boolean         booleanProperty;

    /**
     * 
     */
    @Column(nullable = true)
    protected Boolean         nullableBooleanProperty;

    /** 
     * 
     */
    @Column(nullable = true)
    protected SortRule        nullableEnumProperty;

    /**
     * 
     */
    @Enumerated(EnumType.STRING)
    protected SortRule        enumProperty;

    /**
     * 
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    protected ETestCategory   category;

    /**
     * @return categoryを返す.
     */
    @EditorFactory(CategoryEditor.Factory.class)
    public ETestCategory getCategory() {
        return this.category;
    }

    /**
     * @return enumPropertyを返す.
     */
    public SortRule getEnumProperty() {
        return this.enumProperty;
    }

    /**
     * @return nullableBooleanPropertyを返す.
     */
    @Nullable
    public Boolean getNullableBooleanProperty() {
        return this.nullableBooleanProperty;
    }

    /**
     * @return nullableEnumPropertyを返す.
     */
    @Nullable
    public SortRule getNullableEnumProperty() {
        return this.nullableEnumProperty;
    }

    /**
     * @return booleanPropertyを返す.
     */
    public boolean isBooleanProperty() {
        return this.booleanProperty;
    }

    /**
     * @param pBooleanProperty booleanPropertyを設定.
     */
    public void setBooleanProperty(final boolean pBooleanProperty) {
        this.booleanProperty = pBooleanProperty;
    }

    /**
     * @param pCategory categoryを設定.
     */
    public void setCategory(final ETestCategory pCategory) {
        this.category = pCategory;
    }

    /**
     * @param pEnumProperty enumPropertyを設定.
     */
    public void setEnumProperty(final SortRule pEnumProperty) {
        this.enumProperty = pEnumProperty;
    }

    /**
     * @param pNullableBooleanProperty nullableBooleanPropertyを設定.
     */
    public void setNullableBooleanProperty(final Boolean pNullableBooleanProperty) {
        this.nullableBooleanProperty = pNullableBooleanProperty;
    }

    /**
     * @param pNullableEnumProperty nullableEnumPropertyを設定.
     */
    public void setNullableEnumProperty(final SortRule pNullableEnumProperty) {
        this.nullableEnumProperty = pNullableEnumProperty;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
        return "ETest [booleanProperty=" + this.booleanProperty + ", nullableBooleanProperty=" + this.nullableBooleanProperty
                + ", nullableEnumProperty=" + this.nullableEnumProperty + ", enumProperty=" + this.enumProperty + ", id=" + this.id + "]";
    }
}
