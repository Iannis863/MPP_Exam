import {ChangeDetectorRef, Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Game} from '../data/game';
import {Move} from '../data/move';
import {Stats} from '../data/stats';
import {LeaderboardEntry} from '../data/leaderboard';
import {HttpClient} from '@angular/common/http';
import {CommonModule} from '@angular/common';

@Component({
  templateUrl: 'board.component.html',
  imports: [CommonModule]
})
export class GameBoard implements OnInit {
  userId: number | undefined = undefined;
  activatedRoute = inject(ActivatedRoute);
  private http = inject(HttpClient);
  private changeDetectorRef = inject(ChangeDetectorRef);

  game: Game | undefined = undefined;
  lastMove: Move | undefined = undefined;

  stats: Stats | undefined = undefined;
  leaderboard: LeaderboardEntry[] = [];

  constructor() {
    this.activatedRoute.params.subscribe(params => {
      try {
        this.userId = Number(params['Uid']);
      } catch (e) {
        console.error(e);
      }
    })
  }

  ngOnInit() {
    this.initGame();
  }

  private initGame() {
    if (this.userId !== undefined) {
      console.log("Initializing game for user:", this.userId);
      this.http.get<Game>('http://localhost:8080/game/' + this.userId).subscribe({
        next: (res: Game) => {
          this.game = res;
          this.changeDetectorRef.detectChanges();
          console.log("Game loaded/started", this.game);
        },
        error: (err) => {
          console.error("Error loading game:", err);
        }
      });
    }
  }

  private createNewGame() {
    this.initGame();
  }

  doMove(choice: string) {
    if (this.game != undefined) {
      let moveType = 0;
      if (choice === 'ROCK') moveType = 1;
      if (choice === 'PAPER') moveType = 2;
      if (choice === 'SCISSORS') moveType = 3;

      let moveRequest = {
        gameId: this.game.id,
        moveType: moveType
      };

      console.log("Sending move:", choice, "(type", moveType, ") for gameId:", this.game.id);

      this.http.post<any>('http://localhost:8080/move', moveRequest).subscribe({
        next: (res: any) => {
          const outcome = res.outcome;
          this.game = res.game;

          let resultString = '';
          if (outcome === 1) resultString = 'DRAW';
          else if (outcome === 2) resultString = 'WIN';
          else if (outcome === 3) resultString = 'LOSS';

          this.lastMove = {
            id: 0,
            gameId: this.game?.id || 0,
            userMove: choice,
            computerMove: '?',
            result: resultString
          };

          console.log("Move result:", resultString, "New Score:", this.game?.score, "Draws:", this.game?.draws);

          if (resultString === 'LOSS') {
            console.log("Game Over! New game started by server:", this.game);
          }

          this.changeDetectorRef.detectChanges();
        },
        error: (err) => {
          console.error("Error sending move:", err);
        }
      });
    }
  }

  showStats() {
    console.log("Fetching stats for user:", this.userId);
    this.http.get<Stats>('http://localhost:8080/stats/' + this.userId).subscribe((res: Stats) => {
      this.stats = res;
      console.log("Stats received:", res);
      this.changeDetectorRef.detectChanges();
    })
  }

  showLeaderboard() {
    console.log("Fetching global leaderboard");
    this.http.get<LeaderboardEntry[]>('http://localhost:8080/leaderboard').subscribe((res: LeaderboardEntry[]) => {
      this.leaderboard = res;
      console.log("Leaderboard received:", res);
      this.changeDetectorRef.detectChanges();
    })
  }

  hideStats() {
    this.stats = undefined;
  }

  hideLeaderboard() {
    this.leaderboard = [];
  }
}
