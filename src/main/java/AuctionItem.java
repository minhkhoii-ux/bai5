public class AuctionItem {
    private String itemName;
    private double startingPrice;
    private double highestBid;
    private String highestBidder;
    private boolean isActive;

    public AuctionItem(String itemName, double startingPrice) {
        if (startingPrice < 0) {
            throw new IllegalArgumentException("Giá khởi điểm không được âm.");
        }
        this.itemName = itemName;
        this.startingPrice = startingPrice;
        this.highestBid = 0.0;
        this.highestBidder = null;
        this.isActive = true;
    }

    /**
     * Xử lý logic đặt giá của người chơi (Bidder)
     */
    public boolean placeBid(String bidder, double bidAmount) {
        if (!isActive) {
            throw new IllegalStateException("Phiên đấu giá đã kết thúc.");
        }
        if (bidAmount < startingPrice) {
            throw new IllegalArgumentException("Mức giá phải lớn hơn hoặc bằng giá khởi điểm.");
        }
        if (bidAmount <= highestBid) {
            return false; // Đặt giá thất bại do không cao hơn giá hiện tại
        }

        // Cập nhật người trả giá cao nhất
        this.highestBid = bidAmount;
        this.highestBidder = bidder;
        return true;
    }

    public void closeAuction() {
        this.isActive = false;
    }

    // --- Các phương thức Getters ---
    public String getItemName() { return itemName; }
    public double getStartingPrice() { return startingPrice; }
    public double getHighestBid() { return highestBid; }
    public String getHighestBidder() { return highestBidder; }
    public boolean isActive() { return isActive; }
}