    // ===== CUSTOM EXCEPTION TRANSLATOR =====
    
    /**
     * Custom SQLExceptionTranslator implementation
     * This shows how to create domain-specific exception translations
     */
    private static class CustomSQLExceptionTranslator implements SQLExceptionTranslator {
        
        // Fallback to default translator
        private SQLExceptionTranslator fallbackTranslator = new SQLErrorCodeSQLExceptionTranslator();
        
        @Override
        public DataAccessException translate(String task, String sql, SQLException ex) {
            // Custom business logic for specific error codes
            if (ex.getErrorCode() == 1062 && ex.getSQLState().equals("23000")) {
                // MySQL duplicate key - translate to custom business exception
                return new CustomBusinessException("Business rule violation: Duplicate data not allowed", ex);
            }
            
            // Custom handling for connection timeouts
            if (ex.getSQLState() != null && ex.getSQLState().startsWith("08")) {
                return new CustomConnectionException("Database connection problem", ex);
            }
            
            // Fall back to standard Spring translation
            return fallbackTranslator.translate(task, sql, ex);
        }
    }