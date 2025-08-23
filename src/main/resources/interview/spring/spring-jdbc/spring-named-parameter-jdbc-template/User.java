    public static class User {
        private Long id;
        private String name;
        private String email;
        private String department;
        private Integer age;
        private Boolean isActive;
        private Double salary;
        
        // Constructors, getters, and setters
        public User() {}
        
        public User(String name, String email, String department) {
            this.name = name;
            this.email = email;
            this.department = department;
        }
        
        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        
        public Boolean getIsActive() { return isActive; }
        public void setIsActive(Boolean isActive) { this.isActive = isActive; }
        
        public Double getSalary() { return salary; }
        public void setSalary(Double salary) { this.salary = salary; }
        
        @Override
        public String toString() {
            return "User{id=" + id + ", name='" + name + "', email='" + email + 
                   "', department='" + department + "', age=" + age + 
                   ", isActive=" + isActive + ", salary=" + salary + "}";
        }
    }