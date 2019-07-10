import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Auction {
    private Account seller;
    private List<Account> bidders = new ArrayList<>();
    private List<Integer> bids = new ArrayList<>();
    private int baseMoney;
    private Card card;
    public Auction(Account seller, Card card){
        this.seller = seller;
        this.card = card;
        baseMoney = card.getPrice()*80/100;
    }

    public Account getSeller() {
        return seller;
    }

    public void setSeller(Account seller) {
        this.seller = seller;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }


    public int getBaseMoney() {
        return baseMoney;
    }

    public int getHighestBid(){
        int max =0;
        for (int i=0;i<bids.size();i++){
            if (bids.get(i)>max){
                max = bids.get(i);
            }
        }
        return max;
    }

    public int getHighestBidIndex(){
        int max = getHighestBid();
        for (int i =0;i<bids.size();i++){
            if (bids.get(i)==max){
                return i;
            }
        }
        return -1;
    }

    public Account getHighestBidder(){
        int i = getHighestBidIndex();
        if (i==-1){
            return null;
        }
        return bidders.get(i);
    }

    public void addBidder(Account bidder, int price){
        bidders.add(bidder);
        bids.add(price);
    }
}
