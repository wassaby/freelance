#! /bin/sh
cd vio
mysql vio < ./create-tables.sql -u vio_dev -p
