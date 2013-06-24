/**
 * 
 */
package jp.co.city.nangood.model;

/**
 * @author jabaraster
 */
public class Duplicate extends Exception {
    private static final long  serialVersionUID = -4457298063973634343L;

    /**
     * 
     */
    public static final Global INSTANCE         = new Global();

    /**
     * @author jabaraster
     */
    public static final class Global extends Duplicate {
        private static final long serialVersionUID = -2535653158639773394L;
    }
}
