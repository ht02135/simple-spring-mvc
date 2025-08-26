/*
hibernate-cache-example/
 ├── src/
 │    ├── main/java/
 │    │    ├── com/example/Employee.java
 │    │    ├── com/example/HibernateUtil.java
 │    │    └── com/example/MainApp.java
 │    └── main/resources/
 │         ├── hibernate.cfg.xml
 │         └── ehcache.xml
//////////////////////////////
In Hibernate (an ORM framework for Java), caching improves performance 
by reducing the number of database queries. Instead of fetching the 
same data multiple times from the database, Hibernate stores it in 
memory and reuses it.
-----------------------------
1️⃣ First-Level Cache (Session Cache)
1a>Scope: Exists per Hibernate Session (org.hibernate.Session).
1b>Enabled by default (cannot be disabled).
1c>Stores objects associated with the current session.
1d>If you fetch the same entity multiple times in the same session, 
Hibernate returns the cached object instead of hitting the database again.
-----------------------------
2️⃣ Second-Level Cache (SessionFactory Cache)
2a>Scope: Shared across all sessions from a SessionFactory.
2b>Not enabled by default — you must configure it.
2c>Requires a cache provider (e.g., Ehcache, Infinispan, OSCache).
2d>Stores entities, collections, or queries at the SessionFactory level, 
so that multiple sessions can reuse cached data.
------------------------------
Example behavior:
Session session = sessionFactory.openSession();
// First time: SQL query executed, data loaded from DB
Employee emp1 = session.get(Employee.class, 1);
// Second time: No SQL query, returned from session cache
Employee emp2 = session.get(Employee.class, 1);
System.out.println(emp1 == emp2); // true (same object from cache)
*/
package com.example;

import org.hibernate.Session;

public class MainApp {
    public static void main(String[] args) {
        // --- First Session ---
        Session session1 = HibernateUtil.getSessionFactory().openSession();
        session1.beginTransaction();

        // First fetch → hits DB
        Employee emp1 = session1.get(Employee.class, 1);
        System.out.println("First fetch: " + emp1);

        // Second fetch in SAME session → no DB hit (first-level cache)
        Employee emp2 = session1.get(Employee.class, 1);
        System.out.println("Second fetch in same session: " + emp2);

        session1.getTransaction().commit();
        session1.close();

        // --- Second Session ---
        Session session2 = HibernateUtil.getSessionFactory().openSession();
        session2.beginTransaction();

        // Fetch again → no DB hit (second-level cache via Ehcache)
        Employee emp3 = session2.get(Employee.class, 1);
        System.out.println("Fetch in new session (from 2nd level cache): " + emp3);

        session2.getTransaction().commit();
        session2.close();

        HibernateUtil.getSessionFactory().close();
    }
}
