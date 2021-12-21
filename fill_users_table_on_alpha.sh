#!/bin/bash

# put 1000 users records to alpha
docker exec alpha sh -c 'export MYSQL_PWD=toor; mysql -u root -h127.0.0.1 < /home/users_data.sql'
docker exec alpha sh -c "export MYSQL_PWD=toor; mysql -u root -h127.0.0.1 -e 'use socialnet; SELECT count(*) FROM users'"

echo "Done"