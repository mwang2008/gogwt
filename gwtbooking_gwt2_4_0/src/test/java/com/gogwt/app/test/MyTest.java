package com.gogwt.app.test;

public class MyTest {

	private void test() throws Throwable {
		System.out.println(" test ");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new MyTest().test();
		}
		catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
