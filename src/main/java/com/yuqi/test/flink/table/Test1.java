package com.yuqi.test.flink.table;/*
 * Author: park.yq@alibaba-inc.com
 * Date: 2018/12/18 下午10:01
 */

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Test1 {
	public static void main(String[] args) {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

//// create a TableEnvironment
//// for batch programs use BatchTableEnvironment instead of StreamTableEnvironment
//		StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
//
//// register a Table
//		tableEnv.registerTable("table1")            // or
//		tableEnv.registerTableSource("table2", ...);     // or
//		tableEnv.registerExternalCatalog("extCat", ...);
//// register an output Table
//		tableEnv.registerTableSink("outputTable", ...);
//
//// create a Table from a Table API query
//		Table tapiResult = tableEnv.scan("table1").select(...);
//// create a Table from a SQL query
//		Table sqlResult  = tableEnv.sqlQuery("SELECT ... FROM table2 ... ");
//
//// emit a Table API result Table to a TableSink, same for SQL result
//		tapiResult.insertInto("outputTable");
//
//// execute
//		env.execute();

		//todo test union all
		//select * from (select byte_test from test4dmp.test where id=5 union all select byte_test from test4dmp.test where id=900000 and byte_test=1)
	}
}
