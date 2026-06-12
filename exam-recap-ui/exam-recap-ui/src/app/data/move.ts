export class Move {
  id: number;
  gameId: number;
  userMove: string;
  computerMove: string;
  result: string;

  constructor(id: number, gameId: number, userMove: string, computerMove: string, result: string) {
    this.id = id;
    this.gameId = gameId;
    this.userMove = userMove;
    this.computerMove = computerMove;
    this.result = result;
  }
}
