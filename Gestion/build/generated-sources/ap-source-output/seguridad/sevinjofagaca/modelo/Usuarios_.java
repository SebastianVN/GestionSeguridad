package seguridad.sevinjofagaca.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import seguridad.sevinjofagaca.modelo.Logs;
import seguridad.sevinjofagaca.modelo.Servicio;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-10T01:51:50")
@StaticMetamodel(Usuarios.class)
public class Usuarios_ { 

    public static volatile ListAttribute<Usuarios, Servicio> servicios;
    public static volatile SingularAttribute<Usuarios, String> apellido;
    public static volatile SingularAttribute<Usuarios, Integer> id;
    public static volatile SingularAttribute<Usuarios, String> user;
    public static volatile SingularAttribute<Usuarios, String> nombre;
    public static volatile ListAttribute<Usuarios, Logs> logs;
    public static volatile SingularAttribute<Usuarios, String> contraseña;

}