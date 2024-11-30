package wingbank.com.kh.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqAndRes {
    private Integer accountId;
    private Integer page;
    private Integer limit;

    @Getter
    @Setter
    public static class InfoData {
        private int Page;
        private int Limit;
        public InfoData(int Page, int Limit) {
            this.Page = Page;
            this.Limit = Limit;
        }
    }

    @Getter
    @Setter
    public static class ResData<T> {
        private String message;
        private T data;

        public ResData(String message, T data) {
            this.message = message;
            this.data = data;
        }

    }

    @Getter
    @Setter
    public static class ResDataPage<T> {
        private String message;
        private T data;
        private InfoData Page;


        public ResDataPage(String message, T data, InfoData Page) {
            this.message = message;
            this.data = data;
            this.Page = Page;
        }

    }
}
