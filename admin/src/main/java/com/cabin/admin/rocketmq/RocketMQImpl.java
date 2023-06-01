package com.cabin.admin.rocketmq;

import org.apache.rocketmq.client.ClientConfig;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.MQAdminImpl;
import org.apache.rocketmq.client.impl.MQClientAPIImpl;
import org.apache.rocketmq.client.impl.factory.MQClientInstance;
import org.apache.rocketmq.common.TopicConfig;
import org.apache.rocketmq.common.protocol.body.TopicConfigSerializeWrapper;
import org.apache.rocketmq.common.protocol.body.TopicList;
import org.apache.rocketmq.remoting.exception.RemotingConnectException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.remoting.exception.RemotingSendRequestException;
import org.apache.rocketmq.remoting.exception.RemotingTimeoutException;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 伍六七
 * @date 2023/5/30 13:09
 */
@Component
public class RocketMQImpl {

    //
    @Value("${monitor.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${monitor.rocketmq.brokerAddr}")
    private String brokerAddr;

    @Value("${monitor.rocketmq.defaultTopicName}")
    private String defaultTopicName;

    @Value("${monitor.rocketmq.consumerGROUP}")
    private String consumerGROUP;


    private DefaultMQPushConsumer consumer;
    private MQClientInstance mqClientInstance;
    private MQClientAPIImpl mqClientAPIImpl;
    private DefaultMQAdminExt admin;

    private MQAdminImpl mqAdminImpl;


    //自动加载
//    @PostConstruct
    public void init() {
        if (mqClientInstance == null) {
            mqClientInstance = start();
        } else {
            throw new RuntimeException("mqClientInstance已经初始化");
        }


        if (mqClientAPIImpl == null) {
            getMQClientAPIImpl(mqClientInstance);
        } else {
            throw new RuntimeException("mqClientInstance已经初始化");
        }


        if (mqAdminImpl == null) {
            getMqAdminImpl(mqClientInstance);
        } else {
            throw new RuntimeException("mqClientInstance已经初始化");
        }

        if (admin == null) {
            adminStart();
        }
    }

    public void adminStart() {
        admin = new DefaultMQAdminExt();
        admin.setNamesrvAddr("127.0.0.1:9876");
        try {
            admin.start();
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
    }

    public MQClientInstance start() {
        consumer = new DefaultMQPushConsumer(consumerGROUP);
        consumer.setNamesrvAddr(namesrvAddr);
        ClientConfig clientConfig = consumer.cloneClientConfig();
        String buildMQClientId = consumer.buildMQClientId();
        mqClientInstance = new MQClientInstance(clientConfig, 0, buildMQClientId);
        try {
            mqClientInstance.start();
        } catch (MQClientException e) {
            throw new RuntimeException("开启失败" + e);
        }
        return mqClientInstance;
    }

    public void shutdown() {
        if (mqClientInstance == null) {
            throw new RuntimeException("mqClientInstance已为null");
        } else {
            mqClientInstance.shutdown();
            mqClientInstance = null;
        }
        if (consumer == null) {
            throw new RuntimeException("consumer已为null");
        } else {
            consumer.shutdown();
            consumer = null;
        }
        if (admin == null) {
            throw new RuntimeException("admin已为null");
        } else {
            admin.shutdown();
            admin = null;
        }
    }

    private void getMQClientAPIImpl(MQClientInstance instance) {
        mqClientAPIImpl = instance.getMQClientAPIImpl();
    }

    private void getMqAdminImpl(MQClientInstance instance) {
        mqAdminImpl = instance.getMQAdminImpl();
    }

    public Set<String> getTopics() {
        Set<String> topics = new HashSet<>();
        long timeoutMillis = 2000L;
        try {
            TopicList topicList = mqClientAPIImpl.getTopicListFromNameServer(timeoutMillis);
            Set<String> topicSet = topicList.getTopicList();
            topics.addAll(topicSet);

            TopicList systemTopicList = mqClientAPIImpl.getSystemTopicList(timeoutMillis);
            Set<String> sysTopicSet = systemTopicList.getTopicList();//当前系统的topic
            topics.addAll(sysTopicSet);

            return topics;
        } catch (RemotingException | MQClientException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // 输出当前客户端获取到的topic
    public void printTopicList() {
        Set<String> topics = getTopics();
        System.out.println("打印所有的主题");
        System.out.println(topics);
    }

    public void printAllTopicInfo() throws Exception {
        TopicConfigSerializeWrapper allTopicConfig = mqClientAPIImpl.getAllTopicConfig(brokerAddr, 6000L);
        ConcurrentMap<String, TopicConfig> topicConfigTable = allTopicConfig.getTopicConfigTable();
        System.out.println(topicConfigTable);
    }


    // 直接创建一个topic(注意创建topic需要使用MQAdminImpl创建，并需要一个默认的topic才行，但是mqClientAPIImpl却执行失败)
    public boolean createTopic(String topicName) throws Exception {
        Set<String> topics = getTopics();
        if (!topics.contains(topicName)) {
            //mqAdminImpl.createTopic(defaultTopicName,topicName, 16);
            // 注意使用brokerAddr
            mqClientAPIImpl.createTopic(brokerAddr, defaultTopicName, new TopicConfig(topicName), 2000L);
            return true;
        } else {
            System.out.println("已存在topic名称为" + topicName + "的主题，无法执行添加操作！");
            return false;
        }
    }

    // 手动删除topic操作，可以执行删除的操作
    public boolean deleteTopicByName(String topicName) throws Exception {
        Set<String> topics = getTopics();
        if (topics.contains(topicName)) {
            //删除nameserver的主题信息
            mqClientAPIImpl.deleteTopicInNameServer(namesrvAddr, topicName, 2000L);

            //删除broker的主题信息
            mqClientAPIImpl.deleteTopicInBroker(brokerAddr, topicName, 2000L);
            return true;
        } else {
            System.out.println("没有这个topic名称为" + topicName + "的主题，无法执行删除操作！");
            return false;
        }
    }

    public HashMap<String, Object> getMonitor() {
        if (admin == null) {
            throw new RuntimeException("监控服务还未开启");
        }
        Set<String> topics = getTopics();
        HashMap<String, Object> map = new HashMap<>();
        // 获取 Broker 配置信息
        Properties properties = null;
        map.put("topics", topics);
        try {
            properties = admin.getBrokerConfig("120.55.99.46:10911");
        } catch (RemotingConnectException | RemotingSendRequestException | RemotingTimeoutException |
                 UnsupportedEncodingException | InterruptedException | MQBrokerException e) {
            throw new RuntimeException(e);
        }
        System.out.println(properties);
        System.out.println("---------------------------------------");
        //名称服务地址，指定 RocketMQ 集群中的名称服务器地址。
        String namesrvAddr = properties.getProperty("namesrvAddr");
        map.put("namesrvAddr", namesrvAddr);
        //Broker 名称，指定当前 Broker 在集群中的唯一标识。
        String brokerName = properties.getProperty("brokerName");
        map.put("brokerName", brokerName);
        // 获取 Broker集群信息
        String brokerClusterName = properties.getProperty("brokerClusterName");
        map.put("brokerClusterName", brokerClusterName);
        //消息延迟级别，指定消息发送后延迟发送的等待时间级别。
        String messageDelayLevel = properties.getProperty("messageDelayLevel");
        map.put("messageDelayLevel", messageDelayLevel);
        //最大消息大小，指定消息体最大允许的大小。
        String maxMessageSize = properties.getProperty("maxMessageSize");
        map.put("maxMessageSize", maxMessageSize);
        //消息发送线程池任务队列初始大小
        String sendThreadPoolQueueCapacity = properties.getProperty("sendThreadPoolQueueCapacity");
        map.put("sendThreadPoolQueueCapacity", sendThreadPoolQueueCapacity);
        //服务端处理消息发送线程池数量
        String sendMessageThreadPoolNums = properties.getProperty("sendMessageThreadPoolNums");
        map.put("sendMessageThreadPoolNums", sendMessageThreadPoolNums);
        //异步消息发送最大并发度
        String serverAsyncSemaphoreValue = properties.getProperty("serverAsyncSemaphoreValue");
        map.put("serverAsyncSemaphoreValue", serverAsyncSemaphoreValue);
        //客户端工作线程数。
        String clientWorkerThreads = properties.getProperty("clientWorkerThreads");
        map.put("clientWorkerThreads", clientWorkerThreads);
        //服务端处理查询消息线程池数量默认为8加上当前操作系统CPU核数的两倍
        String queryMessageThreadPoolNums = properties.getProperty("queryMessageThreadPoolNums");
        map.put("queryMessageThreadPoolNums", queryMessageThreadPoolNums);
        //broker存储目录 默认为用户的主目录/store
        String storePathRootDir = properties.getProperty("storePathRootDir");
        map.put("storePathRootDir", storePathRootDir);
        //是否定时刷盘，默认为 false，表示实时刷盘。
        String flushCommitLogTimed = properties.getProperty("flushCommitLogTimed");
        map.put("flushCommitLogTimed", flushCommitLogTimed);
        //查询消息默认返回条数,默认为32
        String defaultQueryMaxNum = properties.getProperty("defaultQueryMaxNum");
        map.put("defaultQueryMaxNum", defaultQueryMaxNum);
        return map;
    }
}
