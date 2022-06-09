package com.system.instaKill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：公共返回对象
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean {
    private long code;
    private String message;
    private Object obj;

    /**
     * 描述：成功返回结果
     * @return 返回ResponseBean
     */
    public static ResponseBean success(){
        return new ResponseBean(ResponseBeanEnum.SUCCESS.getCode(),ResponseBeanEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 描述：成功返回结果
     * @param obj 需要封装在ResponseBean的对象
     * @return 返回ResponseBean
     */
    public static ResponseBean success(Object obj){
        return new ResponseBean(ResponseBeanEnum.SUCCESS.getCode(),ResponseBeanEnum.SUCCESS.getMessage(), obj);
    }

    /**
     * 功能描述：失败返回结果
     * @param responseBeanEnum 错误枚举类型
     * @return 返回ResponseBean
     */
    public static ResponseBean error(ResponseBeanEnum responseBeanEnum){
        return new ResponseBean(responseBeanEnum.getCode(), responseBeanEnum.getMessage(), null);
    }

    /**
     * 功能描述：失败返回结果
     * @param responseBeanEnum 错误枚举类型
     * @param obj 需要封装在ResponseBean的对象
     * @return 返回ResponseBean
     */
    public static ResponseBean error(ResponseBeanEnum responseBeanEnum,Object obj){
        return new ResponseBean(responseBeanEnum.getCode(), responseBeanEnum.getMessage(), obj);
    }
}
