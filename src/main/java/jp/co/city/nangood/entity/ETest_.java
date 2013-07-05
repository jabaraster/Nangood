package jp.co.city.nangood.entity;

import jabara.general.SortRule;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-07-04T16:23:19.381+0900")
@StaticMetamodel(ETest.class)
public class ETest_ extends AppEntity_ {
	public static volatile SingularAttribute<ETest, Boolean> booleanProperty;
	public static volatile SingularAttribute<ETest, Boolean> nullableBooleanProperty;
	public static volatile SingularAttribute<ETest, SortRule> nullableEnumProperty;
	public static volatile SingularAttribute<ETest, SortRule> enumProperty;
	public static volatile SingularAttribute<ETest, ETestCategory> category;
}
