import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.taotao.comon.utils.FastDFSClient;
import com.taotao.mapper.TbItemMapper;
import com.taotao.po.TbItem;
import com.taotao.po.TbItemExample;

import org.csource.fastdfs.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestPageHelper {
    @Test
    public void testPageHeler() throws Exception{
        //1.获取代理对象
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        TbItemMapper itemMapper=applicationContext.getBean(TbItemMapper.class);
        //2.设置分页
        PageHelper.startPage(1,30);
        //3.执行查询
        TbItemExample example=new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        //4.取分页后的结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        long total=pageInfo.getTotal();
        System.out.println("total:"+total);
        int pageSize = pageInfo.getPageSize();
        System.out.println("pagesize:"+pageSize);
    }

    @Test
    public void testUpLoad()throws Exception {
        //初始化全局配置
        ClientGlobal.init("E:\\works1\\tao-web\\src\\main\\resources\\properties\\client.conf");
        //
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer=null;
        StorageClient storageClient=new StorageClient(trackerServer,storageServer);
        String[] strings=storageClient.upload_appender_file("D:\\timg 1.jpg","jpg",null);
        for(String string:strings){
            System.out.println(string);
        }
    }
    @Test
    public void  testFastDFSClient() throws Exception{
        FastDFSClient client=new FastDFSClient("properties/client.conf");
        String uploadFile=client.uploadFile("D:\\timg 1.jpg","jpg");
        System.out.println(uploadFile);
    }
}
