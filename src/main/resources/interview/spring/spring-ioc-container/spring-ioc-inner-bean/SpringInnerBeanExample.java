public class SpringInnerBeanExample {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        // Retrieve the outer bean
        Person person = (Person) context.getBean("person");
        
        // Retrieve the inner bean by its ID
        Address homeAddress = (Address) context.getBean("homeAddress");
        
        // Retrieve another inner bean
        Department engineeringDept = (Department) context.getBean("engineeringDept");
        
        System.out.println("Person: " + person.getName());
        System.out.println("Address from inner bean: " + homeAddress.getStreet());
        System.out.println("Department: " + engineeringDept.getName());
        
        // This would work - both references point to the same object
        System.out.println("Same address object: " + 
            (person.getAddress() == homeAddress)); // true
    }
}