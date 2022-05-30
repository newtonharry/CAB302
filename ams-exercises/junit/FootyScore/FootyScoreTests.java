package junit.FootyScore;

//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class FootyScoreTests {


    FootyScore footyScore;

    @BeforeEach
    public void setupFootyScore(){
        this.footyScore = new FootyScore();
    }

    @Test
    public void testConstruction(){
        this.footyScore = new FootyScore();
    }


    @Test
    public void testNone(){
        this.footyScore = new FootyScore();
        assertEquals(0,this.footyScore.getPoints());
    }

    @Test
    public void testKickGoal(){
        this.footyScore = new FootyScore();
        assertEquals(0,this.footyScore.getPoints());
    }

   @Test
   public void testBehindGoal(){
        this.footyScore.kickBehind();
        assertEquals(1,this.footyScore.getPoints());
   }

    @Test
    public void testBothGoal(){
        this.footyScore.kickGoal();
        this.footyScore.kickBehind();
        assertEquals(7,this.footyScore.getPoints());

    }

    @Test
    public void testSpeakable(){
        this.footyScore.kickGoal();
        this.footyScore.kickBehind();
        assertTrue(footyScore.toString().equals("1, 1, 7"));
    }

    @Test
    public void testLosing(){
        FootyScore opponent = new FootyScore();
        this.footyScore.kickGoal();
        opponent.kickBehind();
        assertTrue(opponent.losing(this.footyScore.getPoints()));
    }

    @Test
    public void testNotLosing(){
        FootyScore opponent = new FootyScore();
        this.footyScore.kickGoal();
        opponent.kickBehind();
        assertTrue(!this.footyScore.losing(opponent.getPoints()));
    }

    @Test
    public void testNotLosingEqual(){
        FootyScore opponent = new FootyScore();
        this.footyScore.kickGoal();
        opponent.kickGoal();
        assertTrue(!this.footyScore.losing(opponent.getPoints()));
    }

    @Test
    public void testNotLosingEqualReversed(){
        FootyScore opponent = new FootyScore();
        this.footyScore.kickGoal();
        opponent.kickGoal();
        assertTrue(!opponent.losing(this.footyScore.getPoints()));
    }

}
