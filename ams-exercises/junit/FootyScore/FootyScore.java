package junit.FootyScore;

/**
 * An Australian Rules football score consists of a number of points,
 * accumulated by kicking goals and behinds.
 */
public class FootyScore {

    private int goals;
    private int behinds;

    /**
     * Constructs a team, initializing their score as 0.
     */
    public FootyScore() {
        goals = 0;
        behinds = 0;
    }

    /**
     * Returns the total number of points, based on the number of goals and behinds
     * kicked, and the number of points earned for each.
     *
     * @return the total number of points earned
     */
    public int getPoints() {
        final int pointsPerGoal = 6;
        final int pointsPerBehind = 1;
        return (goals * pointsPerGoal) + (behinds * pointsPerBehind);
    }

    /**
     * Increments the number of goals kicked.
     */
    public void kickGoal() {
        goals += 1;
    }

    /**
     * Increments the number of points kicked.
     */
    public void kickBehind() {
        behinds += 1;
    }

    /**
     * Returns a string representing the way an AFL score would be expressed by a
     * typical footy commentator, as the number of goals kicked, behinds kicked, and
     * points earned.
     *
     * @return a "speakable" football score
     */
    public String toString() {
        return Integer.toString(goals) + ", " + Integer.toString(behinds) + ", " + getPoints();
    }

    /**
     * Returns whether or not the given team's score exceeds this team's score.
     *
     * @param otherTeam the opposing footy team's score
     * @return true iff this team is losing
     */
    public boolean losing(int otherTeam) {
        return (getPoints() < otherTeam);
    }
}
