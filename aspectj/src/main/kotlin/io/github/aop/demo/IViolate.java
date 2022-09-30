package io.github.aop.demo;

public class IViolate {


    private volatile Integer age;


    public static void main(String[] args) {
        final IViolate iViolate = new IViolate();
        iViolate.age = 1;
        System.out.println(iViolate.age);
    }
}
