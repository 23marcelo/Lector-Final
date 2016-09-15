#!/bin/sh
# WSIMPORT_HOME= /usr/java/jdk1.7.0_80/bin/wsimport
# SOURCE_FOLDER= /home/cajero/workspace/Client/src
# BIN_FOLDER= /home/cajero/workspace/Client/bin
# DESTINY_PACKAGE= "descargados"

/usr/java/jdk1.7.0_80/bin/wsimport -s /home/cajero/workspace/Client/src -d /home/cajero/workspace/Client/bin -p descargados http://192.168.10.149:8080/ejemploHuellaWS?wsdl
