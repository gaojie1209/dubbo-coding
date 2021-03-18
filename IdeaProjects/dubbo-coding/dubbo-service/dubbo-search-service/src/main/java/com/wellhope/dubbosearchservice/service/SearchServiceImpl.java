package com.wellhope.dubbosearchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wellhope.api.SearchService;
import com.wellhope.entity.Product;
import com.wellhope.mapper.ProductMapper;
import com.wellhope.pojo.PageResultBean;
import com.wellhope.pojo.ResultBean;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author GaoJ
 * @create 2021-03-07 20:58
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    public ProductMapper productMapper;
    @Autowired
    public SolrClient solrClient;

    @Override
    public ResultBean synAllDate() {
        //1查询源数据
        //TODO 只查询需要的数据
        List<Product> products = productMapper.list();
        //2MySql ->solr
        for (Product product : products) {
            //Product->document
            //1创建一个document对象
            SolrInputDocument document = new SolrInputDocument();
            //2设置相关的属性
            document.setField("id",product.getId());
            document.setField("product_name",product.getName());
            document.setField("product_price",product.getPrice());
            document.setField("sale_point",product.getSalePoint());
            document.setField("product_images",product.getImages());
            //3保存
            try {
                solrClient.add(document);
            } catch (SolrServerException |IOException e) {
                e.printStackTrace();
                //TODO log
                return new ResultBean<String>("500","同步数据失败！");
            }
        }
        try {
            solrClient.commit();
        }catch (SolrServerException |IOException e) {
            e.printStackTrace();
            //TODO log
            return new ResultBean<String>("500","同步数据失败！");
        }
        return new ResultBean<String>("200","同步数据成功！");
    }

    @Override
    public ResultBean synById(Long id) {
        //1查询源数据
        Product product = productMapper.selectByPrimaryKey(id);
        //2MySql ->solr
        //Product->document
        //1创建一个document对象
        SolrInputDocument document = new SolrInputDocument();
        //2设置相关的属性
        document.setField("id",product.getId());
        document.setField("product_name",product.getName());
        document.setField("product_price",product.getPrice());
        document.setField("sale_point",product.getSalePoint());
        document.setField("product_images",product.getImages());
        //3保存
        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException |IOException e) {
            e.printStackTrace();
            //TODO log
            return new ResultBean("500","同步数据失败！");
        }

        return new ResultBean("200","同步数据成功！");
    }

    @Override
    public ResultBean delById(Long id) {
        try {
            solrClient.deleteById(String.valueOf(id));
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //TODO log
            return new ResultBean("500","同步数据失败！");
        }
        return new ResultBean("200","同步数据成功！");
    }

    @Override
    public ResultBean queryByKeywords(String keywords) {
        //1组装查询条件
        SolrQuery queryCondtion = new SolrQuery();
        if (keywords==null||"".equals(keywords.trim())){
            queryCondtion.setQuery("product_name:华为");
        }else {
            queryCondtion.setQuery("product_name:"+keywords);
        }
        //ADD1 添加高亮的设置
        queryCondtion.setHighlight(true);
        queryCondtion.addHighlightField("product_name");
        queryCondtion.setHighlightSimplePre("<font color='red'>");
        queryCondtion.setHighlightSimplePost("</font>");
        //2获取结果，并将结果转换为list
        List<Product> products=null;
        try {
            QueryResponse response = solrClient.query(queryCondtion);
            SolrDocumentList results = response.getResults();
            //result -> List
            products=new ArrayList<>(results.size());

            //ADD2 获取高亮的信息
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //华为
            //1001-华为1 1002-华为2
            //外层Map key（String）--1001
            //外层Map value---1001记录对应的高亮信息(包含多个字段的高亮信息)

            //为什么里面又是一个map？
            //里层Map key（String）---字段名（product_name）
            //里层Map value----字段对应的高亮信息

            for (SolrDocument document : results) {
                //document->product
                Product product = new Product();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
//                product.setName(document.getFieldValue("product_name").toString());
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                product.setImages(document.getFieldValue("product_images").toString());
                //TODO 传递product对象不合适，因为页面展示只需要4个字段（VO）

                //设置product_name高亮的信息
                //1获取当前记录的高亮信息
                Map<String, List<String>> higlight =highlighting.get(document.getFieldValue("id").toString());
                //2获取字段对应的高亮信息
                List<String> productNameHiglight = higlight.get("product_name");
                //3判断该字段是否有高亮信息
                if (productNameHiglight!=null&&productNameHiglight.size()>0){
                    //高亮信息
                    product.setName(productNameHiglight.get(0));
                }else {
                    //普通的文本信息
                    product.setName(document.getFieldValue("product_name").toString());
                }
                products.add(product);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean<List<Product>>("500",new ArrayList<Product>());
        }
        return new ResultBean<List<Product>>("200",products);
    }

    @Override
    public ResultBean queryByKeywords(String keywords, Integer pageIndex, Integer pageSize) {

        //1组装查询条件
        SolrQuery queryCondtion = new SolrQuery();
        if (keywords==null||"".equals(keywords.trim())){
            queryCondtion.setQuery("product_name:华为");
        }else {
            queryCondtion.setQuery("product_name:"+keywords);
        }
        //ADD1 添加高亮的设置
        queryCondtion.setHighlight(true);
        queryCondtion.addHighlightField("product_name");
        queryCondtion.setHighlightSimplePre("<font color='red'>");
        queryCondtion.setHighlightSimplePost("</font>");

        //添加分页的设置
        //从哪个下标开始
        queryCondtion.setStart((pageIndex-1)*pageSize);
        queryCondtion.setRows(pageSize);

        //2获取结果，并将结果转换为list
        List<Product> products=null;

        //List --> pageResultBean
        PageResultBean<Product> pageResultBean=new PageResultBean<>();
        //总条数
        long total = 0L;
        try {
            QueryResponse response = solrClient.query(queryCondtion);
            SolrDocumentList results = response.getResults();
            //result -> List
            products=new ArrayList<>(results.size());

            //ADD2 获取高亮的信息
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //华为
            //1001-华为1 1002-华为2
            //外层Map key（String）--1001
            //外层Map value---1001记录对应的高亮信息(包含多个字段的高亮信息)

            //为什么里面又是一个map？
            //里层Map key（String）---字段名（product_name）
            //里层Map value----字段对应的高亮信息
            //
            total=results.getNumFound();
            for (SolrDocument document : results) {
                //document->product
                Product product = new Product();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
//                product.setName(document.getFieldValue("product_name").toString());
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                product.setImages(document.getFieldValue("product_images").toString());
                //TODO 传递product对象不合适，因为页面展示只需要4个字段（VO）

                //设置product_name高亮的信息
                //1获取当前记录的高亮信息
                Map<String, List<String>> higlight =highlighting.get(document.getFieldValue("id").toString());
                //2获取字段对应的高亮信息
                List<String> productNameHiglight = higlight.get("product_name");
                //3判断该字段是否有高亮信息
                if (productNameHiglight!=null&&productNameHiglight.size()>0){
                    //高亮信息
                    product.setName(productNameHiglight.get(0));
                }else {
                    //普通的文本信息
                    product.setName(document.getFieldValue("product_name").toString());
                }
                products.add(product);
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("500",new PageResultBean<>());
        }
        //给pageResultBean赋值
        pageResultBean.setList(products);
        pageResultBean.setPageNum(pageIndex);
        pageResultBean.setPageSize(pageSize);
        pageResultBean.setTotal(total);
        pageResultBean.setPages((int) (total%pageSize==0?total/pageSize:(total/pageSize)+1));
        pageResultBean.setPageNum(3);
        return new ResultBean<PageResultBean<Product>>("200",pageResultBean);
    }
}
