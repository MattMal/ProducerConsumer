# ProducerConsumer

import java.util.LinkedList;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Processor p = new Processor();
		Thread t1 = new Thread(new Runnable(){

			public void run() {
				try {
					p.produce();
				} catch (InterruptedException e) {
				}
			}
			
		});
		
		Thread t2 = new Thread(new Runnable(){

			public void run() {
				try {
					p.consume();
				} catch (InterruptedException e) {
				}
			}
			
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();

	}

}

class Processor{
	
	LinkedList<Integer> list = new LinkedList<Integer>();
	int a = 0;
	Object lock = new Object();
	public void produce() throws InterruptedException{
		
		
		while(true){
			synchronized(lock){
				while(list.size() == 5){
					lock.wait();
				}
				list.add(a++);
				System.out.println("One cycle is finished");
			}
			
		}
		
	}
	
	public void consume() throws InterruptedException{

		while(true){
			Thread.sleep(1000);

			synchronized(lock){
				int value = list.removeFirst();
				System.out.println(value);
				lock.notify();
			}
			
		}
		
	}
	
	
}
