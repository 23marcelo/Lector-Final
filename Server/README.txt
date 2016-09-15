CONFIGURACIONES

1- La aplicacion funciona con la Version de java 1.7 o superior, los archivos del jar se compilaron con esa version.

2- En la carpeta del proyecto se tiene un archivo de configuracion, considerar su configuracion para el correcto funcionamiento del sistema.

3- En el archivo de configuracion se define el directorio para almacenar las huellas, asegurarse de que sea una ruta valida.

4- Se debe definir la variable de entorno LD_LIBRARY_PATH. Siga los sgtes pasos:
	4.1- Crear un script, con el siguiente codigo:
		LD_LIBRARY_PATH=/home/cajero/workspace/Client/libHuellas/linux-32\ bits/linux-i386/
		export LD_LIBRARY_PATH
	4.2- Modificar la ruta del archivo sh, se debe indicar la ruta donde se encuentran los archivos con extension .so (librerias del lector), los mismos se encuentran en la carpeta "libHuellas", sino no se hace esto la aplicacion arroja la excepcion JNCore....
	4.3- Copiar el archivo sh en la siguiente ruta: "/etc/profile.d/"
	4.4- Ejecute el comando:  source /etc/profile.d/biometria.sh
	4.5- Puede verificar la existencia de la variable abriendo la terminal y ejecutando el comando: export

5- Antes de ejecutar la aplicacion debe realizar el proceso de activacion, en la carpeta "Activacion". Siga las indicaciones que se encuentran en dicha carpeta.

6- Para finalmente publicar el servicio web se debe correr la clase "InterServerImp.java" del paquete "webServices".






OBSERVACIONES

Paso 3:
La ruta debe ser como sigue (NO OLVIDE LA BARRA FINAL):
directorio=/home/usuario/Escritorio/huellas/

Paso 6:
Asegurese que la variable de entorno creada en el punto 4, es accesible desde la consola que ejecutara el servidor. Si no se muestra la variable creada (mediante el comando export), antes de levantar el servidor, siempre ejecute el siguiente comando:
	source /etc/profile.d/biometria.sh   (ver paso 4.4)





