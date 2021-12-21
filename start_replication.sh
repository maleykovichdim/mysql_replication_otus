#!/bin/bash


until docker exec alpha sh -c 'export MYSQL_PWD=toor; mysql -u root -h127.0.0.1 -e ";"'
do
    echo "Waiting for alpha database connection..."
    sleep 4
done


until docker exec slave sh -c 'export MYSQL_PWD=toor; mysql -u root -h127.0.0.1 -e ";"'
do
    echo "Waiting for slave database connection..."
    sleep 4
done

docker-ip() {
    docker inspect --format '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' "$@"
}

MS_STATUS=`docker exec alpha sh -c 'export MYSQL_PWD=toor; mysql -u root -h127.0.0.1 -e "SHOW MASTER STATUS"'`
CURRENT_LOG=`echo $MS_STATUS | awk '{print $6}'`
CURRENT_POS=`echo $MS_STATUS | awk '{print $7}'`

start_slave_stmt="CHANGE MASTER TO MASTER_HOST='$(docker-ip alpha)',MASTER_USER='repl',MASTER_PASSWORD='slavepass',MASTER_LOG_FILE='$CURRENT_LOG',MASTER_LOG_POS=$CURRENT_POS; START SLAVE;"
start_slave_cmd='export MYSQL_PWD=toor; mysql -u root -e "'
start_slave_cmd+="$start_slave_stmt"
start_slave_cmd+='"'
docker exec slave sh -c "$start_slave_cmd"
docker exec slave2 sh -c "$start_slave_cmd"


docker exec slave sh -c "export MYSQL_PWD=toor; mysql -u root -h127.0.0.1 -e 'SHOW SLAVE STATUS \G'"
docker exec slave2 sh -c "export MYSQL_PWD=toor; mysql -u root -h127.0.0.1 -e 'SHOW SLAVE STATUS \G'"


echo "Done"