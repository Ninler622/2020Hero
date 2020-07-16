package com.recommender.cf_hadoop;

import java.io.IOException;

import com.utils.HdfsUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DataConversion {
	static class dcMapper extends Mapper<LongWritable, Text, Text, Text> {
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();

			try {
				String outputKey = "";
				String outputVal = line.replace("::", ",");
				outputVal=outputVal.substring(0,outputVal.lastIndexOf(","));
				System.out.println(outputVal);
				context.write(new Text(outputVal), new Text(outputKey));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static final String HDFS = "hdfs://ecs-hadoop-master:9000";

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		conf.addResource("classpath:/resources/core-site.xml");
		conf.addResource("classpath:/resources/hdfs-site.xml");
		conf.addResource("classpath:/resources/mapred-site.xml");

		//String localFile = HDFS + "/user/hdfs/data/ratings.dat";
		String inPath = HDFS + "/user/hdfs/itemCF";
		String outPath = HDFS + "/user/hdfs/itemCF/dc/";

		Job job = null;
		try {
			job = Job.getInstance(conf);

			HdfsUtil hdfs = new HdfsUtil(HDFS, conf);
			hdfs.rmr(inPath);
			hdfs.mkdirs(inPath);
			//hdfs.copyFile(localFile, inPath);
			
			job.setJarByClass(DataConversion.class);
			job.setJobName("dc");

			job.setMapperClass(dcMapper.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			FileInputFormat.addInputPath(job, new Path(inPath));
			FileOutputFormat.setOutputPath(job, new Path(outPath));
			// System.exit(job.waitForCompletion(true) ? 0 : 1);
			job.submit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
