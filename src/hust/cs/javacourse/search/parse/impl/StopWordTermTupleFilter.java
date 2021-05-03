package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.StopWords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StopWordTermTupleFilter extends AbstractTermTupleFilter {
    private List<String> stopWords;

    /**
     * constructor
     *
     * @param input: 输入
     */
    public StopWordTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
        this.stopWords = new ArrayList<>(Arrays.asList(StopWords.STOP_WORDS));
    }

    /**
     * 获得下一个三元组
     *
     * @return: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple stopWordTermTuple = input.next();
        while (stopWordTermTuple != null && stopWords.contains(stopWordTermTuple.term.getContent())) {
            stopWordTermTuple = input.next();
        }
        return stopWordTermTuple;
    }
}
