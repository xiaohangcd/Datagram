package storm;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

/**
 * Created by yss on 2018/4/10.
 */
public class GetPacket {


    //��������������
    public static void main(String args[]){

        try{

            //��ȡ�����ϵ�����ӿڶ�������
            final  NetworkInterface[] devices = JpcapCaptor.getDeviceList();
            for(int i=0;i<devices.length;i++){
                NetworkInterface nc=devices[i];
                //����ĳ�������ϵ�ץȡ����,���Ϊ2000��
                JpcapCaptor jpcap = JpcapCaptor.openDevice(nc, 2000, true, 20);
                jpcap.setFilter("udp",true);       //���ˣ�ֻ��udp��
                startCapThread(jpcap);       //�߳�ִ��ץ��
                System.out.println("��ʼץȡ��"+i+"�������ϵ�����");
            }

        }catch(Exception ef){
            ef.printStackTrace();
            System.out.println("����ʧ��:  "+ef);
        }

    }
    //��ÿ��Captor�ŵ������߳�������
    public static void startCapThread(final JpcapCaptor jpcap ){

        java.lang.Runnable rnner=new Runnable(){         //�����߳�
            public void run(){
                //ʹ�ýӰ�������ѭ��ץ��
                jpcap.loopPacket(-1, new ReceivePacket());   //-1����ץȡ����ץ����������ȡ��
            }
        };
        new Thread(rnner).start();//����ץ���߳�
    }

}
