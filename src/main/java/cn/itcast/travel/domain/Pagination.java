package cn.itcast.travel.domain;

import java.util.List;

/**
 * 装载分页查询数据
 */
public class Pagination<T> {

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getBeforePage() {
       this.beforePage =  this.page-1;
       if(this.beforePage<=0){
           this.beforePage=1;
       }
        return beforePage;
    }


    public int getNextPage() {
        this.nextPage=this.page+1;
        if(this.nextPage>=this.totalPages){
            this.nextPage=this.totalPages;
        }
        return nextPage;
    }


    public int getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(int totalCounts) {
        this.totalCounts = totalCounts;
    }

    public int getTotalPages() {
        this.totalPages = (this.totalCounts+this.pageSize-1)/this.pageSize;
        return totalPages;
    }



    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int[] getPageBar() {
        //  百度分页栏制作 前五后四原则 10个页码    11   19
        int beginPage;  //  10
        int endPage;   //  19
        //  100  10    101   10     99   10
        int totalPages = (totalCounts+pageSize-1)/pageSize;

        if(totalPages<=10){
            beginPage=1;
            endPage=totalPages;
        }else{
            //  大于10个页码 前五后四原则
            beginPage=page-5;
            endPage=page+4;

            //  如果当前页码 page =2  如果 总页码 12页   当前页码 page 11

            if(beginPage<=0){
                beginPage = 1;
                endPage=beginPage+9;
            }

            if(endPage>=totalPages){
                endPage=totalPages;
                beginPage=endPage-9;
            }

        }

        //  数组完成动态分页栏制作

        int arr[] = new int[endPage-beginPage+1];
        int index=0;
        for (int i = beginPage; i <=endPage ; i++) {
            arr[index++]=i;
        }


        return arr;


    }



    private int page =1;
    private int pageSize =10;
    private int beforePage;
    private int nextPage;
    private int totalCounts;//  数据库查询
    private int totalPages;
    private List<T> data;//  数据库查询  service  调用dao
    private int[] pageBar;




}
