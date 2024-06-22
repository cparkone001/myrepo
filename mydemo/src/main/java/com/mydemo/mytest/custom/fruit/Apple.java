package com.mydemo.mytest.custom.fruit;

public class Apple {

    @FruitName("Apple")
    private String appleName;

    @FruitColor(fruitColor = FruitColor.Color.RED)
    private String appleColor;

    @FruitProvider(id = 1,name = "HomePlus",address = "Seoul")
    private String appleProvider;
   
}