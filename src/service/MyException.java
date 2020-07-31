package service;

/**
 * @author barry
 * @create 2020-07-30 15:28
 */
public class MyException extends Exception {
    static final long serialVersionUID = -338751655124229948L;

    public MyException() {
    }

    public MyException(String message) {
        super(message);
    }
}
