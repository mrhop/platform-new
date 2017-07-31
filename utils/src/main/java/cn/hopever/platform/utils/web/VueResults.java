package cn.hopever.platform.utils.web;

import lombok.Data;

@Data
public class VueResults {

    @lombok.Data
    static class Data {
        Data(String code, String title, String message) {
            this.code = code;
            this.title = title;
            this.message = message;
        }

        private String code;
        private String title;
        private String message;
    }

    public static interface Result {

    }

    @lombok.Data
    public static class Success implements Result {
        Data success;

        Success(String title, String message) {
            success = new Data("200", title, message);
        }

        Success(String code, String title, String message) {
            success = new Data(code, title, message);
        }
    }

    @lombok.Data
    public static class Error implements Result {
        Data error;

        Error(String title, String message) {
            error = new Data("500", title, message);
        }

        Error(String code, String title, String message) {
            error = new Data(code, title, message);
        }
    }

    public static Success generateSuccess(String title, String message) {
        return new Success(title, message);
    }

    public static Error generateError(String title, String message) {
        return new Error(title, message);
    }
}
