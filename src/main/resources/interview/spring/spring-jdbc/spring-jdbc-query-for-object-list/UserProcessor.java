    // ===== CALLBACK INTERFACES =====

    @FunctionalInterface
    public interface UserProcessor {
        void process(User user);
    }