package com.yuqi.test.flink.wordcount;/*
 * Author: park.yq@alibaba-inc.com
 * Date: 2019/1/17 下午2:38
 */

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.slf4j.LoggerFactory;

public class WordCountSink {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WordCount.class);
	public static void main(String[] args) {

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		env.enableCheckpointing(100000);
		env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
		env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
		env.getCheckpointConfig().setCheckpointTimeout(600000);
		env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
		//env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

//		try {
//			env.setStateBackend(new FsStateBackend("oss://039696/test/"));
//		} catch (Exception e) {
//			System.exit(-1);
//		}

//		DataStream<Tuple2<String, Integer>> dataStream = env
//				.socketTextStream("localhost", 9999)
//				.flatMap(new WordCount.Splitter())
//				.keyBy(0)
//				.timeWindow(Time.seconds(5))
//				.sum(1);

		DataStream<Tuple2<String, Integer>> dataStream = env.addSource(new WordCountSource.Source()).setParallelism(1)
				.flatMap(new WordCount.Splitter()).setParallelism(1)
				.keyBy(0)
				.timeWindow(Time.seconds(5))
				.sum(1).setParallelism(1);


		try {
			env.execute("Window WordCount");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

		public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
			logger.info("we get '" + sentence + "'");
			for (String word: sentence.split(" ")) {
				out.collect(new Tuple2<String, Integer>(word, 1));
			}
		}
	}

	public static class Source extends RichSourceFunction<String> implements ParallelSourceFunction<String> {

		private volatile boolean isRunning = true;
		@Override
		public void run(SourceContext<String> sourceContext) throws Exception {
			int i = 0;
			while(isRunning) {
				try {
					Thread.currentThread().sleep(1000);
					sourceContext.collect(i + " " + i);
					i++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void cancel() {
			isRunning = false;
		}
	}
}
