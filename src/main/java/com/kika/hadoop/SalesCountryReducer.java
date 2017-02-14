package com.kika.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SalesCountryReducer extends MapReduceBase implements Reducer<Text, Text, Text, IntWritable> {

	public void reduce(Text t_key, Iterator<Text> values, OutputCollector<Text,IntWritable> output, Reporter reporter) throws IOException {
		Text key = t_key;
		int frequencyForCountry = 0;
		Set<String> idSet = new HashSet<String>() {{
			add("15749fff367c5a968da4a37a04625478");
//			add("17bf6cf52ad07fdfd2221ccc2a37e267");
//			add("3a4e423a0ffd02a91bb241a839473673");
//			add("43a9b6e74af2d04a3e34bf2b3fd57c74");
//			add("832492b72e006e0c82f1b3b1ec24bb0c");
//			add("8ba90203c54d8b7c9a2c480e8a58cd8b");
//			add("9d3684828219f6253c8f6cbf84a3ea0");
//			add("c962bafff92327f7675256ebebc38ef5");
//			add("d3bec80281b15a83a3fe7c2d983a840");
//			add("f0df9692d84470b118d96b8366d37bdc");
		}};
		while (values.hasNext()) {
			// replace type of value with the actual type of our value
			String value = values.next().toString();
			for(String text : idSet) {
				if (value.contains(text)) {
					frequencyForCountry += 1;
				}
			}
			//frequencyForCountry += value.get();
		}
		output.collect(key, new IntWritable(frequencyForCountry));
	}
}
