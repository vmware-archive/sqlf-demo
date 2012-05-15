mkdir locator
mkdir server1
mkdir server2

export CLASSPATH=$CLASSPATH:$SF_LIB/mysql-connector-java-5.1.6.jar

echo $CLASSPATH;

sqlf locator start -peer-discovery-port=3241 -dir=locator \
-client-port=1530 -statistic-sampling-enabled=true \
-statistic-archive-file=sqlfirestats_locator.gfs \
-server-groups=dbsync -conserve-sockets=false \
-distributed-system-id=1

sqlf server start -dir=server1 -locators=localhost:3241 \
-client-port=1528 -statistic-sampling-enabled=true \
-statistic-archive-file=sqlfirestats_srvr1.gfs \
-server-groups=dbsync -conserve-sockets=false

sqlf server start -dir=server2 -locators=localhost:3241 \
-client-port=1529 -statistic-sampling-enabled=true \
-statistic-archive-file=sqlfirestats_srvr2.gfs \
-server-groups=dbsync -conserve-sockets=false


