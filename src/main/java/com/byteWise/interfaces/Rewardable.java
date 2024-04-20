package src.main.java.com.byteWise.interfaces;
import src.main.java.com.byteWise.gamification.Badge;

public interface Rewardable {
    void rewardPoints(int points);
    void awardBadge(Badge badge);
}
