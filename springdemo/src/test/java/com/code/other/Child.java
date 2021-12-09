package com.code.other;

public class Child extends Parent{

    public void work(){
        System.out.println("Child work");
        super.work();
    }


    public void paly(){
        System.out.println("Child play");
    }

    public static void main(String[] args) {
        Child child = new Child();
        child.work();
    }
}
