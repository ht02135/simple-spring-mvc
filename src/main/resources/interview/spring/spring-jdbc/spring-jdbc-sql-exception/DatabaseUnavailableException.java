    public static class DatabaseUnavailableException extends DataAccessResourceFailureException {
        public DatabaseUnavailableException(String message, Throwable cause) {
            super(message, cause);
        }
    }