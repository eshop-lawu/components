纯JAVA命令启动
=====
```bash
nohup java -jar id-worker-1.0-SNAPSHOT.jar --number-generation.id-worker.worker-id=0 --eureka.client.serviceUrl.defaultZone=http://192.168.1.26:8888/eureka/ &
```

Docker启动
====

开发环境
----
```
docker run -d --name id-worker -p 9002:9002 \
    --network=eshop_eshopNet \
    -v /etc/localtime:/etc/localtime:ro \
    -e TZ=Asia/Shanghai
    -e eureka.client.serviceUrl.defaultZone=http://192.168.1.29:8888/eureka/ \
    -e number-generation.id-worker.worker-id=0 \
    registry.eshop.com/library/id-worker
```


预发布环境
----

```
docker run -d --name id-worker -p 9002:9002 \
    --network=eshop_eshopNet \
    -v /etc/localtime:/etc/localtime:ro \
    -e TZ=Asia/Shanghai
    -e eureka.client.serviceUrl.defaultZone=http://192.168.100.183:8888/eureka/,http://192.168.100.184:8888/eureka/ \
    -e number-generation.id-worker.worker-id=0 \
    registry.eshop.com/library/id-worker
    

docker run -d --name id-worker -p 9002:9002 \
    --network=eshop_eshopNet \
    -v /etc/localtime:/etc/localtime:ro \
    -e TZ=Asia/Shanghai
    -e eureka.client.serviceUrl.defaultZone=http://192.168.100.183:8888/eureka/,http://192.168.100.184:8888/eureka/ \
    -e number-generation.id-worker.worker-id=1 \
    registry.eshop.com/library/id-worker
```

正式环境
----

```
docker run -d --name id-worker -p 9002:9002 \
    --network=eshop_eshopNet \
    -v /etc/localtime:/etc/localtime:ro \
    -e TZ=Asia/Shanghai
    -e eureka.client.serviceUrl.defaultZone=http://192.168.100.80:8888/eureka/,http://192.168.100.81:8888/eureka/ \
    -e number-generation.id-worker.worker-id=0 \
    registry.eshop.com/library/id-worker
    

docker run -d --name id-worker -p 9002:9002 \
    --network=eshop_eshopNet \
    -v /etc/localtime:/etc/localtime:ro \
    -e TZ=Asia/Shanghai
    -e eureka.client.serviceUrl.defaultZone=http://192.168.100.80:8888/eureka/,http://192.168.100.81:8888/eureka/ \
    -e number-generation.id-worker.worker-id=1 \
    registry.eshop.com/library/id-worker
```