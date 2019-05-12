package sample;

public class Fill implements VecInstruction{
    private int colour;


    public Fill(String value){
          if (value.equals("OFF")){
              this.colour = -0xFFFFFF;
          } else{
              this.colour = Integer.parseInt(value, 16);
          }
    }

    public int getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return null;
    }
}
