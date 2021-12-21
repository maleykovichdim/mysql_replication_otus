#!/bin/bash
#check records on slaves
docker exec alpha sh -c "export MYSQL_PWD=toor; mysql -u root -h127.0.0.1 -e 'use socialnet; SELECT count(*) FROM users'"
docker exec slave sh -c "export MYSQL_PWD=toor; mysql -u root -h127.0.0.1 -e 'use socialnet; SELECT count(*) FROM users'"
docker exec slave2 sh -c "export MYSQL_PWD=toor; mysql -u root -h127.0.0.1 -e 'use socialnet; SELECT count(*) FROM users'"
echo "Done"