package com;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @Classname MessagePaqu
 * @Description
 * @@Author Pupansheng
 * @Date 2019/7/10 9:25
 * @Vestion 1.0
 **/
public class MessagePaqu implements PageProcessor {

    public  static  int index=4;
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    public  static  List<String> message=new ArrayList<>();
    public static Stack<String> stackUrl=new Stack<>();
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {



          if(!stackUrl.contains(page.getUrl().get())) {

              Selectable span = page.getHtml().css("span");
              Selectable regex = span.regex("<span>.{0,}");//内容
              regex.all().forEach(p -> {
                  message.add(p);
              });
              stackUrl.push(page.getUrl().get());

              if(index>message.size()) {
                  String next = page.getHtml().css("a[class='next']").links().get();//下一页
                  page.addTargetRequest(next);
              }

          }
          else {
              String next = page.getHtml().css("a[class='next']").links().get();//下一页
              page.addTargetRequest(next);
          }

    }

    public Site getSite() {
        return site;
    }

    public void start(String url){

        Spider.create(new MessagePaqu())
                //从"https://github.com/code4craft"开始抓
                .addUrl(url)
                //开启5个线程抓取
                .thread(1)
                //启动爬虫
                .run();


    }

}
