    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setDepartment(rs.getString("department"));
            user.setAge(rs.getInt("age"));
            user.setIsActive(rs.getBoolean("is_active"));
            user.setSalary(rs.getDouble("salary"));
            return user;
        }
    }
}