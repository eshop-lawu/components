从命令行运行 MyBatis Generator
-----
利用mybatis-generator-core-1.3.2.jar生成文件
```bash
java -jar mybatis-generator-core-1.3.2.jar -configfile generatorConfig.xml -overwrite
```
通过Maven运行 MyBatis Generator
-----
```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```