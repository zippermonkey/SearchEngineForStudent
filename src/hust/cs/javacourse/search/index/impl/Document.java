package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractTermTuple;

import java.util.LinkedHashSet;
import java.util.List;

public class Document extends AbstractDocument {
    /**
     * 缺省构造函数
     */
    public Document() {
        docPath = new String();
    }

    /**
     * 构造函数
     *
     * @param docId：文档id
     * @param docPath：文档绝对路径
     */
    public Document(int docId, String docPath) {
        this.docId = docId;
        this.docPath = docPath;
    }

    /**
     * 构造函数
     *
     * @param docId：文档id
     * @param docPath：文档绝对路径
     * @param tuples         三元组列表
     */
    public Document(int docId, String docPath, List<AbstractTermTuple> tuples) {
        this.docId = docId;
        this.docPath = docPath;
        this.tuples = tuples;
    }

    /**
     * 获得文档id
     *
     * @return ：文档id
     */
    @Override
    public int getDocId() {
        return this.docId;
    }

    /**
     * 设置文档id
     *
     * @param docId ：文档id
     */
    @Override
    public void setDocId(int docId) {
        this.docId = docId;
    }

    /**
     * 获得文档绝对路径
     *
     * @return ：文档绝对路径
     */
    @Override
    public String getDocPath() {
        return this.docPath;
    }

    /**
     * 设置文档绝对路径
     *
     * @param docPath ：文档绝对路径
     */
    @Override
    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    /**
     * 获得文档包含的三元组列表
     *
     * @return 文档包含的三元组列表
     */
    @Override
    public List<AbstractTermTuple> getTuples() {
        return this.tuples;
    }

    /**
     * 向文档对象里添加三元组, 要求不能有内容重复的三元组
     *
     * @param tuple ：要添加的三元组
     */
    @Override
    public void addTuple(AbstractTermTuple tuple) {
        // todo: 快速判断重复
        this.tuples.add(tuple);
        LinkedHashSet<AbstractTermTuple> set = new LinkedHashSet<>(this.tuples.size());
        set.addAll(this.tuples);
        this.tuples.clear();
        this.tuples.addAll(set);
    }

    /**
     * 判断是否包含指定的三元组
     *
     * @param tuple ： 指定的三元组
     * @return ： 如果包含指定的三元组，返回true;否则返回false
     */
    @Override
    public boolean contains(AbstractTermTuple tuple) {
        return this.tuples.contains(tuple);
    }

    /**
     * 获得指定下标位置的三元组
     *
     * @param index ：指定下标位置
     * @return：三元组
     */
    @Override
    public AbstractTermTuple getTuple(int index) {
        if (index >= 0 && index < this.tuples.size()) {
            return this.tuples.get(index);
        } else {
            return null;
        }
    }

    /**
     * 返回文档对象包含的三元组的个数
     *
     * @return ：文档对象包含的三元组的个数
     */
    @Override
    public int getTupleSize() {
        return this.tuples.size();
    }

    /**
     * 获得Document的字符串表示
     *
     * @return ： Document的字符串表示
     */
    @Override
    public String toString() {
        String string = new String();
        string += ("docId: " + docId + '\n');
        string += ("docPath: " + docPath + '\n');
        for (int i = 0; i < tuples.size(); i++) {
            string += (tuples.get(i).toString() + '\n');
        }
        return string;
    }
}
