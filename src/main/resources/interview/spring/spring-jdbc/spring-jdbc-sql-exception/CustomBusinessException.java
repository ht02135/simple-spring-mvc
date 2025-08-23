    // Custom exceptions from custom translator
    public static class CustomBusinessException extends DataAccessException {
        public CustomBusinessException(String message, Throwable cause) {
            super(message, cause);
        }
    }