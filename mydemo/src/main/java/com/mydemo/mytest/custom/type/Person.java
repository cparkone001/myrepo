package com.mydemo.mytest.custom.type;

import java.math.BigDecimal;
import java.util.Date;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@PersonInfo(mention = "반가워요.")
public class Person {
	private String name;
	private int age;
}
