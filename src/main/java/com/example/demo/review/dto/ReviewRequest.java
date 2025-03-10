public class ReviewRequest {
    private Long userId;      // 사용자 ID
    private Long productId;   // 상품 ID
    private String content;   // 후기 내용

    // Getter 및 Setter
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}