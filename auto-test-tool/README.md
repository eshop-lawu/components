接口自动化测试工具
====
    基本功能：
    1、手动配置测试目标（可通过配置文件）。
    2、请求测试目标接口元数据信息并保存（可写入json文件），可刷新。
    3、通过元数据注意请求接口，分析返回结果，判断接口是否成功部署（提供页面，列表展示）。
    
纯JAVA命令启动
------
```bash
nohup java -jar auto-test-tool-1.0-SNAPSHOT.jar --spring.profiles.active=test &
```

Docker启动
-------
```bash
sudo docker run -d --name auto-test-tool -p 9003:9003 \
    --network=eshop_eshopNet \
    -v ~/config:/usr/local/eshop/config:ro \
    -v /etc/localtime:/etc/localtime:ro \
    -e spring.profiles.active=dev \
    registry.eshop.com/auto-test-tool
```