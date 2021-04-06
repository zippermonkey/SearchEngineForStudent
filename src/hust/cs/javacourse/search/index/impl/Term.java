package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

public  class Term extends AbstractTerm {
    /**
     * 缺省构造函数
     */
    public Term(){ }

    /**
     * 构造函数
     * @param content
     */
    public Term(String content){this.content = content;}

    /**
     * 判断二个Term内容是否相同
     *
     * @param obj ：要比较的另外一个Term
     * @return 如果内容相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Term)
            return content.equals(((Term)obj).content);
        return false;
    }

    @Override
    public String toString(){
        return this.content;
    }

    /**
     * 返回Term内容
     *
     * @return Term内容
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * 设置Term内容
     *
     * @param content ：Term的内容
     */
    @Override
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 比较二个Term大小（按字典序）
     *
     * @param o ： 要比较的Term对象
     * @return ： 返回二个Term对象的字典序差值
     */
    @Override
    public int compareTo(AbstractTerm o) {
        return this.content.compareTo(o.getContent());
    }

    /**
     * 因为要作为HashMap里面的key，因此必须要覆盖hashCode方法
     * 返回对象的HashCode
     *
     * @return ：对象的HashCode
     */

}
