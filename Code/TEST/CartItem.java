package rim;

public class CartItem {
	Product p;
	int sellingQuantity;
	String name;
	double price;
	public CartItem(Product p, int sellingQuantity) {
		super();
		this.p = p;
		this.sellingQuantity = sellingQuantity;
		this.name=p.getName();
		this.price=p.getPrice();
	}
	public Product getP() {
		return p;
	}
	public void setP(Product p) {
		this.p = p;
	}
	public int getSellingQuantity() {
		return sellingQuantity;
	}
	public void setSellingQuantity(int sellingQuantity) {
		this.sellingQuantity = sellingQuantity;
	}
	@Override
	public String toString() {
		return String.format("%-5s",p.getName()) + ": " + "("+sellingQuantity + ").......$"+String.format("%.2f",p.getPrice()*sellingQuantity) + "\n";
	}
}
