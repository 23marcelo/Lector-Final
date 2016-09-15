#!/bin/bash

if [ "${UID}" != "0" ]; then
	echo "ERROR: Please run this application with superuser privileges."
	exit 1
fi

if [ ! "${LICENSE_ACTIVATION_DIR}" ]
then
	LICENSE_ACTIVATION_DIR=`dirname "$0"`

	if [ "${LICENSE_ACTIVATION_DIR:0:1}" != "/" ]
	then
		LICENSE_ACTIVATION_DIR="${PWD}/${LICENSE_ACTIVATION_DIR}"
	fi
fi

LIB_DIR="`cd ${LICENSE_ACTIVATION_DIR}/../../../Lib/Linux_x86_64/; pwd`"

cd "${LICENSE_ACTIVATION_DIR}"

LD_LIBRARY_PATH="${LIB_DIR}:${LD_LIBRARY_PATH}" ./license_activation "$@"

