
mkdir locator
mkdir server1
mkdir server2

export CLASSPATH=$CLASSPATH:$SF_LIB/mysql-connector-java-5.1.6.jar

echo $CLASSPATH;

sqlf locator start -dir=locator \
-statistic-sampling-enabled=true -statistic-archive-file=sqlfirestats_locator.gfs \
-server-groups=dbsync -conserve-sockets=false -distributed-system-id=2 \
-peer-discovery-address=10.46.95.104 -peer-discovery-port=3241 \
-locators=10.46.95.104[20202],10.28.229.148[10101] \
-client-bind-address=10.46.95.104 -client-port=1530 


sqlf server start -dir=server1 -locators=10.46.95.104:3241 -client-port=1528 \
-statistic-sampling-enabled=true -statistic-archive-file=sqlfirestats_srvr1.gfs \
-server-groups=dbsync -conserve-sockets=false

sqlf server start -dir=server2 -locators=10.46.95.104:3241 -client-port=1529 \
-statistic-sampling-enabled=true -statistic-archive-file=sqlfirestats_srvr2.gfs \
-server-groups=dbsync -conserve-sockets=false