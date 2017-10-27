纯JAVA命令启动
------
```bash
nohup java -jar id-worker-1.0-SNAPSHOT.jar --number-generation.id-worker.worker-id=0 --spring.profiles.active=test &
```

Docker启动
-------
```bash
sudo docker run -d id-worker --name id-worker -p 9002:9002 \
    -v ~/config:/usr/local/eshop/config:ro \
    -v /etc/localtime:/etc/localtime:ro \
    -e spring.profiles.active=dev \
    -e number-generation.id-worker.worker-id=0 \
    registry.eshop.com/id-worker
```