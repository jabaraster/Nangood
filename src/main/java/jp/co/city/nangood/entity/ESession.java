/**
 * 
 */
package jp.co.city.nangood.entity;

import jabara.jpa.entity.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author jabaraster
 */
@Entity
public class ESession extends EntityBase<ESession> {
    private static final long serialVersionUID            = 4019671149522574100L;

    /**
     * 
     */
    public static final int   MAX_CHAR_COUNT_NAME         = 50;

    /**
     * 
     */
    public static final int   MAX_CHAR_COUNT_ENGLISH_NAME = 20;

    /**
     * 
     */
    public static final int   MAX_CHAR_COUNT_DESCRIPTION  = 2000;
    /**
     * 
     */
    @Column(nullable = true, unique = true, length = MAX_CHAR_COUNT_NAME * 3)
    @NotNull
    @Size(min = 1, max = MAX_CHAR_COUNT_NAME)
    protected String          name;
    /**
     * 
     */
    @Column(nullable = true, unique = true, length = MAX_CHAR_COUNT_ENGLISH_NAME)
    @Pattern(regexp = "[a-zA-Z0-9_-]+")
    @NotNull
    @Size(min = 1, max = MAX_CHAR_COUNT_ENGLISH_NAME)
    protected String          englishName;

    /**
     * 
     */
    @Column(nullable = true, length = MAX_CHAR_COUNT_DESCRIPTION * 3)
    @Size(min = 0, max = MAX_CHAR_COUNT_DESCRIPTION)
    protected String          description;

    /**
     * @return descriptionを返す.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return nameInEnglishを返す.
     */
    public String getEnglishName() {
        return this.englishName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param pDescription descriptionを設定.
     */
    public void setDescription(final String pDescription) {
        this.description = pDescription;
    }

    /**
     * @param pNameInEnglish nameInEnglishを設定.
     */
    public void setEnglishName(final String pNameInEnglish) {
        this.englishName = pNameInEnglish;
    }

    /**
     * @param pName the name to set
     */
    public void setName(final String pName) {
        this.name = pName;
    }
}
