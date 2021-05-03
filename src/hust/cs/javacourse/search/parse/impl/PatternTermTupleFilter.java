package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 *
 */
public class PatternTermTupleFilter extends AbstractTermTupleFilter {
    private String pattern;

    public PatternTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
        pattern = Config.TERM_FILTER_PATTERN;
    }

    /**
     * 获得下一个三元组
     *
     * @return: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple patternTermTuple = input.next();
        while ((patternTermTuple != null) && (!patternTermTuple.term.getContent().matches(pattern))) {
            patternTermTuple = input.next();
        }
        return patternTermTuple;
    }


    public static void main(String[] argv) {
        String fileName = Config.DOC_DIR + "3.txt";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AbstractTermTupleStream s = new PatternTermTupleFilter(new TermTupleScanner(reader));
        AbstractTermTuple iter = s.next();
        while (iter != null) {
            System.out.println(iter.toString() + "\n");
            iter = s.next();
        }
    }
}
