import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuctionItemTest {

    private AuctionItem item;

    @BeforeEach
    void setUp() {
        // Khởi tạo một vật phẩm đấu giá mới trước mỗi test case
        item = new AuctionItem("MacBook Pro", 1000.0);
    }

    @Test
    void testConstructor_NegativeStartingPrice_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new AuctionItem("Invalid Item", -50.0);
        });
        assertEquals("Giá khởi điểm không được âm.", exception.getMessage());
    }

    @Test
    void testPlaceBid_FirstValidBid_Success() {
        boolean result = item.placeBid("UserA", 1200.0);

        assertTrue(result);
        assertEquals(1200.0, item.getHighestBid());
        assertEquals("UserA", item.getHighestBidder());
    }

    @Test
    void testPlaceBid_HigherBid_Success() {
        item.placeBid("UserA", 1200.0);
        boolean result = item.placeBid("UserB", 1500.0); // UserB trả cao hơn

        assertTrue(result);
        assertEquals(1500.0, item.getHighestBid());
        assertEquals("UserB", item.getHighestBidder());
    }

    @Test
    void testPlaceBid_LowerOrEqualBid_ReturnsFalse() {
        item.placeBid("UserA", 1500.0);
        boolean result = item.placeBid("UserB", 1400.0); // UserB trả thấp hơn

        assertFalse(result);
        // Đảm bảo thông tin người thắng hiện tại không bị thay đổi
        assertEquals(1500.0, item.getHighestBid());
        assertEquals("UserA", item.getHighestBidder());
    }

    @Test
    void testPlaceBid_BelowStartingPrice_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            item.placeBid("UserC", 900.0); // Giá khởi điểm là 1000.0
        });
        assertEquals("Mức giá phải lớn hơn hoặc bằng giá khởi điểm.", exception.getMessage());
    }

    @Test
    void testPlaceBid_AuctionClosed_ThrowsException() {
        item.closeAuction(); // Đóng phiên đấu giá

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            item.placeBid("UserD", 2000.0);
        });
        assertEquals("Phiên đấu giá đã kết thúc.", exception.getMessage());
    }

    @Test
    void testGetters() {
        assertEquals("MacBook Pro", item.getItemName());
        assertEquals(1000.0, item.getStartingPrice());
        assertTrue(item.isActive());
    }
}