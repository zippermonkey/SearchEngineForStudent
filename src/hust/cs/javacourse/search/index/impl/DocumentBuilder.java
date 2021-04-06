package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.LengthTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;
import hust.cs.javacourse.search.util.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentBuilder extends AbstractDocumentBuilder {
    /**
     * <pre>
     * 由解析文本文档得到的TermTupleStream,构造Document对象.
     * @param docId             : 文档id
     * @param docPath           : 文档绝对路径
     * @param termTupleStream   : 文档对应的TermTupleStream
     * @return ：Document对象
     * </pre>
     */
    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream) {
        // todo 感觉有特殊情况没有考虑
        List<AbstractTermTuple> tuples = new ArrayList<>();
        AbstractTermTuple itr = termTupleStream.next();
        while (itr != null) {
            tuples.add(itr);
            itr = termTupleStream.next();
        }
        return new Document(docId, docPath, tuples);
    }

    /**
     * <pre>
     * 由给定的File,构造Document对象.
     * 该方法利用输入参数file构造出AbstractTermTupleStream子类对象后,内部调用
     *      AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream)
     * @param docId     : 文档id
     * @param docPath   : 文档绝对路径
     * @param file      : 文档对应File对象
     * @return          : Document对象
     * </pre>
     */
    @Override
    public AbstractDocument build(int docId, String docPath, File file) {
        try {
            AbstractTermTupleStream scanner = new TermTupleScanner(new BufferedReader(new InputStreamReader(new FileInputStream(file))));
            AbstractTermTupleStream lengthFilter = new LengthTermTupleFilter(scanner);
            return build(docId, docPath, lengthFilter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * main测试
     */
    public static void main(String[] argv) {
        String docPath = Config.DOC_DIR + "1.txt";
        DocumentBuilder documentBuilder = new DocumentBuilder();
        File file = new File(docPath);
        AbstractDocument document = documentBuilder.build(0, docPath, file);
        System.out.println(document.toString());
    }
}
