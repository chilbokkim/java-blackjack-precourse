package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.card.CardFactory;
import domain.user.Dealer;
import domain.user.Player;

public class BlackJackGame {
	Scanner scanner = new Scanner(System.in);
	private List<String> playerNames = new ArrayList<String>();
	private List<Double> bettingMoney = new ArrayList<Double>();
	private int numberOfPlayers = 0;
	private List<Player> players = new ArrayList<Player>();
	private Dealer dealer = new Dealer();
	private CardFactory deck = new CardFactory();
	public BlackJackGame() {
	}
	public void run() {
		setPlayerNames();
		setBettingMoney();
		setPlayers();
		
		firstDraw();
		printAllHands();
		for(int i=0;i<numberOfPlayers;i++) {
			drawCard(players.get(i));
		}
		setGameResult();
		checkDealerScore();
		printFinalState();
		printFinalBenefit();
		return;
	}
	public void setDealer() {
		
	}
	public void setPlayerNames() {
		String[] nameArray;
		String nameString;
		System.out.println("���ӿ� ������ ����� �̸��� �Է��ϼ���. (��ǥ �������� �и�)");
		nameString = scanner.nextLine();
		nameArray = nameString.split(",");
		for(int i=0;i<nameArray.length;i++) {
			playerNames.add(nameArray[i]);
		}
		setNumberOfPlayers();
	}
	public void setBettingMoney() {
		int tmpBettingMoney;
		for(int i=0;i<getNumberOfPlayers();i++) {
			System.out.println(getPlayerNameByIndex(i)+"�� ���� �ݾ���?");
			tmpBettingMoney = scanner.nextInt();
			bettingMoney.add((double)tmpBettingMoney);
		}
	}
	public void setNumberOfPlayers() {
		this.numberOfPlayers = playerNames.size();
	}
	public void setPlayers() {
		for(int i=0;i<getNumberOfPlayers();i++) {
			System.out.println(getPlayerNameByIndex(i)+"�� ���� �ݾ���?");
			Player tmpPlayer = new Player(getPlayerNameByIndex(i), getBettingMoneyByIndex(i));
			players.add(tmpPlayer);
		}
	}
	
	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}
	public String getPlayerNameByIndex(int index) {
		return this.playerNames.get(index);
	}
	public double getBettingMoneyByIndex(int index) {
		return this.bettingMoney.get(index);
	}
	public void firstDraw() {
		System.out.println("������ "+getPlayerNames()+"���� ī�带 ���徿 ���������ϴ�.");
		dealer.addCard(deck.drawCard());
		dealer.addCard(deck.drawCard());
		
		for(int i=0;i<getNumberOfPlayers();i++) {
			players.get(i).addCard(deck.drawCard());
			players.get(i).addCard(deck.drawCard());
		}
		setScore();
	}
	public void drawCard(Player player) {
		//String input = null;
		while(player.getScore()<21) {
			System.out.println(player.getName()+"�� ī�带 �߰��� �����ðڽ��ϱ�? (y/n)");
			char input = scanner.next().trim().charAt(0);
			if(input=='y') {
				player.addCard(deck.drawCard());
				player.setScore();
			}else {
				return;
			}
		}
	}
	public String getPlayerNames() {
		String result = "";
		for(int i=0;i<getNumberOfPlayers()-1;i++) {
			result = result + getPlayerNameByIndex(i) +", ";
		}
		result = result+getPlayerNameByIndex(getNumberOfPlayers()-1);
		return result;
	}
	public void setScore() {
		dealer.setScore();
		for(int i=0;i<numberOfPlayers;i++) {
			players.get(i).setScore();
		}
	}
	public int findBlackJackPlayerIndex() {
		int index = -1;
		
		return index;
	}
	public void checkDealerScore() {
		if(dealer.getScore()<17) {
			System.out.println("������ ������ 16�����̹Ƿ� ������ �� �޽��ϴ�");
			dealer.addCard(deck.drawCard());
			dealer.printHands();
			dealer.setScore();
		}
	}
	public void printFinalBenefit() {
		int result = 0;
		System.out.println("##���� ����##");
		System.out.println("���� : "+(int)dealer.getBettingMoney());
		for(int i=0;i<numberOfPlayers;i++) {
			result = (int)(players.get(i).getBettingMoney()-bettingMoney.get(i));
			System.out.println(getPlayerNameByIndex(i)+" : "+result);
		}
	}
	public void setGameResult() {
		
	}
	public void printFinalState() {
		System.out.println();
		System.out.println("-------------�������----------------");
		System.out.println("�� ���þ׼� : "+getTotalBetting());
		System.out.println("-----------------------------");
		dealer.printHands();
		System.out.println("������ ���� : "+dealer.getScore());
		System.out.println("-----------------------------");
		for(int i=0;i<numberOfPlayers;i++) {
			players.get(i).printHands();
			System.out.println(getPlayerNameByIndex(i)+"�� ���� : "+players.get(i).getScore());
			System.out.println("-----------------------------");
		}
	}
	public int getTotalBetting() {
		int sum =0;
		for(int i=0;i<numberOfPlayers;i++) {
			sum += bettingMoney.get(i);
		}
		return sum;
	}
	public void printAllHands() {
		dealer.printHands();
		for(int i=0;i<numberOfPlayers;i++) {
			players.get(i).printHands();
		}
	}
}
