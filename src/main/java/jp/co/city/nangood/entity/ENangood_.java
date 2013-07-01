package jp.co.city.nangood.entity;

import jabara.jpa.entity.EntityBase_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-06-30T22:25:02.872+0900")
@StaticMetamodel(ENangood.class)
public class ENangood_ extends EntityBase_ {
	public static volatile SingularAttribute<ENangood, Date> time;
	public static volatile SingularAttribute<ENangood, Integer> count;
	public static volatile SingularAttribute<ENangood, ESession> session;
}
