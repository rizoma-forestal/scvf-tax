especiesForestales
==================

Descripción de la aplicación:
-----------------------------

Esta aplicación gestiona la información correspondiente a la taxonomía de especies vegetales. Contiene una API de servicios web para ser consumidos tanto por aplicaciones internas como externas, de manera de proveer un registro único de taxonomías. Dada su estructura, sería fácil de escalar de manera de gestionar también la taxonomía de especies animales. Creada especialmente para consumir su información vía REST por SACVeFor, SIIF y las herramientas que lo pudieran requerir.

La acreditación de acceso de esta aplicación es gestionada mediante el acceso previo a gestionAplicaciones.

Disitingue dos roles de usuarios, el administrador que gestiona los contenidos y el seguridad, que gestiona usuarios y roles. Para asignar usuarios a la aplicación, previamente deben ser vinculados mediante gestionAplicaciones.	


Ambiente:
---------

Dado que anteriormente esta aplicación requería el servicio WSDL de autenticación de usuarios y aún no se ha actualizado tal requerimiento actualmente en desuso, 
es condición para la generación del .war de esta aplicación en el ambiente local tener desplegada previamente gestionAplicaciones.


Configuraciones:
----------------

Dado que todas las dependencias de las aplicaciones desarrolladas en java están gestionadas por Maven, una vez levantado el proyecto, deberá actualizarse todas las dependencias mediante el comando respectivo del IDE para que pueda compilar sin inconvenientes.

Deberá crearse en el directorio `scvf-app/src/main/resources/META-INF/` el archivo `persistence.xml` replicando el contenido del archivo `persistence.dist.xml` para
gestionar la unidad de persistencia de datos.

Deberá crearse en el directorio `scvf-app/src/main/resources/` el archivo `Config.properties` replicando el contenido del archivo `Config.properties.dist`,
si el puerto del servidor fuera otro alternativo al 8080, deberá modificarse la línea correspondiente.


Datos:
------

Deberá crearse en el servidor de base de datos la base `especiesForestales` con los parámetros de creación por defecto. Deberá restaurarse luego con el 
backup remitido por la coordinación del proyecto.

Se deberá crear en el servidor de aplicaciones el recurso `Datasource` con 
los siguientes parámetros (los que no se indiquen quedarán por defecto):  
Nombre: `EspeciesForestalesDS`  
JNDI:  `java:jboss/datasources/especiesForestales`  
Driver: `postgresql`  
Connection URL: `la que corresponda a la configuración local`  
Usuario y password de acceso: `los que correspondan a la configuración local`

Nota: El driver deberá estar previamente registrado en el servidor de aplciaciones.
