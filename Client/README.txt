CONFIGURACIONES
1- El sh wsimport debe ser modificado para que se conecte al servidor correctamente
	- La ruta del archivo wsimport
	- direccion servidor
	- nombre del servicio

2- En la carpeta del proyecto se tiene un archivo de configuracion, considerar su configuracion para el correcto funcionamiento del sistema.

3- Se debe definir la variable de entorno LD_LIBRARY_PATH. Siga los sgtes pasos:
	4.1- Crear un script, con el siguiente codigo:
		LD_LIBRARY_PATH=/home/cajero/workspace/Client/libHuellas/linux-32\ bits/linux-i386/
		export LD_LIBRARY_PATH
	4.2- Modificar la ruta del script (creado anteriormente), dicha ruta indica donde se encuentran los archivos con extension .so (librerias del lector), los mismos se encuentran en la carpeta "libHuellas", sino no se hace esto la aplicacion arroja la excepcion JNCore....
	4.3- Copiar el archivo sh en la siguiente ruta: "/etc/profile.d/"
	4.4- Ejecute el comando:  source /etc/profile.d/biometria.sh
	4.5- Puede verificar la existencia de la variable abriendo la terminal y ejecutando el comando: export

4- La aplicacion funciona con la Version de java 1.7, los archivos del jar se compilaron con esa version.

5- Antes de ejecutar la aplicacion recuerde correr el ejecutable "pgd", el mismo se encuentra en la carpeta "Activacion", este ejecutable es el que finalmente provee la/s licencia/s a la aplicacion.