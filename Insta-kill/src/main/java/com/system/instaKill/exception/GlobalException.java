package com.system.instaKill.exception;

import com.system.instaKill.vo.ResponseBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：全局异常
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException {
    private ResponseBeanEnum responseBeanEnum;

}
