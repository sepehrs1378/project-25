package ClientPackage;

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
        for (int i = 0, j = 1; i < cards.size(); i++) {
            if (cards.get(i) == null) {
                System.out.println("null");
            } else if (cards.get(i) instanceof Spell) {
                Spell spell = (Spell) cards.get(i);
                System.out.println("\t" + j++ + " : Type : Client.Models.Spell - Name : " + spell.getName() + " - MP : " + spell.getMana() +
                        " - Desc : " + spell.getDescription());
            } else if (cards.get(i) instanceof Unit) {
                Unit unit = (Unit) cards.get(i);
                if (unit.getHeroOrMinion().equals(Constants.MINION)) {
                    System.out.println("\t" + j++ + " : Type : Minion - Name : " + unit.getName() + " - Class : " +
                            unit.getClass() + " - AP : " + unit.getAp() + " - HP : " + unit.getHp() +
                            " - MP : " + unit.getMana() + " - Special power : " +
                            ((unit.getMainSpecialPower() == null) ? "" : unit.getMainSpecialPower().getDescription()));
                }
            }
        }
    }

    private void showInfoOfHeroes(List<Card> cards) {
        System.out.println("Heroes:");
        for (int i = 0, j = 1; i < cards.size(); i++) {
            if (cards.get(i) instanceof Unit) {
                Unit unit = (Unit) cards.get(i);
                if (unit == null) {
                    System.out.println("null");
                } else if (unit.getHeroOrMinion().equals(Constants.HERO)) {
                    System.out.println("\t" + j++ + " : Name : " + unit.getName() + " - AP : " + unit.getAp() + " - HP : " +
                            unit.getHp() + " - Class : " + unit.getUnitClass() + " - Special power : " +
                            ((unit.getMainSpecialPower() == null) ? "" : unit.getDescription()));
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

    public void showLeaderBoard(List<Account> accounts) {
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
                " - Special Power: " + hero.getMainSpecialPower().getDescription() +
                " - Sell Cost: " + hero.getPrice());
    }

    public void showMinionInBattle(Unit minion, int[] coordination) {
        System.out.println(minion.getId() + " : " + minion.getId().split("_")[1] + ", health : " +
                minion.getHp() + ", location:(" + coordination[0] + "," + coordination[1] + "),power : "
                + minion.getAp());
    }

    public void showCardInfoHero(Unit hero) {
        System.out.println("Hero:");
        System.out.println("\tName: " + hero.getName());
        System.out.println("\tCost: " + hero.getPrice());
        System.out.println("\tDesc: " + hero.getDescription());
    }

    public void showCardInfoMinion(Unit unit) {
        System.out.println("Minion:");
        System.out.println("\tName: " + unit.getName());
        System.out.println("\tHP: " + unit.getHp() + " AP: " + unit.getAp() + " MP: " + unit.getMana());
        System.out.println("\tRange: " + unit.getMaxRange());
        System.out.println("\tCombo-ability: " + unit.canUseComboAttack());
        System.out.println("\tCost: " + unit.getPrice());
        System.out.println("\tDesc: " + unit.getDescription());
    }

    public void showCardInfoSpell(Spell spell) {
        System.out.println("Client.Models.Spell: ");
        System.out.println("\tName: " + spell.getName());
        System.out.println("\tMP: " + spell.getMana());
        System.out.println("\tCost: " + spell.getPrice());
        System.out.println("\tDesc: " + spell.getDescription());
    }

    public int showItemInfo(int counter, Item item) {
        if (item instanceof Usable) {
            Usable usable = (Usable) item;
            System.out.println("\t" + counter + " : Name: " + usable.getName() + " - Desc: " +
                    usable.getDescription() + " - Sell Cost: " + usable.getPrice());
            return counter + 1;
        }
        if (item instanceof Collectable) {
            Collectable collectable = (Collectable) item;
            System.out.println("\t" + counter + " : Name: " + collectable.getName() + " - Desc: " +
                    collectable.getDescription() + " - No Sell Cost: Collectable");
            return counter + 1;
        }
        return counter;
    }

    public void showMatchHistoryTitle() {
        System.out.println("OPPONENT                 " +
                "WIN/LOSS                 " +
                "TIME");
    }

    public void showMatchHistory(String opponentName, String winOrLoss, long seconds,
                                 long minutes, long hours, long days, long years) {
        opponentName += generateEmptySpace(opponentName);
        System.out.print(opponentName + winOrLoss);
        if (years == 0 && days == 0 && hours == 0 && minutes == 0) {
            System.out.println(seconds + "seconds ago");
        } else if (years == 0 && days == 0 && hours == 0) {
            System.out.println(minutes + "minutes ago");
        } else if (years == 0 && days == 0) {
            System.out.println(hours + "hours ago");
        } else if (years == 0) {
            System.out.println(days + "days ago");
        } else {
            System.out.println(years + "years ago");
        }
    }

    public void showUnitMove(String unitID, int destinationRow, int destinationColumn) {
        System.out.println(unitID + " moved to (" + destinationRow + "," + destinationColumn + ")");
    }

    public void showGameInfo(Battle battle) {
        System.out.println("Turn number : " + battle.getTurnNumber());
        System.out.println("Player in turn : " + battle.getPlayerInTurn().getPlayerInfo().getPlayerName());
        System.out.println("Mana points of player1 is: " + battle.getPlayer1().getMana());
        System.out.println("Mana points of player2 is: " + battle.getPlayer2().getMana());
        if (battle.getMode().equals(Constants.CLASSIC)) {
            System.out.println("HP of player1's hero is: " + battle.getPlayer1().getDeck().getHero().getHp());
            System.out.println("HP of player2's hero is: " + battle.getPlayer2().getDeck().getHero().getHp());
        } else if (battle.getMode().equals(Constants.ONE_FLAG)) {
            for (int i = 0; i < battle.getBattleGround().getCells().length; i++) {
                for (int j = 0; j < battle.getBattleGround().getCells()[i].length; j++) {
                    if (!battle.getBattleGround().getCells()[i][j].getFlags().isEmpty()) {
                        if (battle.getBattleGround().getCells()[i][j].getUnit().getId().equals(battle.getPlayer1().getPlayerInfo().getPlayerName())) {
                            System.out.println("flag is in row " + i + " column " + j + " in hand of player1");
                        } else
                            System.out.println("flag is in row " + i + " column " + j);
                    }
                }
            }
        } else if (battle.getMode().equals(Constants.FLAGS)) {
            int rowCounter = 0;
            int flagCounter = 1;
            for (Cell[] cellRow : battle.getBattleGround().getCells()) {
                int columnCounter = 0;
                for (Cell cell : cellRow) {
                    if (cell.getUnit() != null) {
                        for (Flag flag : cell.getUnit().getFlags()) {
                            System.out.println("flag" + flagCounter + " in row " + rowCounter +
                                    " column " + columnCounter + " " + cell.getUnit().getId());
                            flagCounter++;
                        }
                    }
                    for (Flag flag : cell.getFlags()) {
                        System.out.println("flag" + flagCounter + " in row " + rowCounter +
                                " column " + columnCounter);
                        flagCounter++;
                    }
                    columnCounter++;
                }
                rowCounter++;
            }
        }
    }

    public void showCell(String toShow) {
        System.out.print(toShow + "|");
    }

    public void showCardsAndItemsOfCollection(PlayerCollection playerCollection) {
        System.out.println("Heroes :");
        int counter = 1;
        for (Card card : playerCollection.getCards()) {
            if (card instanceof Unit && ((Unit) card).getHeroOrMinion().equals(Constants.HERO)) {
                Unit unit = (Unit) card;
                System.out.println("    " + counter + " : Name: " + unit.getName() + " - AP : " + unit.getAp()
                        + " - HP : " + unit.getHp() + " - Class : " + unit.getUnitClass() + " - Special Power : " +
                        ((unit.getMainSpecialPower() == null) ? "" : unit.getMainSpecialPower().getDescription())
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
                System.out.println(outPut + "Type : Client.Models.Spell - Name : " + card.getName() + " - MP : " + card.getMana()
                        + " - Desc : " + ((Spell) card).getDescription() + " Sell Cost : " + card.getPrice());
                counter++;

            } else if (card instanceof Unit && ((Unit) card).getHeroOrMinion().equals(Constants.MINION)) {
                System.out.println(outPut + "Type : Minion - Name : " + card.getName() + " - Class : "
                        + card.getClass() + " - AP : " + ((Unit) card).getAp() + " - HP : " + ((Unit) card).getHp()
                        + " - MP : " + card.getMana() + " - Special Power : "
                        + ((((Unit) card).getMainSpecialPower() == null) ? "" : ((Unit) card).getMainSpecialPower().getDescription())
                        + " - Sell Cost : " + card.getPrice());
                counter++;
            }
        }
    }

    public void showCardsAndItemsInShop() {
        List<Card> cardList = DataBase.getInstance().getCardList();
        showInfoOfHeroes(cardList);
        System.out.println("Items:");
        int counter = 1;
        for (Item item : DataBase.getInstance().getUsableList()) {
            counter = showItemInfo(counter, item);
        }
        for (Item item : DataBase.getInstance().getCollectableList()) {
            counter = showItemInfo(counter, item);
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
        if (deck == null) {
            System.out.println("this deck doesn't exist");
            return;
        }
        System.out.println(whiteSpace + "Heroes :");
        int counter = 1;
        if (deck.getHero() != null) {
            System.out.println(whiteSpace + "   " + counter + " : Name : " + deck.getHero().getName() + " - AP : " + deck.getHero().getAp()
                    + " - HP : " + deck.getHero().getHp() + " - Class : " + deck.getHero().getClass() + " - Special Power : "
                    + ((deck.getHero().getMainSpecialPower() == null) ? "" : deck.getHero().getMainSpecialPower().getDescription()));
        }
        System.out.println(whiteSpace + "Items :");
        if (deck.getItem() != null) {
            System.out.println(whiteSpace + "   " + counter + " : Name : " + deck.getItem().getName() + " - Desc : "
                    + deck.getItem().getDescription());
        }
        System.out.println(whiteSpace + "Cards :");
        for (Card card : deck.getCards()) {
            if (card instanceof Spell) {
                System.out.println(whiteSpace + "   " + counter + " : " + "Type : Client.Models.Spell - Name : " + card.getName() + " - MP : " + card.getMana()
                        + " - Desc : " + ((Spell) card).getDescription() + " - Sell Cost : " + card.getPrice());
                counter++;

            } else if (card instanceof Unit && ((Unit) card).getHeroOrMinion().equals(Constants.MINION)) {
                Spell specialPower = ((Unit) card).getMainSpecialPower();
                System.out.println(whiteSpace + "   " + counter + " : " + "Type : Minion - Name : " + card.getName() + " - Class : "
                        + card.getClass() + " - AP : " + ((Unit) card).getAp() + " - HP : " + ((Unit) card).getHp()
                        + " - MP : " + card.getMana() + " - Special Power : " + (specialPower == null ? " " : specialPower.getDescription())
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

    public void showValidDecks(List<Deck> decks) {
        for (Deck deck : decks) {
            System.out.println(deck.getName());
        }
    }

    public void print(String message) {
        System.out.println(message);
    }

    public String generateEmptySpace(String string) {
        String spaces = "";
        for (int i = 0; i + string.length() < 25; i++) {
            spaces += " ";
        }
        return spaces;
    }
}

