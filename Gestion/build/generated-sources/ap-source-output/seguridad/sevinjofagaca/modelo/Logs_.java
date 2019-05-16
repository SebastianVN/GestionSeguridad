package seguridad.sevinjofagaca.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-16T00:09:02")
@StaticMetamodel(Logs.class)
public class Logs_ { 

    public static volatile SingularAttribute<Logs, Integer> idUser;
    public static volatile SingularAttribute<Logs, Date> fecha;
    public static volatile SingularAttribute<Logs, String> tipoEvento;
    public static volatile SingularAttribute<Logs, Integer> id;

}