    public static class CustomConnectionException extends DataAccessResourceFailureException {
        public CustomConnectionException(String message, Throwable cause) {
            super(message, cause);
        }
    }