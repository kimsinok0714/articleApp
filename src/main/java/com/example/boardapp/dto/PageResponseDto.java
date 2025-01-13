package com.example.boardapp.dto;


import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class PageResponseDto<E> {

    private List<E> dtoList;

    private List<Integer> pageNumList = new ArrayList<>();

    private PageRequestDto pageRequestDto;   // page, size

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, currentPage;

    private int pageSize = 10;

    private int current;


    @Builder
    public PageResponseDto(List<E> dtoList, PageRequestDto pageRequestDto, long total) {
        
        this.dtoList = dtoList;
        this.pageRequestDto = pageRequestDto;
        this.totalCount = (int)total;
        this.currentPage = pageRequestDto.getPage();

        int end = (int) (Math.ceil(this.pageRequestDto.getPage() / (double) pageSize)) * pageSize;

        int start = end - (pageSize - 1);

        // last page
        int last = (int)(Math.ceil(this.totalCount / (double) pageRequestDto.getSize()));

        end = end > last ? last : end;

        this.prev = start > 1;

        this.next = this.totalCount > end * pageRequestDto.getSize();

        System.out.println("totalCount : " + totalCount);        
        System.out.println("start : " + start);
        System.out.println("end : " + end);


        for (int i = start; i <= end; i++) {
            this.pageNumList.add(Integer.valueOf(i));
        }

        // this.pageNumList = IntStream.range(start, end).boxed().collect(Collectors.toList());

        this.prevPage = prev ? start - 1 : 0;
        
        this.nextPage = next ? end + 1 : 0;
        

    }


}
