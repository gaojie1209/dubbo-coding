package com.wellhope.dubboitem.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wellhope.api.ProductService;
import com.wellhope.entity.Product;
import com.wellhope.pojo.ResultBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * @author GaoJ
 * @create 2021-03-10 12:34
 */
@Controller
@RequestMapping("item")
public class ItemController {
    private static final Logger LOGGER  = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private Configuration configuration;
    @Reference
    private ProductService productService;
    @Autowired
    private ThreadPoolExecutor pool;
//    http://localhost:9093/item/createById/1
    /**
     * 根据id生成静态页面 http://localhost:9093/1.html
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("createById/{id}")
    @ResponseBody
    public ResultBean createById(@PathVariable("id") Long id) throws Exception {
        create(id);
        return new ResultBean("200","生成静态页面成功！");
    }
    /**
     * 生成静态页面
     * @param id
     * @throws IOException
     * @throws TemplateException
     */
    private  void create(@PathVariable("id")Long id) throws IOException, TemplateException {
        //1根据id，获取到商品数据
        Product product = productService.selectByPrimaryKey(id);
        //2采用FreeMarker生成对应的商品详情页
        Template template = configuration.getTemplate("item.ftl");
        Map<String, Object> data = new HashMap<>();
        data.put("product",product);
        //3输出
        //获取到项目运行时的路径
        //获取static的路径
        String serverpath= ResourceUtils.getURL("classpath:static").getPath();
        StringBuilder path = new StringBuilder(serverpath).append(File.separator).append(id).append(".html");
        FileWriter out = new FileWriter(path.toString());
        template.process(data,out);
    }

    /**
     *批量生成静态页面
     * @param ids
     * @return
     */
    @RequestMapping("batchCreate")
    @ResponseBody
    public ResultBean batchCreate(@RequestParam List<Long> ids){
        List<Future<Long>> results=new ArrayList<>(ids.size());
        for (Long id:ids){
            //调用方法--》创建一个线程来执行任务
            //串行-》并行
            //create(id);
            results.add(pool.submit(new CreateHTMLTask(id)));
        }
        //后续根据处理结果进行处理
        List<Long> errors=new ArrayList<>();
        for (Future<Long> future : results) {
            try {
                //获取执行结果，阻塞等待
                Long result = future.get();
                if (result !=0){
                    errors.add(result);
                }
            } catch (InterruptedException |ExecutionException e) {
                e.printStackTrace();
            }
        }
        for (Long error : errors) {
            //做错误处理的工作
            //1.输出日志
            LOGGER.error("批量生成页面失败，失败的页面为[{}]",errors);
            //2.将处理错误的id信息保存到日志表中
            // id product_id retry_times create_update update_time
            // 1    1            3           2019        2019

            //3.通过定时任务，扫描这张表
            //select * from t_create_html_log where retry_time<3;
            //update retry_time=retry_time+1

            //4.超过3次的记录，需要人工介入
        }
        return new ResultBean("200","批量生成页面成功！");
    }

    public class CreateHTMLTask implements Callable<Long>{
        private Long id;
        public CreateHTMLTask(Long id){
            this.id=id;
        }

        /**
         * 如果执行成功，则返回0
         * 如果执行失败，则返回当前的记录ID
         * @return
         * @throws IOException
         */
        @Override
        public Long call() throws IOException {
            //1根据id，获取到商品数据
            Product product = productService.selectByPrimaryKey(id);
            //2采用FreeMarker生成对应的商品详情页
            Template template=null;
            try {
                template = configuration.getTemplate("item.ftl");
                Map<String, Object> data = new HashMap<>();
                data.put("product",product);
                //3输出
                //获取到项目运行时的路径
                //获取static的路径
                String serverpath= ResourceUtils.getURL("classpath:static").getPath();
                StringBuilder path = new StringBuilder(serverpath).append(File.separator).append(id).append(".html");
                FileWriter out = new FileWriter(path.toString());
                template.process(data,out);
            } catch (IOException |TemplateException e) {
                //如果我们针对不同的异常，处理方式是不同的，那么就应该分别catch
                e.printStackTrace();
                return id;
            }
            return 0L;
        }
    }
}
