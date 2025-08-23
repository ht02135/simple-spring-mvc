    // ===== MODEL CLASSES =====
    
    public static class User {
        private Long id;
        private String name;
        private String email;
        private String department;
        
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
    }
}