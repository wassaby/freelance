#! /bin/sh
cd vio
mysql vio < ./update_db.sql -u vio_dev -p
