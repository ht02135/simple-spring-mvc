    public static class InvalidUserDataException extends DataIntegrityViolationException {
        public InvalidUserDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }