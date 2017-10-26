#config-server
启动两个节点的派号器服务
```bash
nohup java -jar id-worker-1.0-SNAPSHOT.jar --server.port=9002 --number-generation.id-worker.worker-id=0 &
nohup java -jar id-worker-1.0-SNAPSHOT.jar --server.port=9003 --number-generation.id-worker.worker-id=1 &
```