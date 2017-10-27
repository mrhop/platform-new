package cn.hopever.platform.utils.web;

import lombok.Data;

/**
 * Created by Donghui Huo on 2017/10/26.
 */

public class ChartModal {

    @Data
    public static class LineModal {
        private Object x;
        private Object y;

        public LineModal() {
        }

        public LineModal(Object x, Object y) {
            this.x = x;
            this.y = y;
        }
    }

}
