package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;
import java.util.*;

public class IndexSearcher extends AbstractIndexSearcher {
    /**
     * 从指定索引文件打开索引，加载到index对象里. 一定要先打开索引，才能执行search方法
     *
     * @param indexFile ：指定索引文件
     */
    @Override
    public void open(String indexFile) {
        index.load(new File(indexFile));
        index.optimize();
    }

    /**
     * 根据单个检索词进行搜索
     *
     * @param queryTerm ：检索词
     * @param sorter    ：排序器
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        AbstractPostingList postingList = index.search(queryTerm);
        if (postingList == null)
            return new AbstractHit[0];

        List<AbstractHit> hits = new ArrayList<>();
        for (int i = 0; i < postingList.size(); i++) {
            AbstractPosting posting = postingList.get(i);
            int docId = posting.getDocId();
            String docPath = index.getDocName(docId);
            Map<AbstractTerm, AbstractPosting> termPostingMapping = new TreeMap<>();
            termPostingMapping.put(queryTerm, posting);
            AbstractHit hit = new Hit(docId, docPath, termPostingMapping);
            hit.setScore(sorter.score(hit));
            hits.add(hit);
        }
        sorter.sort(hits);
        return hits.toArray(new AbstractHit[0]);
    }

    /**
     * 根据二个检索词进行搜索
     *
     * @param queryTerm1 ：第1个检索词
     * @param queryTerm2 ：第2个检索词
     * @param sorter     ：    排序器
     * @param combine    ：   多个检索词的逻辑组合方式
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        AbstractPostingList postingList1 = index.search(queryTerm1);
        AbstractPostingList postingList2 = index.search(queryTerm2);

        if (combine == LogicalCombination.AND) {
            if (postingList1 == null || postingList2 == null) {
                return new AbstractHit[0];
            }
            // 找到相同的docId
            else {
                List<AbstractHit> hits = new ArrayList<>();
                for (int i = 0; i < postingList1.size(); i++) {
                    AbstractPosting posting1 = postingList1.get(i);
                    int pos2 = postingList2.indexOf(posting1);
                    if (pos2 != -1) {
                        AbstractPosting posting2 = postingList2.get(pos2);
                        Map<AbstractTerm, AbstractPosting> termPostingMapping = new TreeMap<>();
                        termPostingMapping.put(queryTerm1, posting1);
                        termPostingMapping.put(queryTerm2, posting2);
                        int docId = posting1.getDocId();
                        String docPath = index.getDocName(docId);
                        AbstractHit hit = new Hit(docId, docPath, termPostingMapping);
                        hit.setScore(sorter.score(hit));
                        hits.add(hit);
                    }
                }
                sorter.sort(hits);
                return hits.toArray(new AbstractHit[0]);
            }
        } else if (combine == LogicalCombination.OR) {
            if (postingList1 == null) return search(queryTerm2, sorter);
            else if (postingList2 == null) return search(queryTerm1, sorter);
            else {
                List<AbstractHit> hits1 = new ArrayList<>(Arrays.asList(search(queryTerm1, sorter)));
                List<AbstractHit> hits2 = new ArrayList<>(Arrays.asList(search(queryTerm2, sorter)));
                List<AbstractHit> resultHits = new ArrayList<>();
                // 合并
                for (AbstractHit hit2 : hits2) {
                    for (int i = 0; i < hits1.size(); i++) {
                        if (hits1.get(i).getDocId() == hit2.getDocId()) {
                            hits1.get(i).getTermPostingMapping().putAll(hit2.getTermPostingMapping());
                            hits1.get(i).setScore(sorter.score(hits1.get(i)));
                            break;
                        }
                        if (i == hits1.size() - 1) {
                            hits1.add(hit2);
                        }
                    }
                }
                sorter.sort(hits1);
                return hits1.toArray(new AbstractHit[0]);
            }

        }
        return new AbstractHit[0];
    }
}
