package com.johann.javaEnum;

/**包含成员变量，且实现接口的枚举类
* @Description:
* @Param:
* @return:
* @Author: Johann
*/
public enum SeasonEnum implements SeasonMonth{

    SPRING("春天","立春、雨水、惊蛰、春分、清明、谷雨"){
        @Override
        public String getMonth() {
            return "2月3-5日 ---> 5月5-7日";
        }
    },
    SUMMER("夏天","立夏、小满、芒种、夏至、小暑、大暑"){
        @Override
        public String getMonth() {
            return "5月5-7日 --- 8月7-9日";
        }
    },
    FALL("秋天","立秋、处暑、白露、秋分、寒露、霜降"){
        @Override
        public String getMonth() {
            return "8月7-9日 --- 11月7-8日";
        }
    },
    WINTER("冬天","立冬、小雪、大雪、冬至、小寒、大寒"){
        @Override
        public String getMonth() {
            return "11月7-8日 --- 2月3-5日";
        }
    };

    private final String name;
    private final String solarTerm;

    public String getName() {
        return name;
    }

    public String getSolarTerm() {
        return solarTerm;
    }
//    private SeasonEnum(){
//
//    }

    private SeasonEnum(String name, String solarTerm){
        this.name = name;
        this.solarTerm = solarTerm;
    }

    @Override
    public String getMonth2() {
        return this.name;
    }
}
