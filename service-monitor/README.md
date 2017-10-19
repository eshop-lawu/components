服务监控
====

构建镜像
------
```bash    
mvn clean package -Dmaven.test.skip -DpushImage
```

启动容器
------

```bash
docker run --name service-monitor -it -d -p 5111:5111 \
    -v /usr/local/eshop/service-monitor/product_config.json:/config.json:ro \
    registry.eshop.com/service-monitor
```