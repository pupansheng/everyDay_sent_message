package utils;

/**
 * @Classname MailSentTherd
 * @Description
 * @@Author Pupansheng
 * @Date 2019/7/10 9:16
 * @Vestion 1.0
 **/
public class MailSentTherd extends Thread{


    String content;
    String reciveUser;
    String theme="蒲蒲的问候";
    public MailSentTherd(String c,String r,String clent,String passwd)
    {
        QQMailUtils.setMyEmailAccount(clent);
        QQMailUtils.setMyEmailPassword(passwd);
        content=c;
        reciveUser=r;

    }
    public MailSentTherd(String c,String r,String theme,String clent,String passwd)
    {
        QQMailUtils.setMyEmailAccount(clent);
        QQMailUtils.setMyEmailPassword(passwd);
        content=c;
        reciveUser=r;
        this.theme=theme;
    }
    public void run()
    {
        QQMailUtils.sent(reciveUser,content,theme);
    }



}