package com.gogwt.app.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class Quest {

	class A{}
	class B extends A{}
	class C extends A{}
	
	private void test() {
		System.out.println("heel=" + "hello".substring(2,3));
		
		A x = new A();
		B y = new B();
		C z = new C();
		
		x = y;
		//z = x;  compile error
		y = (B)x;
		//y = (A)y; compile error
		
		int i,j=0;

	     for(i=10;i<0;i--) { j++; }

	     switch(j) {

	     case (0) :

	         j=j+1;

	     case(1):

	         j=j+2;

	         break;

	     case (2) :

	         j=j+3;

	         break;

	     

	     case (10) :

	         j=j+10;

	         break;

	     default :

	         break;

	     }

	   System.out.println(j);

	   Collection<Integer> coll = initialize(1);
	   Collection<String> coll2 = initialize("hello");
	   Collection<Date> coll3 = initialize(new Date());
	   
	}
	int i=0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	   	System.out.println(Math.floor(-2.1));
        System.out.println(11^2);
        
        HashSet set=new HashSet();
        set.add(3);
        set.add(3);
        
        int val = 0;
        if (val >4) {
        	System.out.println("Test A");
        }
        else if (val > 9) {
        	System.out.println("Test B");
        }
        else {
        	System.out.println("Test C");
        }
        try {
			InetAddress ad = InetAddress.getByName("122.22.22.22");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		char c='c';
		int i=50;
		double d=80;
		long l=200;
		String s = "GoogBy";
		
		s+=i;
		//i+=s;  //compile error
		//c+=s;  //compile error
		//c=c+i;  //compile error
		
		int a = 2;
		int b = 0;
		System.out.println("==" + g(a, new int[]{b}));
		
		System.out.println("==" + Math.ceil(a));
		
		char ch;

        String test2 = "abcd";

        String test = new String("abcd");

        if(test.equals(test2)) {

            if(test == test2)

                ch = test.charAt(0);

            else

                ch = test.charAt(1);	           

        }

        else {

            if(test == test2)

                ch = test.charAt(2);

            else                

                ch = test.charAt(3);

        }

        System.out.println(ch);
        Quest test1 = new Quest();
        System.out.println("test" + test1.xyz(100));
        
        //System.out.println(args[0]);
        System.out.println(test1.getClass().getName());
        
        test1.test();
        
        
        
	}
	
	public int xyz(int num) {

        if(num == 1) return 1;

        else return(xyz(num-1) + num);

        
        
    }

	public static int g(int a, int b[]) {
		b[0] = 2*a;
		return b[0];
	}
	
	Quest() {
		top: 
		while (i<2) {
			System.out.println(i);
			i++;
			continue top;
		}
	 
	}
	
	public <T> Collection<T> initialize(T element) {

		Collection<T> coll = new ArrayList<T>();

		coll.add(element);

		return coll;
		}

	
	String str = new String("OPPS");
	public void KintMethod(final int iArgs) {
		int iOne;
		class Bicycle {
			public void sayHello() {
				System.out.println(str);
				System.out.println(iArgs);
			}
		}
	}
	
	public void Method2() {
		int iTwo;
	}
	
	public synchronized void  processIt() {
		
	}
}
