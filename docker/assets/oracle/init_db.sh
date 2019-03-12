#!/bin/bash

service oracle-xe start
sleep 40s

if [ "$ORACLE_ALLOW_REMOTE" = true ]; then
  export ORACLE_HOME=/u01/app/oracle/product/11.2.0/xe
  export PATH=$ORACLE_HOME/bin:$PATH
  export ORACLE_SID=XE
  echo "alter system disable restricted session;" | sqlplus -s SYSTEM/oracle
fi

for f in /docker-entrypoint-initdb.d/*; do
  case "$f" in
    *.sh)     echo "$0: running $f"; /bin/bash "$f" ;;
    *.sql)    echo "$0: running $f"; echo "exit" | /u01/app/oracle/product/11.2.0/xe/bin/sqlplus "SYSTEM/oracle" @"$f"; echo ;;
    *)        echo "$0: ignoring $f" ;;
  esac
  echo
done

rm -f /docker-entrypoint-initdb.d/CSVStorage.sh 

service oracle-xe stop 
