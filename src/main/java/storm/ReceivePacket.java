package storm;

import Helper.FileHelper;
import Helper.KafkaNewProducer;
import com.google.gson.Gson;
import jpcap.PacketReceiver;
import jpcap.packet.*;
import kafka.server.KafkaApis;
import udp.Datagram_Msg;

/**
 * Created by yss on 2018/4/10.
 */
public class ReceivePacket  implements PacketReceiver {
//    KafkaNewProducer producer;
    MyConfig baseConfig;

    public ReceivePacket()
    {
        Gson gson=new Gson();
        String text = FileHelper.readString("config.json");
        baseConfig = gson.fromJson(text, MyConfig.class);
//        producer=new KafkaNewProducer(baseConfig.brokerList);
    }
    /**
     * 实现的接包方法:
     */
    public void receivePacket(Packet packet) {
        //UDP包

      //  System.out.println(baseConfig);

        if(packet instanceof jpcap.packet.UDPPacket ){
            UDPPacket p=(UDPPacket)packet;
            if(baseConfig.dst_ports.length!=0)
            {
                for(int i=0;i<baseConfig.dst_ports.length;i++)
                    if(p.dst_port==baseConfig.dst_ports[i])                 ///抓目的端口为27015的包
                    {

                        String src_ip=p.src_ip.getHostAddress();
                        String dst_ip=p.dst_ip.getHostAddress();
                        Gson gson=new Gson();
                        Datagram_Msg msg=new Datagram_Msg(dst_ip,p.dst_port,src_ip,p.src_port,p.len,p.data);
                        System.out.println(gson.toJson(msg));

                        String str=new String(p.data);
                        System.out.println(str);
//                        producer.send(baseConfig.topic,gson.toJson(msg));
                        break;
                    }
            }
            else
            {
                String src_ip=p.src_ip.getHostAddress();
                String dst_ip=p.dst_ip.getHostAddress();
                Gson gson=new Gson();
                Datagram_Msg msg=new Datagram_Msg(dst_ip,p.dst_port,src_ip,p.src_port,p.len,p.data);
                System.out.println(gson.toJson(msg));
//                producer.send(baseConfig.topic,gson.toJson(msg));
            }


        }
       /*
        //取得链路层数据头 :如果你想局网抓包或伪造数据包，嘿嘿
        DatalinkPacket datalink  =packet.datalink;
        //如果是以太网包
        if(datalink instanceof jpcap.packet.EthernetPacket){
            EthernetPacket ep=(EthernetPacket)datalink;
            String s="  以太包: "
                    +"|目的MAC: "+ep.getDestinationAddress()
                    +"|源MAC: "+ep.getSourceAddress();
             System.out.println(s);
        }
        */
    }
    public static String bytesToHexString(byte src) {
        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        return hv;
    }
}
