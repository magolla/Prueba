Envio sms con codigo
Login es con phone y device id
Caso de uso de ingreso con codigo validando

Bajarse Tomcat7, descomprimirlo en algun directorio.

Bajarse el eclipse Neon
Agregarle el plugin http://specialclientstools.appspot.com/wtf_update_site/
Setearle al plugin que use tomcat 7
Reiniciar el eclipse
Agregar al eclipse la variable de 
Java >> Build Path >> Classpath variables
TOMCAT7_HOME apuntando al folder de donde este instalado el tomcat7, ejemplo: /home/mgodoy/apache-tomcat-7.0.42
Ir a preferencias >> WTF >> Cambiar la version del tomcat a 7
Importar los proyectos del git.

Crear la base de datos,
mysql -uroot -proot
create database d2d; 

Levantar el proyecto con  el boton d2d

Conectarse a la base de datos como sigue
mysql -uroot -proot d2d

importar el sql de localidades en el mysql (el path va a variar)
mysql> source /home/mgodoy/bitbucket/doc2doc/src/main/resources/sql/localidades.sql

Inicializar la base
https://localhost:8443/d2d/api/initDB

Luego de esto, si se desea reinicar la base, proceder como sigue:
conectarse al mysql
mysql -uroot -proot
mysql> drop database d2d;

Y luego repetir los pasos a partir de Crear la base de datos,

NOTA:
EN caso que la clave de root no sea root, cambiarla como sigue: 
SET PASSWORD FOR 'root'@'localhost' = PASSWORD('root');

Terminar TODOS

Para actualizar las dependencias recordar :
- Bot�n derecho en el pom.xml / Run As / Maven Build...
- Poner en los GOALS: eclipse:eclipse

