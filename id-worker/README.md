纯JAVA命令启动
=====
```bash
nohup java -jar id-worker-1.0-SNAPSHOT.jar --number-generation.id-worker.worker-id=0 --spring.profiles.active=test &
```

Docker启动
====

开发环境
----
```
docker run -d --name id-worker -p 9002:9002 \
    --network=eshop_eshopNet \
    -v /etc/localtime:/etc/localtime:ro \
    -e spring.profiles.active=dev \
    -e number-generation.id-worker.worker-id=0 \
    registry.eshop.com/id-worker
```


预发布环境
----

```
docker run -d --name id-worker -p 9002:9002 \
    --network=eshop_eshopNet \
    -v /etc/localtime:/etc/localtime:ro \
    -e spring.profiles.active=pp \
    -e number-generation.id-worker.worker-id=0 \
    registry.eshop.com/id-worker
    

docker run -d --name id-worker -p 9002:9002 \
    --network=eshop_eshopNet \
    -v /etc/localtime:/etc/localtime:ro \
    -e spring.profiles.active=pp \
    -e number-generation.id-worker.worker-id=1 \
    registry.eshop.com/id-worker
```

正式环境
----

```
docker run -d --name id-worker -p 9002:9002 \
    --network=eshop_eshopNet \
    -v /etc/localtime:/etc/localtime:ro \
    -e spring.profiles.active=product \
    -e number-generation.id-worker.worker-id=0 \
    registry.eshop.com/id-worker
    

docker run -d --name id-worker -p 9002:9002 \
    --network=eshop_eshopNet \
    -v /etc/localtime:/etc/localtime:ro \
    -e spring.profiles.active=product \
    -e number-generation.id-worker.worker-id=1 \
    registry.eshop.com/id-worker
```