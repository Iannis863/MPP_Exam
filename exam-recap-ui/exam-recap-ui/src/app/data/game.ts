export class Game {
  id: number;
  userId: number;
  score: number;
  draws: number;
  status: string = "";

  constructor(id: number, userId: number, score: number, draws: number, status: string) {
    this.id = id;
    this.userId = userId;
    this.score = score;
    this.draws = draws;
    this.status = status;
  }
}
