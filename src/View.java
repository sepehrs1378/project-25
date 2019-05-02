import java.util.List;

public class View {
    private static View ourInstance = new View();

    public static View getInstance() {
        return ourInstance;
    }

    private View() {
    }

    public void printOutputMessage(OutputMessageType outputMessageType) {
        System.out.println(outputMessageType.getMessage());
    }

    public void printHelp(HelpType helpType) {
        System.out.println(helpType.getMessage());
    }

    public void showInfoOfDeadCards(List<Card> cards) {
        showInfoOfHeroes(cards);
        showInfoOfSpellsAndMinions(cards);
    }

    private void showInfoOfSpellsAndMinions(List<Card> cards) {
        System.out.println("Cards:");
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) instanceof Spell) {
                Spell spell = (Spell) cards.get(i);
                System.out.println(i + " : Type : Spell - Name : " + spell.getName() + " - MP : " + spell.getMana() +
                        " - Desc : " + spell.getDescription());
            } else if (cards.get(i) instanceof Unit) {
                Unit unit = (Unit) cards.get(i);
                if (unit.getHeroOrMinion().equals("Minion")) {
                    System.out.println(i + " : Type : Minion - Name : " + unit.getName() + " - Class : " +
                            unit.getClass() + " - AP : " + unit.getAp() + " - HP : " + unit.getHp() +
                            " - MP : " + unit.getMana() + " - Special power : " + unit.getSpecialPower().getDescription());
                }
            }
        }
    }

    private void showInfoOfHeroes(List<Card> cards) {
        System.out.println("Heroes:");
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) instanceof Unit) {
                Unit unit = (Unit) cards.get(i);
                if (unit.getHeroOrMinion().equals("Hero")) {
                    System.out.println("1 : Name : " + unit.getId() + " - AP : " + unit.getAp() + " - HP : " +
                            unit.getHp() + " - Class : " + unit.getUnitClass() + " - Special power : " +
                            unit.getSpecialPower().getDescription());
                }
            }
        }
    }

    public void showUsers(List<Account> users, String currentUserName) {
        for (Account account : users) {
            if (!account.getUsername().equals(currentUserName))
                System.out.println(account.getUsername());
        }
    }

    public void showLeaderboard(List<Account> accounts) {
        int counter = 1;
        for (Account account : accounts) {
            System.out.println(counter + "- Username: " + account.getUsername() +
                    " - Wins: " + account.getNumberOfWins());
            counter++;
        }
    }

    public void showCollection(PlayerCollection playerCollection) {

    }

    public void showHeroInfo(Unit hero) {
        System.out.println("Name: " + hero.getId().split("_")[1] + " - AP: " + hero.getAp() +
                " - HP: " + hero.getHp() + " - Class: " + hero.getUnitClass() +
                " - Special Power: " + hero.getSpecialPower().getDescription() +
                " - Sell Cost: " + hero.getPrice());
    }

    public void showMinionInBattle(Unit minion, int[] coordination) {
        System.out.println(minion.getId() + " : " + minion.getId().split("_")[1] + ", health : " +
                minion.getHp() + ", location:(" + coordination[0] + "," + coordination[1] + "),power : "
                + minion.getAp());
    }

    public void showCardInfoHero(Unit hero) {
        System.out.println("Hero:");
        System.out.println("Name: " + hero.getName());
        System.out.println("Cost: " + hero.getPrice());
        System.out.println("Desc: " + hero.getDescription());
    }

    public void showCardInfoMinion(Unit unit) {
        System.out.println("Minion:");
        System.out.println("Name: " + unit.getName());
        System.out.println("HP: " + unit.getHp() + " AP: " + unit.getAp() + " MP: " + unit.getMana());
        System.out.println("Range: " + unit.getMaxRange());
        //System.out.println("Combo-ability: "+unit.get);
        //todo Combo-ability
        System.out.println("Cost: " + unit.getPrice());
        System.out.println("Desc: " + unit.getDescription());
    }

    public void showCardInfoSpell(Spell spell) {
        System.out.println("Spell: ");
        System.out.println("Name: " + spell.getName());
        System.out.println("MP: " + spell.getMana());
        System.out.println("Cost: " + spell.getPrice());
        System.out.println("Desc: " + spell.getDescription());
    }

    public void showItemInfo(Item item) {
        if (item instanceof Usable) {
            Usable usable = (Usable) item;
            System.out.println("Name: " + usable.getId() + " - Desc: " +
                    usable.getDescription() + " - Sell Cost: " + usable.getPrice());
            return;
        }
        if (item instanceof Collectable) {
            Collectable collectable = (Collectable) item;
            System.out.println("Name: " + collectable.getId() + " - Desc: " +
                    collectable.getDescription() + " - No Sell Cost: Collectable");
        }
    }

    public void showMatchHistoryTitle() {
        System.out.println("OPPONENT    WIN/LOSS    TIME");
    }

    public void showMatchHistory(String opponentName, String winOrLoss, long seconds,
                                 long minutes, long hours, long days) {
        System.out.print(opponentName + "   " + winOrLoss);
        if (days == 0 && hours == 0 && minutes == 0) {
            System.out.println(seconds + "seconds ago");
        } else if (days == 0 && hours == 0) {
            System.out.println(minutes + "minutes ago");
        } else if (days == 0) {
            System.out.println(hours + "hours ago");
        } else {
            System.out.println(days + "days ago");
        }
    }

    public void showUnitMove(String unitID, int destinationRow, int destinationColumn) {
        System.out.println(unitID + " move to " + destinationRow + " " + destinationColumn);
    }

    public void showGameInfo(Battle battle) {
        System.out.println("Mana points of player1 is: " + battle.getPlayer1().getMana());
        System.out.println("Mana points of player2 is: " + battle.getPlayer2().getMana());
        if (battle.getMode().equals(Constants.CLASSIC)) {
            System.out.println("HP of player1's hero is: " + battle.getPlayer1().getDeck().getHero().getHp());
            System.out.println("HP of player2's hero is: " + battle.getPlayer2().getDeck().getHero().getHp());
        } else if (battle.getMode().equals(Constants.ONE_FLAG)) {
            for (int i = 0; i < battle.getBattleGround().getCells().length; i++) {
                for (int j = 0; j < battle.getBattleGround().getCells()[i].length; j++) {
                    if (battle.getBattleGround().getCells()[i][j].getFlags().size() > 0) {
                        if (battle.getBattleGround().getCells()[i][j].getUnit().getId().equals(battle.getPlayer1().getPlayerInfo().getPlayerName())) {
                            System.out.println("flag is in row " + i + " column " + j + " in hand of player1");
                        } else if (battle.getBattleGround().getCells()[i][j].getUnit().getId().equals(battle.getPlayer1().getPlayerInfo().getPlayerName())) {
                            System.out.println("flag is in row " + i + " column " + j + " in hand of player2");
                        } else
                            System.out.println("flag is in row " + i + " column " + j);
                    }
                }
            }
        } else if (battle.getMode().equals(Constants.FLAGS)) {
            int rowCounter = 1;
            int flagCounter = 1;
            for (Cell[] cellRow : battle.getBattleGround().getCells()) {
                int columnCounter = 1;
                for (Cell cell : cellRow) {
                    for (Flag flag : cell.getFlags()) {
                        if (cell.getUnit() != null) {
                            System.out.println("flag" + flagCounter + " in row " + rowCounter +
                                    " column " + columnCounter + " " + cell.getUnit().getId());
                        } else {
                            System.out.println("flag" + flagCounter + " in row " + rowCounter +
                                    " column " + columnCounter);
                        }
                        flagCounter++;
                    }
                    columnCounter++;
                }
                rowCounter++;
            }
        }
    }

    public void showCardsAndItemsOfCollection(PlayerCollection playerCollection) {
        System.out.println("Heroes :");
        int counter = 1;
        for (Card card : playerCollection.getCards()) {
            if (card instanceof Unit && ((Unit) card).getHeroOrMinion().equals(Constants.HERO)) {
                Unit unit = (Unit) card;
                System.out.println("    " + counter + " : Name: " + unit.getName() + " - AP : " + unit.getAp()
                        + " - HP : " + unit.getHp() + " - Class : " + unit.getUnitClass() + " - Special Power : " + unit.getSpecialPower()
                        + " - Sell cost : " + unit.getPrice());
                counter++;
            }
        }
        System.out.println("Items :");
        counter = 1;
        for (Usable item : playerCollection.getItems()) {
            System.out.println("    " + counter + " : Name : " + item.getName() + " - Desc : " + item.getDescription()
                    + " - Sell Cost : " + item.getPrice());
            counter++;
        }
        System.out.println("Cards :");
        counter = 1;
        for (Card card : playerCollection.getCards()) {
            String outPut = String.format("   %d : ", counter);
            if (card instanceof Spell) {
                System.out.println(outPut + "Type : Spell - Name : " + card.getName() + " - MP : " + card.getMana()
                        + " - Desc : " + ((Spell) card).getDescription() + " Sell Cost : " + card.getPrice());
                counter++;

            } else if (card instanceof Unit && ((Unit) card).getHeroOrMinion().equals(Constants.MINION)) {
                System.out.println(outPut + "Minion - Name : " + card.getName() + " - Class : "
                        + card.getClass() + " - AP : " + ((Unit) card).getAp() + " - HP : " + ((Unit) card).getHp()
                        + " - MP : " + card.getMana() + " - Special Power : " + ((Unit) card).getSpecialPower()
                        + " - Sell Cost : " + card.getPrice());
                counter++;
            }
        }
    }

    public void showCardsAndItemsInShop() {
        List<Card> cardList = DataBase.getCardList();
        showInfoOfHeroes(cardList);
        for (Item item : DataBase.getUsableList()) {
            showItemInfo(item);
        }
        for (Item item : DataBase.getCollectableList()) {
            showItemInfo(item);
        }
        showInfoOfSpellsAndMinions(cardList);
    }

    public void showId(String id) {
        System.out.println("A card Or item exists with id : " + id);
    }

    public void showCardOrItemDoesNotExist() {
        System.out.println("There is no card or item with this name");
    }

    public void showDeck(Deck deck, String whiteSpace) {
        System.out.println(whiteSpace + "Heroes :");
        int counter = 1;
        System.out.println(whiteSpace + "   " + counter + " : Name : " + deck.getHero().getName() + " - AP : " + deck.getHero().getAp()
                + " - HP : " + deck.getHero().getHp() + " - Class : " + deck.getHero().getClass() + " - Special Power : "
                + deck.getHero().getSpecialPower());
        System.out.println(whiteSpace + "Items :");
        System.out.println(whiteSpace + "   " + counter + " : Name : " + deck.getItem().getName() + " - Desc : "
                + deck.getItem().getDescription());
        for (Card card : deck.getCards()) {
            if (card instanceof Spell) {
                System.out.println(whiteSpace + "   " + counter + " : " + "Type : Spell - Name : " + card.getName() + " - MP : " + card.getMana()
                        + " - Desc : " + ((Spell) card).getDescription() + " Sell Cost : " + card.getPrice());
                counter++;

            } else if (card instanceof Unit && ((Unit) card).getHeroOrMinion().equals(Constants.MINION)) {
                System.out.println(whiteSpace + "   " + counter + " : " + "Minion - Name : " + card.getName() + " - Class : "
                        + card.getClass() + " - AP : " + ((Unit) card).getAp() + " - HP : " + ((Unit) card).getHp()
                        + " - MP : " + card.getMana() + " - Special Power : " + ((Unit) card).getSpecialPower()
                        + " - Sell Cost : " + card.getPrice());
                counter++;
            }
        }
    }

    public void showAllDecks(PlayerCollection playerCollection, Deck mainDeck) {
        int counter = 1;
        String mainDeckName = "";
        if (mainDeck != null) {
            mainDeckName = mainDeck.getName();
            System.out.println(counter + " : " + mainDeckName + " :");
            showDeck(mainDeck, "    ");
            counter++;
        }
        for (Deck deck : playerCollection.getDecks()) {
            if (!deck.getName().equals(mainDeckName)) {
                System.out.println(counter + " : " + deck.getName() + " :");
                showDeck(deck, "    ");
            }
            counter++;
        }
    }

    public void printList(List<String> output) {
        for (String string : output) {
            System.out.println(string);
        }
    }

    public void showCollectables(List<Collectable> collectables) {
        int counter = 1;
        for (Collectable collectable : collectables) {
            System.out.println(counter + " : Name : " + collectable.getName() + " - Desc : " + collectable.getDescription());
            counter++;
        }
    }

    public void showCollectable(Collectable collectable) {
        System.out.println("Name : " + collectable.getName() + " - Desc : " + collectable.getDescription());
    }

    public void showHand(Hand hand) {
        for (Card card : hand.getCards()) {
            if (card instanceof Unit) {
                showCardInfoMinion((Unit) card);
            } else if (card instanceof Spell) {
                showCardInfoSpell((Spell) card);
            }

        }
    }

    public void print(String message) {
        System.out.println(message);
    }
}

