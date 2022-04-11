package com.example.demo.vo;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Page {
    private int pageCount;
    private int startPage;
    private int endPage;
    private int realEnd;
    private boolean prev, next;
    private int total;
    private Criteria criteria;

    public Page(){}

    public Page(int total, int pageCount, Criteria criteria){
        this.total = total;
        this.criteria = criteria;
        this.pageCount = pageCount;
        this.endPage = (int) (Math.ceil(criteria.getPageNum()*1.0/pageCount))*pageCount;
        this.startPage = (endPage - pageCount) + 1;

        if(startPage  <= 0 ) {
            startPage = 1;
        }
        realEnd = (int)(Math.ceil(total*1.0 / criteria.getAmount()));

        if(endPage > realEnd){
            endPage = realEnd;
        }

        prev = startPage == 1 ? false : true;
        next = endPage * criteria.getAmount() < total ? true : false;


        System.out.println("total = " + total);
        System.out.println("criteria = " + criteria);
        System.out.println("pageCount = " + pageCount);
        System.out.println("endPage = " + endPage);
        System.out.println("realEnd = " + realEnd);
        System.out.println("startPage = " + startPage);
        System.out.println("prev = " + prev);
        System.out.println("next = " + next);
    }

}
