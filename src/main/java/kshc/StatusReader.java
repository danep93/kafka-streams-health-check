package kshc;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class StatusReader {

  public static void main(String[] args) throws Exception {
    JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:45455/jmxrmi");

    try (JMXConnector jmxc = JMXConnectorFactory.connect(url)) {
      MBeanServerConnection conn = jmxc.getMBeanServerConnection();

      ObjectName name = new ObjectName("kafka.streams:type=kafka-metrics-count");
      Double count = (Double) conn.getAttribute(name, "count");
      if (count.intValue() == 1) {
        throw new RuntimeException("Could not fetch metrics - Stream is not running.");
      }
    }
  }

}
