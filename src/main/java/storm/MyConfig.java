package storm;

/**
 * Created by yss on 2018/4/11.
 */
public class MyConfig {
    public String brokerList = "192.168.1.4:9092";
    //public String logDir = "./";


   // public String[] zkServers = {"192.168.1.4"};


    //the zk root dir to store zk data
    public String zkRoot = "/test";

    public String topic = "test";

    public int zkPort = 2181;

    
    public String hbaseTable="yss";
    public String zks="192.168.1.4:2181";
    public String[] zkServers={"192.168.1.4"};
    public String id="yss-consumer-test";

    public int []dst_ports={27015};

    public MyConfig()
    {

    }


}
