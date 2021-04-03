package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.util.Config;

import java.io.*;

/**
 * <pre>
 * 类LengthTermTupleFilter 继承抽象类AbstractTermTupleFilter
 * 里面包含TermTupleScanner作为输入
 *
 * 类需要重新实现next方法以过滤掉不需要的单词对应的三元组
 *
 * 该类按照长度过滤
 * </pre>
 */
public class LengthTermTupleFilter extends AbstractTermTupleFilter {

    /**
     * 构造函数 调用super
     *
     * @param input：Filter的输入，类型为TermTupleScanner
     */
    public LengthTermTupleFilter(TermTupleScanner input) {
        super(input);
    }

    /**
     * 获得下一个三元组
     *
     * @return 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        //todo: 考虑下标越界异常 (已解决：通过null解决了)
        AbstractTermTuple temp = input.next();
        while ((temp != null) && (temp.term.getContent().length() > 20 || temp.term.getContent().length() < 3)) {
            temp = input.next();
        }
        return temp;
    }

    public static void main(String[] argv) {
        String fileName = Config.DOC_DIR + "2.txt";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LengthTermTupleFilter s = new LengthTermTupleFilter(new TermTupleScanner(reader));
        AbstractTermTuple iter = s.next();
        while (iter != null) {
            System.out.println(iter.toString() + "\n");
            iter = s.next();
        }
    }
}
