package udp;

/**
 * Created by yss on 2018/4/12.
 */
public class Datagram_Msg {
    public String dst_ip;
    public int dst_port;
    public String src_ip;
    public int src_port;
    public int len;
    public byte[] data;


    public Datagram_Msg(String dst_ip, int dst_port, String src_ip, int src_port, int len, byte[] data) {
        this.dst_ip = dst_ip;
        this.dst_port = dst_port;
        this.src_ip = src_ip;
        this.src_port = src_port;
        this.len = len;
        this.data = data;
    }

    public Datagram_Msg()
    {

    }
}
