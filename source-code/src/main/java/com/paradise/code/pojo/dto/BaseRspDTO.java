package com.paradise.code.pojo.dto;

public class BaseRspDTO<T extends Object> {

    //区分是DTO返回的唯一标记，比如是UserInfoDTO还是BannerDTO
    private String key;
    //返回的data
    private T data;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static void main(String[] args) {
        int i = 0;
        while (true){
            if (i%2==1){
                break;
            }
            System.out.println(i);

            i++;
            if (i >= 10)break;
        }
    }
}