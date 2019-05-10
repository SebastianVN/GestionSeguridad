package seguridad.sevinjofagaca.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import seguridad.sevinjofagaca.modelo.Usuarios;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-10T01:51:50")
@StaticMetamodel(Logs.class)
public class Logs_ { 

    public static volatile SingularAttribute<Logs, Integer> idUser;
    public static volatile SingularAttribute<Logs, Date> fecha;
    public static volatile SingularAttribute<Logs, String> tipoEvento;
    public static volatile SingularAttribute<Logs, Integer> id;
    public static volatile SingularAttribute<Logs, Usuarios> us;

}