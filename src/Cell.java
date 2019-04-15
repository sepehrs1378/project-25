class Cell {
    private Card card;
    private Item item;
    private List<Buff> buffs = new ArrayList<Buff>();

    public Card getCard() {
        return card;
    }

    public Item getItem() {
        return item;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void addBuff(Buff newBuff) {

    }

    public void deleteBuff(Buff buff){

    }
}
