package com.devops;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyService {

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
    public static HashMap<String, String> getLocalIPs() throws SocketException {
        HashMap<String, String> map = new HashMap<>();
        //List<String> list = new ArrayList<>();
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
                //list.add(addr.getHostAddress());
                map.put(intf.getDisplayName(),addr.getHostAddress());
            }
        }
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
    
    @RequestMapping("/")
    //public static void main(String[] args) {
    public static String info() {    
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println("系统时间：" + df.format(new Date()));// new Date()为获取当前系统时间
            System.out.println("操作系统：" + MyService.osName());
            System.out.println("主机名称：" + MyService.getHostName());
            //System.out.println("系统首选IP：" + MyService.getLocalIP());
            for (Entry<String, String> entry : MyService.getLocalIPs().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                System.out.println(key + "网口IP: " + value);
            }
        } catch (UnknownHostException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
