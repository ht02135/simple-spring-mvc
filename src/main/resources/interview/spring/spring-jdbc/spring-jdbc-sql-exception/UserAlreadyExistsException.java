    // ===== CUSTOM EXCEPTION CLASSES =====
    
    // Business-specific exceptions
    public static class UserAlreadyExistsException extends DataIntegrityViolationException {
        public UserAlreadyExistsException(String message, Throwable cause) {
            super(message, cause);
        }
    }