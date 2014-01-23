package com.pma.db;

import java.util.ResourceBundle;


public class DatabaseBundle {
	
	public static void main(String[] args) {
		ResourceBundle bundle = ResourceBundle.getBundle(DatabaseBundle.class.getName());
		
		System.out.println(bundle.getString("dbURL"));
		System.out.println(bundle.getString("dbDriver"));
		System.out.println(bundle.getString("dbUserName"));
		System.out.println(bundle.getString("dbPassword"));
		
	 		
	}
}
