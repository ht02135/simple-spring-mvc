package com.journaldev.hibernate.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.journaldev.hibernate.model.Employee;
import com.journaldev.hibernate.util.HibernateUtil;

public class HibernateGetVsLoad {

	public static void main(String[] args) {
		
		//Prep Work
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		//Get Example
		Employee emp = (Employee) session.get(Employee.class, new Long(2));
		System.out.println("Employee get called");
		System.out.println("Employee ID= "+emp.getId());
		System.out.println("Employee Get Details:: "+emp+"\n");
		
		//load Example
		Employee emp1 = (Employee) session.load(Employee.class, new Long(1));
		System.out.println("Employee load called");
		System.out.println("Employee ID= "+emp1.getId());
		System.out.println("Employee load Details:: "+emp1+"\n");
		
		//Close resources
		tx.commit();
		sessionFactory.close();
	}
}

/*
https://www.digitalocean.com/community/tutorials/hibernate-session-get-vs-load-difference-with-examples
-------------------
Session.get()
1>Loading Behavior: Performs an eager load. When get() is called, Hibernate 
immediately executes a SQL SELECT query to retrieve the data from the 
database and returns the actual persistent object.
2>Return Type: eturns the actual persistent object if found, or null if no 
matching record exists in the database.
3>Error Handling: Returns null if the entity with the specified ID is not found 
in the database. No exception is thrown in this scenario.
-------------------------
Session.load()
1>Loading Behavior: Performs a lazy load. When load() is called, Hibernate 
does not immediately hit the database. Instead, it returns a proxy object 
(a CGLIB or Javassist-generated subclass) with the given identifier. The 
actual data is only loaded from the database when a method (other than 
getId()) or a property of the proxy object is accessed.
2>Return Type: Returns a proxy object. If no matching record exists, 
an ObjectNotFoundException is thrown when a property of the proxy is 
accessed, not when load() is initially called.
3>Error Handling: Throws an ObjectNotFoundException if the entity with 
the specified ID does not exist when a method or property of the returned 
proxy object is accessed.
-------------------
When I execute above code, it produces following output.
Hibernate: select employee0_.emp_id as emp_id1_1_0_, employee0_.emp_name as emp_name2_1_0_, employee0_.emp_salary as emp_sala3_1_0_, address1_.emp_id as emp_id1_0_1_, address1_.address_line1 as address_2_0_1_, address1_.city as city3_0_1_, address1_.zipcode as zipcode4_0_1_ from EMPLOYEE employee0_ left outer join ADDRESS address1_ on employee0_.emp_id=address1_.emp_id where employee0_.emp_id=?
Employee get called
Employee ID= 2
Employee Get Details:: Id= 2, Name= David, Salary= 200.0, {Address= AddressLine1= Arques Ave, City=Santa Clara, Zipcode=95051}

Employee load called
Employee ID= 1
Hibernate: select employee0_.emp_id as emp_id1_1_0_, employee0_.emp_name as emp_name2_1_0_, employee0_.emp_salary as emp_sala3_1_0_, address1_.emp_id as emp_id1_0_1_, address1_.address_line1 as address_2_0_1_, address1_.city as city3_0_1_, address1_.zipcode as zipcode4_0_1_ from EMPLOYEE employee0_ left outer join ADDRESS address1_ on employee0_.emp_id=address1_.emp_id where employee0_.emp_id=?
Employee load Details:: Id= 1, Name= Pankaj, Salary= 100.0, {Address= AddressLine1= Albany Dr, City=San Jose, Zipcode=95129}
-------------------
From the output it’s clear that get() returns the object by fetching it from 
database or from hibernate cache whereas load() just returns the reference of 
an object that might not actually exists, it loads the data from database or 
cache only when you access other properties of the object.
*/