package easymall.controller;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import easymall.po.Products;
import easymall.po.Search;
import mongodb.MongoJDBC;
import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class SearchController {

    @RequestMapping("/search")
    public String search(HttpServletRequest request, Model model) throws IOException {
        MongoCollection<Document> connection = MongoJDBC.getConnection();
        Document document = new Document();
        String name = request.getParameter("name");
        document.append("name", name);
        System.out.println(name);
        FindIterable<Document> findIterable = connection.find(document);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        Products product = new Products();
        while (mongoCursor.hasNext()) {
            Document next = mongoCursor.next();
            System.out.println(next);
            product.setId((String)next.get("id"));
            product.setName((String) next.get("name"));
            String price = (String)next.get("price");
            product.setPrice(Double.valueOf(price));
            product.setCategory((String) next.get("category"));
            String pnum = (String) next.get("pnum");
            product.setPnum(Integer.valueOf(pnum));
            String soldnum = (String) next.get("soldnum");
            product.setSoldnum(Integer.valueOf(soldnum));
            product.setImgurl((String) next.get("imgurl"));
            product.setDescription((String) next.get("description"));
        }

        model.addAttribute("product", product);
        return "prod_info";
    }
}
