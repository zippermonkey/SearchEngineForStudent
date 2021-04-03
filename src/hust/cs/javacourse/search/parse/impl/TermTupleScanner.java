package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TermTupleScanner extends AbstractTermTupleScanner {
    /**
     * 文件里的所有内容
     */
    private String allContent;

    /**
     * TermTuple组成的列表
     */
    private List<AbstractTermTuple> termTuples = new ArrayList<>();
    /**
     * position
     */
    private int position = 0;

    /**
     * 缺省构造函数
     */
    public TermTupleScanner() {
    }

    /**
     * TermTupleScanner的构造函数
     *
     * @param _input: BufferedReader对象 关联文件
     */
    public TermTupleScanner(BufferedReader _input) {
        input = _input;
        List<String> parts;
        try {
            StringBuffer buf = new StringBuffer();
            while ((allContent = input.readLine()) != null) {
                buf.append(allContent).append("\n"); //reader.readLine())返回的字符串会去掉换行符，因此这里要加上
            }
            allContent = buf.toString().trim(); //去掉最后一个多的换行符
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        StringSplitter splitter = new StringSplitter();
        splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
        parts = splitter.splitByRegex(allContent);
        for (int i = 0; i < parts.size(); i++) {
            termTuples.add(new TermTuple(parts.get(i), i + 1));
        }
    }

    /**
     * 获得下一个三元组
     *
     * @return 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        // todo: 实现返回一个单词的三元组
        if (termTuples.isEmpty()) {
            return null;
        } else {
            // 如果越界返回null
            try {
                return termTuples.get(position++);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }

    /**
     * 关闭流
     */
    @Override
    public void close() {
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] argv) {
        String fileName = Config.DOC_DIR + "1.txt";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        TermTupleScanner s = new TermTupleScanner(reader);
        AbstractTermTuple iter = s.next();
        while (iter != null) {
            System.out.println(iter.toString() + "\n");
            iter = s.next();
        }

    }
}
