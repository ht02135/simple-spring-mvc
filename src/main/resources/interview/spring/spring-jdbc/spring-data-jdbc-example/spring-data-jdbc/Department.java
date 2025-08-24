@Table("departments")
public class Department {
    @Id
    private Long id;
    
    private String name;
    private BigDecimal budget;
    
    // Constructors, getters, setters
    public Department() {}
    
    public Department(String name, BigDecimal budget) {
        this.name = name;
        this.budget = budget;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public BigDecimal getBudget() { return budget; }
    public void setBudget(BigDecimal budget) { this.budget = budget; }
}