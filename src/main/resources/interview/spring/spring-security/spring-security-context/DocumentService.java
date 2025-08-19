@Service
@Transactional
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    // Method-level security with @PreAuthorize
    @PreAuthorize("hasRole('ADMIN') or @documentService.isOwner(#id, authentication.name)")
    public Document getDocument(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));
    }

    // Custom security method
    public boolean isOwner(Long documentId, String username) {
        return documentRepository.findById(documentId)
                .map(doc -> doc.getOwner().equals(username))
                .orElse(false);
    }

    // Accessing security context in service
    @PostAuthorize("returnObject.owner == authentication.name or hasRole('ADMIN')")
    public Document createDocument(Document document) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        
        document.setOwner(currentUser);
        document.setCreatedBy(currentUser);
        document.setCreatedDate(LocalDateTime.now());
        
        return documentRepository.save(document);
    }
}