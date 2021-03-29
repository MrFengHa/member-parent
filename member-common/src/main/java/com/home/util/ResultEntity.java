package com.home.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 统一整个Ajax请求返回的结果
 *
 * @author 冯根源
 * @date 2021/1/25 23:34
 */
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity<T> {
    public static final String SUCCESS ="SUCCESS";
    public static final String ERROR ="ERROR";
    /**
     * 用来封装当前请求处理的结果是成功还是失败
     */
    private String result;
    /**
     * 请求处理失败时，返回的错误消息
     */
    private String message;

    /**
     * 要返回的数据
     */
    private T data;

    /**
     * 请求处理成功切不需要返回数据时使用的工具方法
     * @param <E>
     * @return
     */
    public static <E> ResultEntity<E> ok(){
        return new ResultEntity<E>(SUCCESS,null,null);
    }

    /**
     * 请求处理失败后使用的工具方法
     * @param message
     * @param <E>
     * @return
     */
    public static <E> ResultEntity<E> error(String message){
        return new ResultEntity<E>(ERROR,message,null);
    }
    public ResultEntity data(T data){
        this.setData(data);
        return  this;
    }
}
