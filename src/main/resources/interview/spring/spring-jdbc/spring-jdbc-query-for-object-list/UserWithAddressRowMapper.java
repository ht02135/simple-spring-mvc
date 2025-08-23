    /**
     * RowMapper for complex join queries
     */
    private static class UserWithAddressRowMapper implements RowMapper<UserWithAddress> {
        @Override
        public UserWithAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserWithAddress userWithAddress = new UserWithAddress();
            
            // User data
            userWithAddress.setId(rs.getLong("id"));
            userWithAddress.setName(rs.getString("name"));
            userWithAddress.setEmail(rs.getString("email"));
            userWithAddress.setDepartment(rs.getString("department"));
            userWithAddress.setSalary(rs.getDouble("salary"));
            
            // Address data (might be null in LEFT JOIN)
            String street = rs.getString("street");
            if (street != null) {
                userWithAddress.setStreet(street);
                userWithAddress.setCity(rs.getString("city"));
                userWithAddress.setState(rs.getString("state"));
                userWithAddress.setZipCode(rs.getString("zip_code"));
            }
            
            return userWithAddress;
        }
    }