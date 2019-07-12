package com;

import utils.MailSentTherd;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Classname TimeStart
 * @Description
 * @@Author Pupansheng
 * @Date 2019/7/10 9:13
 * @Vestion 1.0
 **/
public class TimeStart {

    public  static Properties properties=new Properties();
    static {
        InputStream resourceAsStream = MessagePaqu.class.getClassLoader().getResourceAsStream("mail.properties");
        try {
            properties.load(resourceAsStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void f(){
        // 时间类
        Calendar startDate = Calendar.getInstance();

        //设置开始执行的时间为 某年-某月-某月 00:00:00
        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE), Integer.parseInt(properties.getProperty("hour")), Integer.parseInt(properties.getProperty("fen")), Integer.parseInt(properties.getProperty("miao")));

        String timeInterval1 = properties.getProperty("timeInterval");
        int i = Integer.parseInt(timeInterval1);
        // 1小时的毫秒设定
        long timeInterval = 60 * 60 * 1000*i;//i*小时
        // 定时器实例

        Timer t = new Timer();

        t.schedule(new TimerTask() {

            public void run() {

                // 定时器主要执行的代码块
                System.out.println("定时器主要执行的代码!");

                //爬取内容
                if(MessagePaqu.message.size()<=MessagePaqu.index) {

                    new MessagePaqu().start(properties.getProperty("url"));
                    while (MessagePaqu.message.size()< MessagePaqu.index) ;

                }
                String s = MessagePaqu.message.get(MessagePaqu.index++);
                System.out.println("发送内容:"+s);
                LocalTime now = LocalTime.now();

                if(!now.isAfter(LocalTime.of(12,0)))
                new MailSentTherd(s,properties.getProperty("jieshou"),"蒲蒲的早安问候",properties.getProperty("client"),properties.getProperty("password")).start();
                else
                    new MailSentTherd(s,properties.getProperty("jieshou"),"蒲蒲的晚安问候",properties.getProperty("client"),properties.getProperty("password")).start();



            }

            // 设定的定时器在15:10分开始执行,每隔 1小时执行一次.
        }, startDate.getTime(), timeInterval); //timeInterval 是一天的毫秒数，也是执行间隔



    }

    public static  void  main(String ...args) {



        new TimeStart().f();
        System.out.println("第二次提交");


    }

}










