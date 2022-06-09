package com.system.instaKill.controller;

import com.system.instaKill.pojo.User;
import com.system.instaKill.service.IGoodsService;
import com.system.instaKill.service.IUserService;
import com.system.instaKill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liditao
 * @since 2022-03-14
 */
@Controller
@SuppressWarnings({"all"})
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     *描述：展示商品页面
     *  1、生成页面html数据并保存在redis中
     *  2、从redis中获取页面html数据
     */
    @RequestMapping(value = "/toList",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response){
        //获取redis中的页面，如果不为空，直接返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String goodsListHtml = (String)valueOperations.get("goodsList");
        if(!StringUtils.isEmpty(goodsListHtml)){
            return goodsListHtml;
        }
        model.addAttribute("goodsList",goodsService.findGoodsVo());
        //如果为空，则需要手动渲染，存入Redis并且返回
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(),model.asMap());
        goodsListHtml = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if(!StringUtils.isEmpty(goodsListHtml)){
            valueOperations.set("goodList",goodsListHtml,60, TimeUnit.SECONDS);
        }
        return goodsListHtml;
    }

    /**
     * 描述：展示商品详情页
     *  1、判断商品秒杀活动是否结束
     *  2、生成商品详情页html数据保存在redis中
     *  3、从redis中读取html数据并返回给前端
     * @param goodsId 商品id
     * @return 页面名称
     */
    @RequestMapping(value= "/toDetail/{goodsId}",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail(Model model, User user, @PathVariable Long goodsId, HttpServletRequest request, HttpServletResponse response){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String goodsDetailHtml = (String)valueOperations.get("goodsDetail:"+goodsId);
        if(!StringUtils.isEmpty(goodsDetailHtml)){
            return goodsDetailHtml;
        }
        model.addAttribute("user",user);
        GoodsVo goodsVo = goodsService.findGoodsByGoodsId(goodsId);
        Date startDate = goodsVo.getStartTime();
        Date endDate = goodsVo.getEndDate();
        Date date = new Date();
        int secKillStatus;
        int remainSeconds = 0;
        if(date.before(startDate)){
            remainSeconds = ((int)(startDate.getTime()-date.getTime()))/1000;
            secKillStatus = 0;
        }else if(date.after(endDate)){
            //秒杀结束
            secKillStatus = 2;
            remainSeconds = -1;
        }else{
            secKillStatus = 1;
        }
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("secKillStatus",secKillStatus);
        model.addAttribute("goods",goodsVo);
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        goodsDetailHtml = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
        if(!StringUtils.isEmpty(goodsDetailHtml)){
            valueOperations.set("goodsDetail:"+goodsId,goodsDetailHtml,5,TimeUnit.SECONDS);
        }
        return goodsDetailHtml;
    }
}
