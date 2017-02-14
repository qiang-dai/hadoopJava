# Hadoop 编程步骤

##一、设置环境变量
```sh
export JAVA_HOME=$(/usr/libexec/java_home)
export HADOOP_HOME=/usr/local/Cellar/hadoop/2.7.3/
export CLASSPATH=$($HADOOP_HOME/bin/hadoop classpath):$CLASSPAT
```
##二、启动Hadoop
```
/usr/local/Cellar/hadoop/2.7.3/sbin/start-dfs.sh
/usr/local/Cellar/hadoop/2.7.3/sbin/start-yarn.sh
```
##三、编写java程序
##四、用 Intellij 进行编译打包
##五、执行hadoop命令
```
hadoop jar WordCount.jar org.apache.hadoop.examples.WordCount input output
```
##六、查看结果：

# 配置hadoop监控环境（伪分布式）
##[搭建链接](http://www.powerxing.com/install-hadoop/)
  
