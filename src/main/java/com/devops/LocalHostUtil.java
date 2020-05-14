import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.HashMap;
import java.util.Map.Entry;

public class LocalHostUtil {

    /**
     * 获取主机名称
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    /**
     * 获取系统首选IP
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getLocalIP() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * 获取所有网卡IP，排除回文地址、虚拟地址
     *
     * @return
     * @throws SocketException
     */
    public static Object getLocalIPs() throws SocketException {
        HashMap<String, String> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            NetworkInterface intf = enumeration.nextElement();
            if (intf.isLoopback() || intf.isVirtual()) { //
                continue;
            }
            Enumeration<InetAddress> inets = intf.getInetAddresses();
            while (inets.hasMoreElements()) {
                InetAddress addr = inets.nextElement();
                if (addr.isLoopbackAddress() || !addr.isSiteLocalAddress() || addr.isAnyLocalAddress()) {
                    continue;
                }
		list.add(addr.getHostAddress());
                map.put(intf.getDisplayName(),addr.getHostAddress());
            }
        }
        //return list.toArray(new String[0]);
        for (Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.println(key + ":" + value);
		}
	//System.out.println("=========================");
	return map; //System.out.println(map);
        //return list.toArray(new String[0]);
    }

    /**
     * 获取操作系统名称
     *
     * @return
     */
    public static String osName() {
        return System.getProperty("os.name");
    }

    public static void main(String[] args) {
        try {
            System.out.println("操作系统：" + LocalHostUtil.osName());
            System.out.println("主机名称：" + LocalHostUtil.getHostName());
            //System.out.println("系统首选IP：" + LocalHostUtil.getLocalIP());
            System.out.println("网络接口IP：\n" + LocalHostUtil.getLocalIPs());
        } catch (UnknownHostException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
