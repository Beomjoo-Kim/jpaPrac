package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookForm {

    //상품공통속성
    private long id;

    private String name;
    private int price;
    private int stockQuantity;

    //book 고유속성
    private String author;
    private String isbn;
}
