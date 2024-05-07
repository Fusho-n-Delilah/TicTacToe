public class Controller {
    private static Game game;
    private static ViewModel viewModel;
    private static View view;
    private static String currentPlayer;

    public Controller(View view, ViewModel viewModel){
        this.game = new Game();
        this.view = view;
        this.viewModel = viewModel;

        this.currentPlayer = "o";
        this.viewModel.setCurrentPlayer(this.currentPlayer);

        this.view.display(viewModel);
    }
    public static void handleClick(int x, int y){

        //do nothing if it's already filled
        if(!game.getValue(x,y).isEmpty()){
            return;
        }

        //set value of current players icon x or o
        game.setValue(x, y, currentPlayer);
        viewModel.setValue(x,y,game.getValue(x,y));

        //switch current player


        //check if move creates a win state
        if(game.isWin(currentPlayer)){
            viewModel.setState("win");
            viewModel.setWinningCells(game.getWinningCells());
        } else {
            //change current player and await next move
            if (currentPlayer.equals("o")){
                currentPlayer = "x";
            } else {
                currentPlayer = "o";
            }
            viewModel.setCurrentPlayer(currentPlayer);
        }

        view.display(viewModel);
    }

    public static void reset(){
        game = new Game();
        viewModel = new ViewModel();

        viewModel.setState("running");
        viewModel.setCurrentPlayer(currentPlayer);
        view.display(viewModel);
    }
}
