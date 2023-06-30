package com.cabin.monitor.util.cpu;

import javax.management.AttributeList;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.ArrayList;
import java.util.List;

public class CpuTemperatureReader {
    public static void main(String[] args) throws Exception {
        List<String> cpuTemperatures = getCpuTemperatures();
        for (String temperature : cpuTemperatures) {
            System.out.println("CPU Temperature: " + temperature);
        }
    }

    public static List<String> getCpuTemperatures() throws Exception {
        List<String> temperatures = new ArrayList<>();
        JMXServiceURL jmxUrl = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(jmxUrl);
        MBeanServerConnection mbsc = connector.getMBeanServerConnection();
        ObjectName name = new ObjectName("java.lang:type=OperatingSystem");
        AttributeList attributes = mbsc.getAttributes(name, new String[]{"ProcessCpuLoad"});
        if (attributes.isEmpty()) {
            throw new Exception("Couldn't retrieve CPU information");
        }
        double cpuLoad = (Double) attributes.get(0);
        temperatures.add(String.valueOf(cpuLoad));
        connector.close();
        return temperatures;
    }
}
