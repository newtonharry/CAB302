package sample.Instructions;

public interface VecInstruction {

    @Override
    String toString();

    InstructionType getType();

      /**
     * This function is inherited from all child shapes and must be implemented.
     *
     * @return void
     */
    void draw();

}
