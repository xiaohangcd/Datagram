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
     * ʵ�ֵĽӰ�����:
     */
    public void receivePacket(Packet packet) {
        //UDP��

      //  System.out.println(baseConfig);

        if(packet instanceof jpcap.packet.UDPPacket ){
            UDPPacket p=(UDPPacket)packet;
            if(baseConfig.dst_ports.length!=0)
            {
                for(int i=0;i<baseConfig.dst_ports.length;i++)
                    if(p.dst_port==baseConfig.dst_ports[i])                 ///ץĿ�Ķ˿�Ϊ27015�İ�
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
        //ȡ����·������ͷ :����������ץ����α�����ݰ����ٺ�
        DatalinkPacket datalink  =packet.datalink;
        //�������̫����
        if(datalink instanceof jpcap.packet.EthernetPacket){
            EthernetPacket ep=(EthernetPacket)datalink;
            String s="  ��̫��: "
                    +"|Ŀ��MAC: "+ep.getDestinationAddress()
                    +"|ԴMAC: "+ep.getSourceAddress();
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
