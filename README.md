1、编写程序，打成jar包
     设置环境变量：
          export JAVA_HOME=$(/usr/libexec/java_home)
          export HADOOP_HOME=/usr/local/Cellar/hadoop/2.7.3/
          export CLASSPATH=$($HADOOP_HOME/bin/hadoop classpath):$CLASSPAT
     启动hadoop
          /usr/local/Cellar/hadoop/2.7.3/sbin/start-dfs.sh
          /usr/local/Cellar/hadoop/2.7.3/sbin/start-yarn.sh
     编写java程序
          （略）
     用 Intellij 进行编译打包
     #编译程序
     #     参考http://www.powerxing.com/hadoop-build-project-by-shell/
     #     javac WordCount.java
     #打包.class文件
     #     jar -cvf WordCount.jar ./WordCount*.class
     执行hadoop命令
          hadoop jar WordCount.jar org.apache.hadoop.examples.WordCount input output
     查看结果
3、配置hadoop监控环境（伪分布式）
     http://www.powerxing.com/install-hadoop/
