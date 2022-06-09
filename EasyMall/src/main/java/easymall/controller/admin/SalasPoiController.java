package easymall.controller.admin;

import easymall.po.Products;
import easymall.service.ProductsService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Controller
@RequestMapping("/admin")
public class SalasPoiController {

    @Autowired
    private ProductsService productsService;

    @RequestMapping("/salas_poi")
    public void salas_poi(HttpServletResponse response) throws IOException {
        //1.创建
        Workbook  wb = new XSSFWorkbook();
        //2.创建表单
        Sheet sheet = wb.createSheet("销售榜单");
        //设置样式
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //3.查找数据
        double _minPrice=0;
        double _maxPrice=Double.MAX_VALUE;
        Map<String,Object> map=new HashMap<>();
        map.put("name", "");
        map.put("category", "");
        map.put("minPrice", _minPrice);
        map.put("maxPrice", _maxPrice);
        List<Products> products = productsService.prodlist(map);
        //3.创建行对象
        Row row = sheet.createRow(0);
        //4.创建列对象
        Cell cell = row.createCell(0);
        cell.setCellValue("商品名称");
        cell = row.createCell(1);
        cell.setCellValue("商品单价");
        cell = row.createCell(2);
        cell.setCellValue("销售数量");
        cell = row.createCell(3);
        cell.setCellValue("库存数量");
        int i=1;
        for(Products product : products){
            row = sheet.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(product.getName());
            cell = row.createCell(1);
            cell.setCellValue(product.getPrice());
            cell = row.createCell(2);
            cell.setCellValue(product.getSoldnum());
            cell = row.createCell(3);
            cell.setCellValue(product.getPnum());
            i++;
        }
        //3.创建文件流
        //设置文件MIME类型
        response.setContentType("application/x-xls;charset=UTF-8");
        //设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename="+"salas_list.xlsx");//filename后面跟的是文件名，自己随便命名
        //4.通过流的形式写出文件
        ServletOutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        //5.关闭流
        outputStream.close();
    }

    @RequestMapping("/salas_echarts")
    @ResponseBody
    public List<Products> salas_echarts(){
        //3.查找数据
        List<Products> products = productsService.findSalasTop();
        return products;
    }
}
