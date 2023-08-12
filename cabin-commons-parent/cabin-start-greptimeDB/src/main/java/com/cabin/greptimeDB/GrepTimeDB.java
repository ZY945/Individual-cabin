package com.cabin.greptimeDB;

import io.greptime.GreptimeDB;
import io.greptime.models.*;
import io.greptime.options.GreptimeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author 伍六七
 * @date 2023/5/31 23:39
 */
public class GrepTimeDB {

    private static final Logger LOG = LoggerFactory.getLogger(GrepTimeDB.class);

    public static void main(String[] args) throws Exception {
        String endpoint = "ip:4001";
        GreptimeOptions opts = GreptimeOptions.newBuilder(endpoint) //
                .writeMaxRetries(1) //
                .readMaxRetries(2) //
                .routeTableRefreshPeriodSeconds(-1) //
                .build();

        GreptimeDB greptimeDB = new GreptimeDB();
        if (!greptimeDB.init(opts)) {
            throw new RuntimeException("Fail to start GreptimeDB client");
        }
        QueryRequest request = QueryRequest.newBuilder()
                .exprType(SelectExprType.Sql)
                .ql("SELECT * FROM monitor;")
                .build();
        CompletableFuture<Result<QueryOk, Err>> future = greptimeDB.query(request);
        Result<QueryOk, Err> result = future.get();
        if (result.isOk()) {
            QueryOk queryOk = result.getOk();
            SelectRows rows = queryOk.getRows();
            // `collectToMaps` will discard type information, if type information is needed,
            // please use `collect`.
            List<Map<String, Object>> maps = rows.collectToMaps();
            for (Map<String, Object> map : maps) {
                LOG.info("Query row: {}", map);
            }
        } else {
            LOG.error("Failed to query: {}", result.getErr());
        }

    }
}
