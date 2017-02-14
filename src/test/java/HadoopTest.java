/**
 * Created by xinmei365 on 2017-02-08
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

public class HadoopTest {
    public static class TokenizerMapper extends Mapper<Object, Text, Text, Text>{


//        private final static IntWritable one = new IntWritable(1);
//        private Text word = new Text();
//
//        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
//            StringTokenizer itr = new StringTokenizer(value.toString());
//            while (itr.hasMoreTokens()) {
//                word.set(itr.nextToken());
//                context.write(word, one);
//            }
//        }
        private Text line = new Text();
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            //解析,找到国家
//            EsFeedbackInfo esFeedbackInfo = Utils.ParseFeedbackLine(Text., "hadoop", "sticker");
            //根据国家输出
        }

    }

    public static class IntSumReducer
            extends Reducer<Text,IntWritable,Text,IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
        ) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
//        WordCount.java中使用到了GenericOptionsParser这个类，它的作用是将命令行中参数自动设置到变量conf中。举个例子，比如我希望通过命令行设置reduce task数量，就这么写：
//
//        bin/hadoop jar MyJob.jar com.xxx.MyJobDriver -Dmapred.reduce.tasks=5
//        上面这样就可以了，不需要将其硬编码到java代码中，很轻松就可以将参数与代码分离开。
//
//        其它常用的参数还有”-libjars”和-“files”，使用方法一起送上：
//
//        bin/hadoop jar MyJob.jar com.xxx.MyJobDriver -Dmapred.reduce.tasks=5 \
//        -files ./dict.conf  \
//        -libjars lib/commons-beanutils-1.8.3.jar,lib/commons-digester-2.1.jar
//        参数”-libjars”的作用是上传本地jar包到HDFS中MapReduce临时目录并将其设置到map和reduce task的classpath中；参数”-files”的作用是上传指定文件到HDFS中mapreduce临时目录，并允许map和reduce task读取到它。这两个配置参数其实都是通过DistributeCache来实现的。
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage: wordcount <in> <out>");
            System.exit(2);
        }
        Job job = new Job(conf, "word count");
        job.setJarByClass(HadoopTest.class);
        job.setMapperClass(TokenizerMapper.class);
        //job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
