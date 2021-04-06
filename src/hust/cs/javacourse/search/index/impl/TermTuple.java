package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;

/**
 * <pre>
 * AbstractTermTuple是所有TermTuple对象的抽象父类.
 *      一个TermTuple对象为三元组(单词，出现频率，出现的当前位置).
 *      当解析一个文档时，每解析到一个单词，应该产生一个三元组，其中freq始终为1(因为单词出现了一次).
 * </pre>
 *
 *
 */
public class TermTuple extends AbstractTermTuple {
    /**
     * 构造函数  new一个term
     */
    public  TermTuple(){
        term = new Term();
    }
    public TermTuple(String content, int pos){
        term = new Term(content);
        curPos = pos;
    }
    /**
     * 判断二个三元组内容是否相同
     *
     * @param obj ：要比较的另外一个三元组
     * @return 如果内容相等（三个属性内容都相等）返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TermTuple) {
            return term.equals(((TermTuple) obj).term) && curPos == ((TermTuple) obj).curPos && freq == ((TermTuple) obj).freq;

        }
        return false;
    }

    /**
     * 获得三元组的字符串表示
     *
     * @return ： 三元组的字符串表示
     */
    @Override
    public String toString() {
        return "content: \t"+term.toString()+"\nfreq: \t\t"+ freq +"\npos: \t\t"+ curPos;
    }
}
