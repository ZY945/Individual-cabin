package com.cabin.file.csv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.util.StopWatch;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * @author 伍六七
 * @date 2023/6/27 10:15
 * 对csv文件进行处理
 * 9,000,000---21295ms
 * 500---30ms
 */
@Slf4j
public class CSVUtil {
    public static void readHeader(String filePath, Consumer<String> lineConsumer) throws IOException {
        readFirstLine(filePath, lineConsumer);
    }

    public static void readFirstLine(String filePath, Consumer<String> lineConsumer) throws IOException {
        readLine(filePath, 1, lineConsumer);
    }

    public static void readLastLine(String filePath, Consumer<String> lineConsumer) throws IOException {
        readLine(filePath, -1, lineConsumer);
    }

    public static void readLine(String filePath, Integer len, Consumer<String> lineConsumer) throws IOException {
        final File csvFile = new File(filePath);
        if (!csvFile.exists()) {
            throw new FileNotFoundException("文件[" + filePath + "]不存在");
        }
        FileReader fileReader = new FileReader(filePath);
        BufferedReader reader = new BufferedReader(fileReader);

        long num = 0L;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CSVParser records = CSVFormat
                .DEFAULT
                // 设置分隔符
//                .withDelimiter(',')
                .parse(reader);
        //列名
        Iterator<CSVRecord> iterator = records.iterator();
        if (len == null) {
            while (iterator.hasNext()) {
                String line = Arrays.toString(iterator.next().values());
                log.info("解析第[{}]行数据[{}]", ++num, line);
                if (Objects.nonNull(lineConsumer)) {
                    lineConsumer.accept(line);
                }
            }
        } else if (len > 0) {
            while (iterator.hasNext() && len > 0) {
                len--;
                String line = Arrays.toString(iterator.next().values());
                log.info("解析第[{}]行数据[{}]", ++num, line);
                if (Objects.nonNull(lineConsumer)) {
                    lineConsumer.accept(line);
                }
            }
        } else if (len == -1) {
            CSVRecord lastElement = null;
            while (iterator.hasNext()) {
                lastElement = iterator.next();
            }
            String line = Arrays.toString(lastElement.values());
            log.info("解析第[{}]行数据[{}]", ++num, line);
            if (Objects.nonNull(lineConsumer)) {
                lineConsumer.accept(line);
            }
        }
        stopWatch.stop();
        log.info("CSV文件[{}]解析结束, 共用时：[{}]ms", filePath, stopWatch.getTotalTimeMillis());

    }

//    public static void main(String[] args) throws IOException {
//        String fileName = "F:\\study\\code\\pythoncode\\loadDataSource\\BigDataSource\\Geo9,000,000.csv";
////        readLine(fileName, 5, System.out::println);
////        read(fileName, 5, System.out::println);
//        readHeader(fileName, System.out::println);
//        readLastLine(fileName, System.out::println);
//    }


    public static void read(String filePath, Integer len, Consumer<String> lineConsumer) throws FileNotFoundException {
        final File csvFile = new File(filePath);
        if (!csvFile.exists()) {
            throw new FileNotFoundException("文件[" + filePath + "]不存在");
        }
        Long num = 0L;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try (
                FileInputStream fis = new FileInputStream(csvFile);
                Scanner sc = new Scanner(fis, StandardCharsets.UTF_8)
        ) {
            if (len == null) {
                while (sc.hasNext()) {
                    String line = sc.nextLine();
                    log.info("解析第[{}]行数据[{}]", ++num, line);
                    if (Objects.nonNull(lineConsumer)) {
                        lineConsumer.accept(line);
                    }
                }
            } else if (len > 0) {
                while (sc.hasNext() && len > 0) {
                    len--;
                    String line = sc.nextLine();
                    log.info("解析第[{}]行数据[{}]", ++num, line);
                    if (Objects.nonNull(lineConsumer)) {
                        lineConsumer.accept(line);
                    }
                }
            }
            stopWatch.stop();
            log.info("CSV文件[{}]解析结束, 共用时：[{}]ms", filePath, stopWatch.getTotalTimeMillis());
        } catch (IOException ex) {
            log.error("解析CSV文件[{}]第[{}]行数据发生异常", filePath, num, ex);
        }
    }
}
