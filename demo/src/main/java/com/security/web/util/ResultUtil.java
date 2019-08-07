package com.security.web.util;

import com.security.web.enums.ResultEnums;
import com.security.web.vo.ResultVO;

/**
 * @description: 定义响应数据格式工具类
 * @author: sca
 * @create: 2019-07-27 21:13
 **/
public class ResultUtil {

    public ResultUtil() {}

    public static ResultVO success(Object obj){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnums.SUCCESS.getCode());
        resultVO.setMsg(ResultEnums.SUCCESS.getMsg());
        resultVO.setData(obj);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(int code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        resultVO.setData(null);
        return resultVO;
    }
}
