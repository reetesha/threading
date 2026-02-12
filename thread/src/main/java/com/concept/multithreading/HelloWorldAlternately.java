package com.concept.multithreading;

public class HelloWorldAlternately {

    public static void main(String args[]){
        Printer printer= new Printer();
        Thread t1 = new Thread(new PrintHelloTask(printer));
        Thread t2 = new Thread(new PrintWorldTask(printer));

       t1.start();
        t2.start();
    }
}

class Printer{
    boolean helloTurn= true;

    public synchronized void printHello() throws InterruptedException {
        while(!helloTurn){
            wait();
        }
        for(int i=0;i<2;i++){
            System.out.println("Hello");
        }
        helloTurn=false;
        notifyAll();
    }

    public synchronized void printWorld() throws InterruptedException {
        while(helloTurn){
            wait();
        }
        for(int i=0;i<2;i++){
            System.out.println("World");
        }
        helloTurn=true;
        notifyAll();
    }

}

class PrintHelloTask implements Runnable{
    Printer printer;

    PrintHelloTask(Printer printer){
        this.printer= printer;
    }

    @Override
    public void run() {
        for(int i=0;i<2;i++){
            try {
                printer.printHello();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class PrintWorldTask implements Runnable{
    Printer printer;
    PrintWorldTask(Printer printer){
        this.printer= printer;
    }

    @Override
    public void run(){
        for(int i=0;i<2;i++){
            try {
                printer.printWorld();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


