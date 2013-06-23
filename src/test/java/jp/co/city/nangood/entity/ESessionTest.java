/**
 * 
 */
package jp.co.city.nangood.entity;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

/**
 * @author jabaraster
 */
public class ESessionTest {

    private Validator validator;

    /**
     * 
     */
    @Test
    public void _test() {
        final ESession session = new ESession();
        session.setEnglishName("abc_10-1"); //$NON-NLS-1$
        for (final ConstraintViolation<ESession> violation : this.validator.validate(session)) {
            System.out.println(violation);
        }
    }

    /**
     * 
     */
    @Before
    public void setUp() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

}
