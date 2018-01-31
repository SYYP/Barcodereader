package qi.saoma.com.barcodereader.bean;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Successbean {


    /**
     * code : 401
     * message : 非法请求
     * data :
     */

    private String code;
    private String message;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
