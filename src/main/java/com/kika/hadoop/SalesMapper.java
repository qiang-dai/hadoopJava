package com.kika.hadoop;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;

public class SalesMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
//	private final static IntWritable one = new IntWritable(1);
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

		String valueString = value.toString();
//		String[] SingleCountryData = valueString.split(",");
//		output.collect(new Text(SingleCountryData[7]), one);
		String keyword = "country:";
		Integer posBeg = valueString.indexOf(keyword);
		if (posBeg != -1) {
			Integer posEnd = valueString.indexOf(",", posBeg);
			if (posEnd != -1) {
				String country = valueString.substring(posBeg + keyword.length(), posEnd);
				output.collect(new Text(country), value);
			}
		}
	}
}
